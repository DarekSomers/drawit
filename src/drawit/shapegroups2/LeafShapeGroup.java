package drawit.shapegroups2;

import drawit.IntPoint;
import drawit.RoundedPolygon;

public class LeafShapeGroup extends ShapeGroup {

	
	/**
	 * Contains the leaf RoundedPolygon object
	 * @invar leaf is null when nonLeaf is not null and vice versa
	 * 		| (nonLeaf == null && leaf != null) || (leaf == null && nonLeaf != null)
	 */
	private RoundedPolygon leaf;
	
	/**
	 * Initializes the ShapeGroup with the extent created from the vertices of the given shape/RoundedPolygon
	 */
	public LeafShapeGroup(RoundedPolygon shape) {
		leaf = shape;
		int minX = leaf.getVertices()[0].getX();
		int maxX = leaf.getVertices()[0].getX();
		int minY = leaf.getVertices()[0].getY();
		int maxY = leaf.getVertices()[0].getY();
		
		for (IntPoint vertix: leaf.getVertices()) {
			if (vertix.getX() < minX)
				minX = vertix.getX();
			if (vertix.getX() > maxX)
				maxX = vertix.getX();
			if (vertix.getY() < minY)
				minY = vertix.getY();
			if (vertix.getY() > maxY)
				maxY = vertix.getY();
		}
		setExtent(Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY));
	}
	
	/**
	 * Returns the shape directly contained by this ShapeGroup or null if this is a nonLeaf ShapeGroup
	 * @return leaf
	 */
	public RoundedPolygon getShape() {
		return leaf;
	}
	
	
	/**
	 * Returns a textual representation of a sequence of drawing commands for drawing the shapes contained directly or indirectly
	 * by this ShapeGroup expressed in this ShapeGroups outercoordinate system
	 * @post contains all of the drawing commands for all leaf and nonLeaf ShapeGroups
	 */
	public java.lang.String getDrawingCommands(){
		

		String result = "";
			
		result += push(this);
		
		result += leaf.getDrawingCommands();
		
		result += "popTransform \r\n";
		result += "popTransform \r\n";

		return result;
	}
	
	
	
}
