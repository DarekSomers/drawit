package drawit.shapegroups2;

import java.util.ArrayList;
import java.util.List;
import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;


/**
 * @invar this Shapegroup does not have the same ShapeGroup as a child twice
 * 		//|  LogicalList.distinct(getSubgroups())
 * @invar this ShapeGroup's children have this ShapeGroup as their parent
 * 		|  getSubgroups().stream().allMatch(child -> child.getParentGroup() == this)
 * @invar this Shapegroup is a root ShapeGroup or else it is among parent's children
 * 		|  getParentGroup() == null || getParentGroup().getSubgroups().contains(this)
 * @invar this Shapegroup does not have itself as an ancestor
 * 		|  !getSubgroups().contains(this)
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
	 */
	private ShapeGroup parent;
	
	/**
	 * Contains the first ShapeGroup contained in parent's subgroups list or null when it's empty
	 */
	private ShapeGroup first; //Only for non-leaves/parents
	/**
	 * Contains the last ShapeGroup contained in parent's subgroups list or null when it's empty
	 */
	private ShapeGroup last; //Only for non-leaves/parents
	/**
	 * Contains the next/following ShapeGroup contained in parent's subgroups list or null when there is none
	 */
	private ShapeGroup next; //Only for children
	/**
	 * Contains the previous ShapeGroup contained in parent's subgroups list or null when there is none
	 */
	private ShapeGroup previous; //Only for children
	
	
	
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
	public Extent getExtent() {
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
	 * Returns the ShapeGroup that directly contains this ShapeGroup or null if no shapeGroup directly contains this ShapeGroup
	 * @return parent
	 * @post if parent is null, the given ShapeGroup is a root ShapeGroup
	 * 		| result == null || result != null
	 */
	public ShapeGroup getParentGroup() {
		return parent;
	}
	
	
	public void setParent(ShapeGroup newParent) {
		this.parent = newParent;
	}
	
	
	public ShapeGroup getFirst() {
		return first;
	}
	
	public void setFirst(ShapeGroup newFirst) {
		this.first = newFirst;
	}
	
	
	public ShapeGroup getLast() {
		return last;
	}
	
	public void setLast(ShapeGroup newLast) {
		this.last = newLast;
	}
	
	
	public ShapeGroup getNext() {
		return next;
	}

	public void setNext(ShapeGroup newNext) {
		this.next = newNext;
	}
	
	
	public ShapeGroup getPrevious() {
		return previous;
	}
	
	public void setPrevious(ShapeGroup newPrev) {
		this.previous = newPrev;
	}
	
	
	
	
	/**
	 * Returns the coordinates in this ShapeGroups innercoordinate's system of the point whose coordinates in the globalcoordinate's system are the given coordinates
	 * @return innerCoordinates
	 * @post Undoes all of the scalings of this ShapeGroup's extent compared to its original extent and translations of this ShapeGroup and its ancestors extent compared to its original extent
	 */
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		double newX;
		double newY;
		ShapeGroup Pgroup = this;
		double scaleX = (double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth();
		double scaleY = (double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight();
		double translateX = 0;
		double translateY = 0;
		while (Pgroup.parent != null) {
			Pgroup = Pgroup.parent;
			//scaleX *= (double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth();
			translateX += (double)(globalCoordinates.getX() + translateX - Pgroup.getExtent().getLeft()) / 
					((double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth()) + Pgroup.getOriginalExtent().getLeft() - globalCoordinates.getX() - translateX;
			//scaleY *= (double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight();
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
		double newX;
		double newY;
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
	 * @post scales the IntVectors of all objects with the scaling of the extent and its ancestors
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
	 * Moves this ShapeGroup to the front of its parent's list of subgroups
	 * @mutates_properties | getSubgroups()
	 * @post this ShapeGroup is the first element of its parent's children array 
	 * 		| this == ((NonleafShapeGroup) getParentGroup()).getSubgroup(0)
	 * @post the order of the other child ShapeGroups in the parent's children array is retained
	 * 		
	 */
	public void bringToFront() {
		if (this != parent.first) {
			if (next != null) {
				previous.next = next;
				next.previous = previous;
			}
			else {
				previous.next = null;
				parent.last = previous;
			}
			parent.first.previous = this;
			next = parent.first;
			previous = null;
			parent.first = this;
		}
	}
	
	
	/**
	 * Moves this ShapeGroup to the back of its parent's list of subgroups
	 * @mutates_properties | getSubgroups()
	 * @post this ShapeGroup is the last element of its parent's children array 
	 * 		| this == ((NonleafShapeGroup) getParentGroup()).getSubgroup(((NonleafShapeGroup) getParentGroup()).getSubgroupCount()-1)
	 * @post the order of the other child ShapeGroups in the parent's children array is retained
	 * 		
	 */
	public void sendToBack() {
		if (this != parent.last) {
			if (previous != null) {
				previous.next = next;
				next.previous = previous;
			}
			else {
				next.previous = null;
				parent.first = next;
			}
			parent.last.next = this;
			previous = parent.last;
			next = null;
			parent.last = this;
		}
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