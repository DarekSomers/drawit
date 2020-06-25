package drawit.shapegroups2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import drawit.IntPoint;


/**
 * Each instance of this class represents a ShapeGroup containing a ShapeGroup array
 * @invar For every NonleafShapeGroup object, nonleaf is not null
 * 		| getNonleaf() != null
 */
public class NonleafShapeGroup extends ShapeGroup {

	
	
	/**
	 * Contains the nonLeaf object
	 * @pre nonLeaf is not null
	 * 		| nonleaf != null
	 */
	private ShapeGroup[] nonLeaf;
	
	/**
	 * Initializes the ShapeGroup with the extent created from the values of the given extents contained in the ShapeGroup array 
	 * And also assigns the children and parent objects
	 */
	public NonleafShapeGroup(ShapeGroup[] subgroups) {
		nonLeaf = subgroups;
		ShapeGroup[] subgroup = nonLeaf;
		subgroup = nonLeaf;
		int minX = subgroup[0].getExtent().getLeft();
		int maxX = subgroup[0].getExtent().getRight();
		int minY = subgroup[0].getExtent().getBottom();
		int maxY = subgroup[0].getExtent().getTop();
		
		setFirst(nonLeaf[0]);
		setLast(nonLeaf[getSubgroupCount()-1]);
		
		for (int i = 0; i < getSubgroupCount(); i++) {
			nonLeaf[i].setParent(this);
			if (i > 0)
				nonLeaf[i].setPrevious(getSubgroup(i-1));
			if (i < getSubgroupCount()-1)
				nonLeaf[i].setNext(nonLeaf[i+1]);
			
			if (getSubgroup(i).getExtent().getLeft() < minX)
				minX = getSubgroup(i).getExtent().getLeft();
			if (getSubgroup(i).getExtent().getRight() > maxX)
				maxX = getSubgroup(i).getExtent().getRight();
			if (getSubgroup(i).getExtent().getTop() < minY)
				minY = getSubgroup(i).getExtent().getTop();
			if (getSubgroup(i).getExtent().getBottom() > maxY)
				maxY = getSubgroup(i).getExtent().getBottom();
		}
		setExtent(Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY));
	}
	
	/**
	 * Returns an array of this ShapeGroup's children
	 * @return nonLeaf
	 * @representationObject
	 */
	public ShapeGroup[] getNonleaf() {
		return nonLeaf;
	}
	
	
	/**
	 * Returns the list of subgroups of this ShapeGroup or null if this is a leaf ShapeGroup
	 * @return list || null
	 * @post contains the child ShapeGroups from the nonLeaf array
	 *	//	| IntStream.range(0, getSubgroupCount()).allMatch(i -> result.get(i).getOriginalExtent().equals(getNonleaf()[i].getOriginalExtent()))
	 * @creates | result
	 */
	public java.util.List<ShapeGroup> getSubgroups(){
		List<ShapeGroup> list = new ArrayList<ShapeGroup>();
		ShapeGroup shape = getFirst();
		while (shape != null) {
			list.add(shape);
			shape = shape.getNext();
		}
		return list;
	}
	
	
	/**
	 * Returns the number of subgroups of this nonLeaf ShapeGroup
	 * @return nonLeaf.length
	 */
	public int getSubgroupCount() {
		return nonLeaf.length;
	}
	
	/**
	 * Returns the subgroup at the given 0-based index in this nonLeaf ShapeGroup's list of subgroups
	 * @return return nonLeaf[index]
	 * @throws IllegalArgumentException when the index is greater than the array's length
	 * 		| !(index >= getSubgroupCount())
	 */
	public ShapeGroup getSubgroup(int index) {
		if (index >= nonLeaf.length)
			throw new IllegalArgumentException("Index out of bounds");
		else {
			ShapeGroup shape;
			if (getExtent() != null) {
				shape = getFirst();
				for (int i = 1; i <= index; i++)
					shape = shape.getNext();
			}
			else {
				shape = nonLeaf[index];
					
			}
			return shape;
		}
	}
	
	
	/**
	 * Returns the first subgroup in this nonLeaf ShapeGroup's list of subgroups whose extent cointains the given point expressed in this ShapeGroup's innercoordinates system 
	 * @return child || null
	 * @post Returns the first child that contains the given point, else returns null
	 * 		|  result == null || result == getSubgroups().stream().filter(child -> child.getExtent().contains(innerCoordinates)).findFirst().get()
	 * @inspects | getSubgroups()
	 */
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		for (ShapeGroup child: getSubgroups()) {
			if (child.getExtent().contains(innerCoordinates))
				return child;
		}
		return null;
	}
	
	
	/**
	 * Specific drawing commands for nonLeaf ShapeGroups
	 * @post contains all drawing commands for all leaf ShapeGroups inside all nonLeaf ShapeGroups
	 */
	public java.lang.String drawNonLeaf(ShapeGroup subgroup) {
		String result = "";
		ShapeGroup child = subgroup.getLast();
		while (child != null) {
			if (child instanceof NonleafShapeGroup) {
				result += push(child);
				result += drawNonLeaf(child);
				result += "popTransform \r\n";
				result += "popTransform \r\n";
				
				
			}
			else {	
				result += push(child);
				result += ((LeafShapeGroup) child).getShape().getDrawingCommands();
				result += "popTransform \r\n";
				result += "popTransform \r\n";
			}
			child = child.getPrevious();
		}
		return result;
	}
	
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing the shapes contained directly or indirectly
	 * by this ShapeGroup expressed in this ShapeGroups outercoordinate system
	 * @post contains all of the drawing commands for all leaf and nonLeaf ShapeGroups
	 */
	@Override
	public java.lang.String getDrawingCommands(){
		

		String result = "";
		
			result += push(this);
			result += drawNonLeaf(this);
			result += "popTransform \r\n";
			result += "popTransform \r\n";

		return result;
	}
	
	
	
}
