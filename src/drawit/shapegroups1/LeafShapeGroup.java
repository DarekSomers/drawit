package drawit.shapegroups1;

import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class LeafShapeGroup extends ShapeGroup{
	

	private RoundedPolygon leaf;
	
	public RoundedPolygon getShape() {
		return leaf;
	}
	
	public void setShape(RoundedPolygon newShape) {
		this.leaf = newShape;
	}
	

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
		setExtent(Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY));
	}
	
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
	



