package drawit.shapegroups1;

import drawit.IntPoint;
import drawit.RoundedPolygon;


/**
 * Each instance of this class represents a ShapeGroup containing a RoundedPolygon object
 * @invar For every LeafShapeGroup, leaf is not null
 * 		| getShape() != null
 */
public class LeafShapeGroup extends ShapeGroup{
	
	/**
	 * The RoundedPolygon object contained by this ShapeGroup
	 * @invar leaf cannot be null
	 * 		| leaf != null
	 */
	private RoundedPolygon leaf;
	
	/**
	 * Returns the shape directly contained by this ShapeGroup 
	 * @return leaf
	 */
	public RoundedPolygon getShape() {
		return leaf;
	}
	
	/**
	 * Sets the leaf value of this ShapeGroup
	 * @pre newShape is not null
	 * 		| newShape != null
	 * @post This ShapeGroup's leaf value is now equal to newShape
	 * 		| getShape() == newShape
	 */
	public void setShape(RoundedPolygon newShape) {
		this.leaf = newShape;
	}
	
	/**
	 * Initializes the ShapeGroup with the extent created from the vertices of the given shape/RoundedPolygon
	 * @pre shape is not null
	 * 		| shape != null
	 * @creates | thisExtent
	 * @inspects | getShape().getVertices()
	 */
	public LeafShapeGroup(RoundedPolygon shape) {
		setShape(shape);
		int minX = getShape().getVertices()[0].getX();
		int maxX = getShape().getVertices()[0].getX();
		int minY = getShape().getVertices()[0].getY();
		int maxY = getShape().getVertices()[0].getY();
		
		for (IntPoint vertix: getShape().getVertices()) {
			if (vertix.getX() < minX)
				minX = vertix.getX();
			if (vertix.getX() > maxX)
				maxX = vertix.getX();
			if (vertix.getY() < minY)
				minY = vertix.getY();
			if (vertix.getY() > maxY)
				maxY = vertix.getY();
		}
		Extent thisExtent = Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY);
		setExtent(thisExtent);
	}
	
	/**
	 * Returns the textual representation of the LeafShapeGroup and by extent the RoundedPolygon object
	 * @post contains the drawing commands for only this LeafShapeGroup and its RoundedPolygon
	 */
	@Override
	public java.lang.String getDrawingCommands() {
		

		String result = "";
				
		result += push(this);
		
		result += getShape().getDrawingCommands();
		
		result += "popTransform \r\n";
		result += "popTransform \r\n";

		return result;
	}
}
