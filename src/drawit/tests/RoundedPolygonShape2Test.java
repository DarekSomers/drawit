package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;
import drawit.shapegroups2.Extent;
import drawit.shapegroups2.LeafShapeGroup;
import drawit.shapegroups2.ShapeGroup;
import drawit.shapes2.ControlPoint;
import drawit.shapes2.RoundedPolygonShape;

class RoundedPolygonShape2Test {
	
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
		
		ShapeGroup leaf1 = new LeafShapeGroup(pol1);
		LeafShapeGroup leaf2 = new LeafShapeGroup(pol2);
		LeafShapeGroup leaf3 = new LeafShapeGroup(pol3);
		
		RoundedPolygonShape RPS1 = new RoundedPolygonShape(leaf1, pol1);
		
		leaf1.setExtent(Extent.ofLeftTopRightBottom(5, 5, 25, 25));
		
		//getPolygon/getParent
		assert RPS1.getPolygon() == pol1;
		assert RPS1.getParent() == leaf1;
		
		//contains
		assert RPS1.contains(p(5,5));
		assert !RPS1.contains(p(11,11));
		
		//getDrawingCommands
		Assert.assertEquals(pol1.getDrawingCommands(), RPS1.getDrawingCommands());
		
		//toShape-/GlobalCoordinates
		assertEquals(5,5	, RPS1.toShapeCoordinates(p(15,15)));
		assertEquals(15,15, RPS1.toGlobalCoordinates(p(5,5)));
		
		//createControlPoints
		ControlPoint[] ctrlPoints = RPS1.createControlPoints();
		System.out.println(ctrlPoints.length);
		System.out.println(ctrlPoints[0].getLocation());
		
		ctrlPoints[0].remove();
		for (IntPoint point: pol1.getVertices()) {
			System.out.println(point.getX() + " " + point.getY());
		}
		
		pol1.insert(0, p(0,0));
		ctrlPoints = RPS1.createControlPoints();
		
		ctrlPoints[2].move(new IntVector(5,5));
		
		for (IntPoint point: pol1.getVertices()) {
			System.out.println(point.getX() + " " + point.getY());
		}
		assertEquals(12,12, pol1.getVertices()[2]);
	}
}
