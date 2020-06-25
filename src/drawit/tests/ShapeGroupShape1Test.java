package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.ShapeGroup;
import drawit.shapes1.ControlPoint;
import drawit.shapes1.ShapeGroupShape;
import org.junit.Assert;

class ShapeGroupShape1Test {

	static IntPoint p(int x, int y) { return new IntPoint(x, y); }
	
	static void assertEquals(int x, int y, IntPoint p) {
		assert p.getX() == x;
		assert p.getY() == y;
	}
	
	@Test
	void test() {

		IntPoint[] vert1 = {p(0,0), p(10,0), p(10,10), p(0,10)};
		IntPoint[] vert2 = {p(2,6), p(12,6), p(12,16), p(2,16)};
		IntPoint[] vert3 = {p(20,20), p(50,20), p(40,50)};
		
		RoundedPolygon pol1 = new RoundedPolygon();
		RoundedPolygon pol2 = new RoundedPolygon();
		RoundedPolygon pol3 = new RoundedPolygon();
		{pol1.setVertices(vert1); 
		pol2.setVertices(vert2);
		pol3.setVertices(vert3);}
		
		LeafShapeGroup leaf1 = new LeafShapeGroup(pol1);
		LeafShapeGroup leaf2 = new LeafShapeGroup(pol2);
		LeafShapeGroup leaf3 = new LeafShapeGroup(pol3);
		
		ShapeGroup[] leafs = {leaf2, leaf3};
		NonleafShapeGroup nonleaf1 = new NonleafShapeGroup(leafs);
		ShapeGroupShape shape1 = new ShapeGroupShape(nonleaf1);
		
		ShapeGroup[] nonleaf = {leaf1, nonleaf1};
		NonleafShapeGroup nonleaf2 = new NonleafShapeGroup(nonleaf);
		ShapeGroupShape shape2 = new ShapeGroupShape(nonleaf2);

		//getShapeGroup
		assert shape1.getShapeGroup() == nonleaf1;
		assert shape2.getShapeGroup() == nonleaf2;
		
		//getParent
		assert shape1.getParent() == nonleaf2;
		assert shape2.getParent() == null;
		
		//contains
		assert shape1.contains(p(30,40));
		assert !shape1.contains(p(0,0));
		assert shape2.contains(p(0,0));
		
		//getDrawingCommands
		Assert.assertEquals(nonleaf1.getDrawingCommands(), shape1.getDrawingCommands());
		Assert.assertEquals(nonleaf2.getDrawingCommands(), shape2.getDrawingCommands());
		
		//toShape-/GlobalCoordinates
		shape1.getShapeGroup().setExtent(Extent.ofLeftTopRightBottom(1, 3, 25, 25));
		assertEquals(5,5, shape1.toShapeCoordinates(p(5,5)));
		assertEquals(5,5, shape1.toGlobalCoordinates(p(5,5)));
		assertEquals(5,5, shape2.toShapeCoordinates(p(5,5)));
		assertEquals(5,5, shape2.toGlobalCoordinates(p(5,5)));
		
		//createControlPoints
		ControlPoint[] ctrlPoints = shape2.createControlPoints();
		System.out.println(ctrlPoints.length);
		System.out.println(ctrlPoints[0].getLocation());
		
		ctrlPoints[0].remove();
		for (IntPoint point: pol1.getVertices()) {
			System.out.println(point.getX() + " " + point.getY());
		}
		
		pol1.insert(0, p(0,0));
		ctrlPoints = shape2.createControlPoints();
		
		ctrlPoints[1].move(new IntVector(5,5));
		
		
		assertEquals(55,55, shape2.getShapeGroup().getExtent().getBottomRight());
		
		
		
		
		
	}
}
