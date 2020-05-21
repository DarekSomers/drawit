package drawit.shapegroups1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.Set;
import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;
import logicalcollections.LogicalList;



/**
 * Each instance of a ShapeGroup is either a LeafShapeGroup, containing a RoundedPolygon object, or a NonleafShapeGroup,
 * containing multiple ShapeGroup objects.
 * @invar A shapeGroup is either a LeafShapGroup or a NonleafShapegroup
 * 		| (ShapeGroup instanceof LeafShapeGroup || ShapeGroup instanceof NonleafShapeGroup) && 
 * 		| !(ShapeGroup instanceof LeafShapeGroup && ShapeGroup instanceof NonleafShapeGroup)
 * @invar this Shapegroup does not have the same ShapeGroup as a child twice
 * 		|  LogicalList.distinct(((NonleafShapeGroup) this).getSubgroups())
 * @invar this ShapeGroup's children have this ShapeGroup as their parent
 * 		|  ((NonleafShapeGroup) this).getSubgroups().stream().allMatch(child -> child.getParentGroup() == this)
 * @invar this Shapegroup is a root ShapeGroup or else it is among parent's children
 * 		|  getParentGroup() == null || getParentGroup().getSubgroups().contains(this)
 * @invar this Shapegroup does not have itself as an ancestor
 * 		|  !((NonleafShapeGroup) this).getSubgroups().contains(this)
 */
public abstract class ShapeGroup {
	
	/**
	 * Contains the original extent when initializing the ShapeGroup
	 * @invar The extent cannot be null
	 * 		| !(getOriginalExtent() == null)
	 */
	private Extent ex;
	/**
	 * Contains the new extent when initializing the ShapeGroup and setting new extents
	 * @invar The new extent cannot be null
	 * 		| !(getExtent() == null)
	 */
	private Extent newEx;
	/**
	 * Contains the last set extent before setting a new extent
	 * @invar The extent cannot be null
	 * 		| !(prevEx == null)
	 */
	private Extent prevEx;
	/**
	 * Contains the parent ShapeGroup of this ShapeGroup
	 * 
	 */
	private NonleafShapeGroup parent;

	
	
	
	/**
	 * Shows the change of the left value of the extent when the right value is updated
	 */
	private double changeXl = 0;
	/**
	 * Shows the change of the right value of the extent when the left value is updated
	 */
	private double changeXr = 0;
	/**
	 * Shows the change of the top value of the extent when the bottom value is updated
	 */
	private double changeYt = 0;
	/**
	 * Shows the change of the bottom value of the extent when the top value is updated
	 */
	private double changeYb = 0;

	
	/**
	 * Returns the extent of the ShapeGroup expressed in its outer coordinate system
	 * @return newEx
	 */
	public  Extent getExtent() {
		return newEx;
	}
	
	/**
	 * Returns the extent of the ShapeGroup expressed in its inner coordinate system
	 * @return ex
	 */
	public Extent getOriginalExtent() {
		return ex;
	}
	
	/**
	 * Returns the previous extent of the ShapeGroup expressed in its outer coordinate system
	 * @return prevEx
	 */
	public Extent getPreviousExtent() {
		return prevEx;
	}
	
	/**
	 * Returns the ShapeGroup that directly contains this ShapeGroup or null if no shapeGroup directly contains this ShapeGroup
	 * @return parent
	 * @post if parent is null, the given ShapeGroup is a root ShapeGroup
	 * 		| result == null || result != null
	 */
	public NonleafShapeGroup getParentGroup() {
		if (parent == null)
			return null;
		else
			return parent;
	}
	
