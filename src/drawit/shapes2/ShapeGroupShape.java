package drawit.shapes2;

import drawit.IntPoint;
import drawit.shapegroups2.LeafShapeGroup;
import drawit.shapegroups2.ShapeGroup;
import drawit.shapes2.ControlPoint;
import drawit.shapes2.Controlpoints;

public class ShapeGroupShape implements Shape {

private drawit.shapegroups2.ShapeGroup shapegroup;
	
	
	public ShapeGroupShape(drawit.shapegroups2.ShapeGroup group) {
		shapegroup = group;
	}
	
	
	public ShapeGroup getShapeGroup() {
		return shapegroup;
	}
	

	@Override
	public ShapeGroup getParent() {
		return shapegroup.getParentGroup();
	}

	@Override
	public boolean contains(IntPoint p) {
		if (shapegroup.getExtent().contains(p))
			return true;
		else 
			return false;
	}

	@Override
	public String getDrawingCommands() {
		return shapegroup.getDrawingCommands();
	}

	@Override
	public IntPoint toShapeCoordinates(IntPoint p) {
	
		if (shapegroup.getParentGroup() == null)
			return p;
		else
			return shapegroup.getParentGroup().toInnerCoordinates(p);

	}

	@Override
	public IntPoint toGlobalCoordinates(IntPoint p) {
		
		if (shapegroup.getParentGroup() == null)
			return p;
		else
			return shapegroup.getParentGroup().toGlobalCoordinates(p);
		
	}

	@Override
	public ControlPoint[] createControlPoints() {
		ControlPoint[] corners = new ControlPoint[2];
		ControlPoint topleft;
		ControlPoint bottomright;
		
		topleft = new Controlpoints(shapegroup.getExtent().getTopLeft(), 0, null, shapegroup);
		bottomright = new Controlpoints(shapegroup.getExtent().getBottomRight(), 1, null, shapegroup);
		
		corners[0] = topleft;
		corners[1] = bottomright;
		return corners;
	}
	
}
