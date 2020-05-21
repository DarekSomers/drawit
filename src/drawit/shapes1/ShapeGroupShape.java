package drawit.shapes1;

import drawit.IntPoint;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

public class ShapeGroupShape implements Shape {

	private drawit.shapegroups1.ShapeGroup shapegroup;
	
	
	public ShapeGroupShape(drawit.shapegroups1.ShapeGroup group) {
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
	
			return shapegroup.toInnerCoordinates(p);

	}

	@Override
	public IntPoint toGlobalCoordinates(IntPoint p) {
		
			return shapegroup.toGlobalCoordinates(p);
		
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