	/**
	 * Sets the parent of this ShapeGroup
	 * @pre newParent is not null
	 * 		| newParent != null
	 * @post this ShapeGroup's parent is now set to newParent
	 * 		| this.getParentGroup() == newParent
	 */
	public void setParent(NonleafShapeGroup newParent) {
		parent = newParent;
	}
	
	
	/**
	 * Returns the coordinates in this ShapeGroups innercoordinate's system of the point whose coordinates in the globalcoordinate's system are the given coordinates
	 * @return innerCoordinates
	 * @post Undoes all of the scalings of this ShapeGroup's extent compared to its original extent and translations of this ShapeGroup and its ancestors extent compared to its original extent
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		double newX = globalCoordinates.getX();
		double newY = globalCoordinates.getY();
		ShapeGroup Pgroup = this;
		double scaleX = (double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth();
		double scaleY = (double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight();
		double translateX = 0;
		double translateY = 0;
		while (Pgroup.parent != null) {
			Pgroup = Pgroup.parent;
			translateX += (double)(globalCoordinates.getX() + translateX - Pgroup.getExtent().getLeft()) / 
					((double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth()) + Pgroup.getOriginalExtent().getLeft() - globalCoordinates.getX() - translateX;
			translateY += (double)(globalCoordinates.getY() + translateY - Pgroup.getExtent().getTop()) / 
					((double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight()) + Pgroup.getOriginalExtent().getTop() - globalCoordinates.getY() - translateY;
		}
	
		if (Pgroup != this) {
			newX = (globalCoordinates.getX() - getExtent().getLeft() + translateX) / 
					scaleX + getOriginalExtent().getLeft();
			newY = ((globalCoordinates.getY() - getExtent().getTop()) + translateY) / 
					scaleY + getOriginalExtent().getTop();
		}

		else {
			newX = (double)(globalCoordinates.getX() - getExtent().getLeft()) / ((double)newEx.getWidth() / ex.getWidth()) + 
					getOriginalExtent().getLeft();
			newY = (double)(globalCoordinates.getY() - getExtent().getTop()) / ((double)newEx.getHeight() / ex.getHeight()) + 
					getOriginalExtent().getTop();
		}
		
		IntPoint innerCoordinates = new DoublePoint(newX, newY).round();
		return innerCoordinates;
	}
	
	
	/**
	 * Returns the coordinates in the globalcoordinate system of the point whose coordinates in this ShapeGroups innercoordinate's system are the given coordinates
	 * @return globalCoordinates
	 * @post Excecutes all the transformations of this ShapeGroup's and its ancestors's extent compared to their original extent
	 */
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		double newX = innerCoordinates.getX();
		double newY = innerCoordinates.getY();
		ShapeGroup Pgroup = this;
		double scaleX = (double)newEx.getWidth() / ex.getWidth();
		double scaleY = (double)newEx.getHeight() / ex.getHeight();
		double translateX = 0;
		double translateY = 0;
		while (Pgroup.parent != null) {
			Pgroup = Pgroup.parent;
			scaleX *= (double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth();
			translateX += (double)(getExtent().getLeft() + translateX - Pgroup.getOriginalExtent().getLeft()) * 
					((double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth()) + Pgroup.getExtent().getLeft() - getExtent().getLeft() - translateX;
			scaleY *= (double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight();
			translateY += (double)(getExtent().getTop() + translateY - Pgroup.getOriginalExtent().getTop()) * 
					((double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight()) + Pgroup.getExtent().getTop() - getExtent().getTop() - translateY;
		}
		
		if (Pgroup != this) {
			newX = (double)(innerCoordinates.getX() - getOriginalExtent().getLeft()) * 
					scaleX + translateX + getExtent().getLeft();
			newY = (double)(innerCoordinates.getY() - getOriginalExtent().getTop()) * 
					scaleY + translateY + getExtent().getTop();
		}
		
		else {
			newX = (double)(innerCoordinates.getX() - getOriginalExtent().getLeft()) * 
					((double)newEx.getWidth() / ex.getWidth()) + getExtent().getLeft();
			newY = (double)(innerCoordinates.getY() - getOriginalExtent().getTop()) * 
					((double)newEx.getHeight() / ex.getHeight()) + getExtent().getTop();
		}
	
		IntPoint globalCoordinates = new DoublePoint(newX, newY).round();
		return globalCoordinates;
	}
	
	
	/**
	 * Returns the coordinates in this ShapeGroup's innercoordinate system of the vector whose coordinates in the globalcoordinate system are the given coordinates
	 * @return innerCoordinates
	 * @post scales the IntVectors of all objects with the scaling of the extent of its ancestors
	 */
	public IntVector toInnerCoordinates(IntVector globalCoordinates) {
		double widthChange = 1;
		double heightChange = 1;
		ShapeGroup Pgroup = this;
		 
		while (Pgroup.parent != null) {
			Pgroup = Pgroup.parent;
			widthChange *= (double)Pgroup.newEx.getWidth()/Pgroup.ex.getWidth();
			heightChange *= (double)Pgroup.newEx.getHeight()/Pgroup.ex.getHeight();
		}
		
		IntVector innerCoordinates = new IntVector((int)(globalCoordinates.getX() / widthChange), 
				(int)(globalCoordinates.getY() / heightChange));
		return innerCoordinates;
	}
	
	
	/**
	 * Sets the given extent as this ShapeGroup's new extent and keeps its previous extent expressed in this ShapeGroups outercoordinate system
	 * @post the previous extent becomes the old new extent and the new extent equals the given extent
	 * 		| getExtent() == newExtent
	 * @throws IllegalArgumentException when the given extent is null
	 * 		| !(newExtent == null)
	 */
	public void setExtent(Extent newExtent) {
		if (newExtent == null)
			throw new IllegalArgumentException("Extent cannot be null!");
		else
			if (ex == null) { 
				ex = newExtent;
				prevEx = newExtent;
			}
			else
				prevEx = newEx;
			newEx = newExtent;
	}
	
	/**
	 * Moves this shape group to the front of its parent's list of subgroups.
	 * 
	 * @throws UnsupportedOperationException if this shape group has no parent
	 *    | getParentGroup() == null
	 * @mutates_properties | getParentGroup().getSubgroups()
	 * @post | getParentGroup().getSubgroups().equals(
	 *       |     LogicalList.plusAt(LogicalList.minus(old(getParentGroup()).getSubgroups(), this), 0, this))
	 */
	public void bringToFront() {
		if (parent == null)
			throw new UnsupportedOperationException("no parent");
		
		List<ShapeGroup> groups = parent.getSubgroups();
		groups.remove(this);
		groups.add(0, this);
		parent.setSubgroups(groups);
	}
	
	
	/**
	 * Moves this shape group to the back of its parent's list of subgroups.
	 * 
	 * @throws UnsupportedOperationException if this shape group has no parent
	 *    | getParentGroup() == null
	 * @mutates_properties | getParentGroup().getSubgroups()
	 * @post | getParentGroup().getSubgroups().equals(
	 *       |     LogicalList.plus(LogicalList.minus(old(getParentGroup()).getSubgroups(), this), this))
	 */
	public void sendToBack() {
		if (parent == null)
			throw new UnsupportedOperationException("no parent");
		
		List<ShapeGroup> groups = parent.getSubgroups();
		groups.remove(this);
		groups.add(this);
		parent.setSubgroups(groups);
	}

	
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing the shapes contained directly or indirectly
	 * by this ShapeGroup expressed in this ShapeGroups outercoordinate system
	 * @post contains all of the drawing commands for all leaf and nonLeaf ShapeGroups
	 */
	public abstract java.lang.String getDrawingCommands();
	
	
	
	/**
	 * Gives the drawing commands for translating and scaling the ShapeGroups
	 * @post all coordinates are scaled with a factor widthFactor and heightFactor and moved over a distance changeX and changeY 
	 */
	public java.lang.String push(ShapeGroup subgroup){
		String result = "";
		
		double widthFactor = (double)subgroup.getExtent().getWidth()/subgroup.getOriginalExtent().getWidth();
		double heightFactor = (double)subgroup.getExtent().getHeight()/subgroup.getOriginalExtent().getHeight();
		
		
		if (!subgroup.newEx.getTopLeft().equals(subgroup.prevEx.getTopLeft())) {
			double changeR = subgroup.getExtent().getRight() -subgroup.getOriginalExtent().getRight();
			double changeB = subgroup.getExtent().getBottom() - subgroup.getOriginalExtent().getBottom();
			subgroup.changeXr = (1-(widthFactor-changeR/subgroup.getOriginalExtent().getWidth()))*subgroup.getOriginalExtent().getRight();
			subgroup.changeYb = (1-(heightFactor-changeB/subgroup.getOriginalExtent().getHeight()))*subgroup.getOriginalExtent().getBottom();
		}
		
			
		else if (!subgroup.newEx.getBottomRight().equals(subgroup.prevEx.getBottomRight())) {
			double changeL = -subgroup.getExtent().getLeft() + subgroup.getOriginalExtent().getLeft();
			double changeT = -subgroup.getExtent().getTop() + subgroup.getOriginalExtent().getTop();
			subgroup.changeXl = (1-(widthFactor-changeL/subgroup.getOriginalExtent().getWidth()))*subgroup.getOriginalExtent().getLeft();
			subgroup.changeYt = (1-(heightFactor-changeT/subgroup.getOriginalExtent().getHeight()))*subgroup.getOriginalExtent().getTop();
		}
				
		double changeX = subgroup.changeXr + subgroup.changeXl;
		double changeY = subgroup.changeYb + subgroup.changeYt;
		
		
		result += "pushTranslate " + changeX + " " + changeY + "\r\n";
		result += "pushScale " + widthFactor + " " + heightFactor + "\r\n";
		
		return result;
	}
	
}