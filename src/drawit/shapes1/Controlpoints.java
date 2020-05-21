package drawit.shapes1;

import drawit.DoubleVector;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;

public class Controlpoints implements ControlPoint {
	
	private drawit.IntPoint point;
	private final int index;
	private drawit.RoundedPolygon polygon;
	private drawit.shapegroups1.ShapeGroup shape;

	public Controlpoints(drawit.IntPoint vertix, int index, drawit.RoundedPolygon polygon, drawit.shapegroups1.ShapeGroup shape) {
		this.point = vertix;
		this.index = index;
		this.polygon = polygon;
		this.shape = shape;
	}
	
	@Override
	public IntPoint getLocation() {
		return point;
	}

	@Override
	public void remove() {
		
		int index = 0;
		if (polygon != null) {
			for (IntPoint V: polygon.getVertices()) {
				if (V.getX() == point.getX() && V.getY() == point.getY())
					break;
				index++;
			}
			if (index == (polygon.getVertices().length))
				throw new UnsupportedOperationException("This polygon has no such vertix");
			else 
				polygon.remove(index);
		}
		else {
			while (!(shape instanceof LeafShapeGroup) && ((NonleafShapeGroup) shape).getSubgroupAt(point) != null) {
				shape = ((NonleafShapeGroup) shape).getSubgroupAt(point);
			}
			if (shape instanceof LeafShapeGroup) {
				for (int i = 0; i < ((LeafShapeGroup) shape).getShape().getVertices().length; i++) {
					if (((LeafShapeGroup) shape).getShape().getVertices()[i].getX() == point.getX() && ((LeafShapeGroup) shape).getShape().getVertices()[i].getY() == point.getY()) {
						((LeafShapeGroup) shape).getShape().remove(i);
						break;
					}
				}
			}
		}
		
	}

	@Override
	public void move(IntVector delta) {
		IntPoint pointy;
		DoubleVector deltor = delta.asDoubleVector();
		if (polygon != null) {
			if (shape!=null) {
				delta.setX((int) (deltor.getX()/ (double) shape.getExtent().getWidth() * (double) shape.getOriginalExtent().getWidth()));
				delta.setY((int) (deltor.getY()/ (double) shape.getExtent().getHeight() * (double) shape.getOriginalExtent().getHeight()));
				pointy = point.plus(shape.toInnerCoordinates(delta));
			}
			else
				pointy = point.plus(delta);
			//IntPoint[] newVertices = new IntPoint[polygon.getVertices().length];
			for (int i = 0; i < polygon.getVertices().length; i++) {
				if (i == index) {
					polygon.update(i, pointy);
//					newVertices[i] = point.plus(delta);
					
				}
//				else
//					newVertices[i] = polygon.getVertices()[i];
			}
			//polygon.setVertices(newVertices);
		}
		
		else {
			pointy = point.plus(shape.toInnerCoordinates(delta));
			if (index == 0)
				shape.setExtent(Extent.ofLeftTopRightBottom(pointy.getX(), pointy.getY(), 
						shape.getExtent().getRight(), shape.getExtent().getBottom()));
			else
				shape.setExtent(Extent.ofLeftTopRightBottom(shape.getExtent().getLeft(), shape.getExtent().getTop(), 
						pointy.getX(), pointy.getY()));
		}
		
		
	}

}
