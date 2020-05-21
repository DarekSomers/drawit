package drawit.shapes2;

import drawit.IntPoint;
import drawit.shapes2.ControlPoint;
import drawit.shapes2.Controlpoints;

public class RoundedPolygonShape implements Shape {
	
private drawit.shapegroups2.ShapeGroup parent;
	
	private drawit.RoundedPolygon polygon;
	
	public RoundedPolygonShape(drawit.shapegroups2.ShapeGroup parent, drawit.RoundedPolygon polygon) {
		this.parent = parent;
		this.polygon = polygon;
	}

	public drawit.RoundedPolygon getPolygon() {
		return polygon;
	}
	
	@Override
	public drawit.shapegroups2.ShapeGroup getParent() {
		return parent;
	}

	@Override
	public boolean contains(drawit.IntPoint p) {
		if (polygon.contains(p))
			return true;
		else
			return false;
	}

	@Override
	public String getDrawingCommands() {
		return polygon.getDrawingCommands();
	}

	@Override
	public IntPoint toShapeCoordinates(IntPoint p) {
		if (parent == null)
			return p;
		else
			return parent.toInnerCoordinates(p);
	}

	@Override
	public IntPoint toGlobalCoordinates(IntPoint p) {
		if (parent == null)
			return p;
		else
			return parent.toGlobalCoordinates(p);
	}

	@Override
	public ControlPoint[] createControlPoints() {
		ControlPoint[] points = new ControlPoint[polygon.getVertices().length];
		for (int i = 0; i < polygon.getVertices().length; i++) {
			ControlPoint point = new Controlpoints(polygon.getVertices()[i], i, polygon, parent);
			points[i] = point;
		}
		return points;
	}

	
	
}
