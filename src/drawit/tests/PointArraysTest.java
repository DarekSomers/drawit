package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.PointArrays;

class PointArraysTest { 

	@Test
	void test() {
		IntPoint p1 = new IntPoint(3,4);
		IntPoint p2 = new IntPoint(1,2);
		IntPoint p3 = new IntPoint(-3,1);
		IntPoint p4 = new IntPoint(2,-4);
		IntPoint p5 = new IntPoint(-1,-1);
		
		IntPoint[] points = new IntPoint[5];
		points[0] = p1;
		points[1] = p2;
		points[2] = p3;
		points[3] = p4;
		points[4] = p5;
		
		IntPoint[] pointss = new IntPoint[6];
		pointss[0] = p1;
		pointss[1] = p2;
		pointss[3] = p3;
		pointss[4] = p4;
		pointss[5] = p5;
		pointss[2] = new IntPoint(0,0);
		
		IntPoint[] pointsss = new IntPoint[5];
		pointsss[0] = p1;
		pointsss[1] = p2;
		pointsss[3] = p3;
		pointsss[4] = p5;
		pointsss[2] = new IntPoint(0,0);
		
		IntPoint[] pointssss = new IntPoint[5];
		pointssss[0] = p1;
		pointssss[1] = p2;
		pointssss[2] = p3;
		pointssss[3] = p4;
		pointssss[4] = new IntPoint(99999,99999);
		
		IntPoint[] pointsssss = new IntPoint[5];
		pointsssss[0] = new IntPoint(0,0);
		pointsssss[1] = p2;
		pointsssss[2] = p3;
		pointsssss[3] = p4;
		pointsssss[4] = p5;
		
		IntPoint[] pointssssss = new IntPoint[5];
		pointssssss[0] = p1;
		pointssssss[1] = p2;
		pointssssss[2] = p3;
		pointssssss[3] = new IntPoint(69,96);
		pointssssss[4] = p5;


		IntPoint[] ipCopy = PointArrays.copy(points);
		assert points[0] == ipCopy[0];
		assert points[1] == ipCopy[1];
		assert points[2] == ipCopy[2];
		assert points[3] == ipCopy[3];
		assert points[4] == ipCopy[4];
		
		
		for (int i = 0; i < points.length; i++) {
			assertEquals(PointArrays.insert(points, 2, new IntPoint(0,0))[i].getX(), pointss[i].getX());
			assertEquals(PointArrays.insert(points, 2, new IntPoint(0,0))[i].getY(), pointss[i].getY());
		}	
		
		for (int j = 0; j < pointsss.length; j++) {
			assertEquals(PointArrays.remove(pointss, 4)[j].getX(), pointsss[j].getX());
			assertEquals(PointArrays.remove(pointss, 4)[j].getY(), pointsss[j].getY());
		}
		
		for (int i = 0; i < points.length; i++) {
			assertEquals(PointArrays.update(points, 4, new IntPoint(99999,99999))[i].getX(), pointssss[i].getX());
			assertEquals(PointArrays.update(points, 4, new IntPoint(99999,99999))[i].getY(), pointssss[i].getY());
			assertEquals(PointArrays.update(points, 0, new IntPoint(0,0))[i].getX(), pointsssss[i].getX());
			assertEquals(PointArrays.update(points, 0, new IntPoint(0,0))[i].getY(), pointsssss[i].getY());
			assertEquals(PointArrays.update(points, 3, new IntPoint(69,96))[i].getX(), pointssssss[i].getX());
			assertEquals(PointArrays.update(points, 3, new IntPoint(69,96))[i].getY(), pointssssss[i].getY());
		}
		IntPoint[] shorty = {p1,p2};
		IntPoint[] fonkyTriangle = {new IntPoint(0,0), new IntPoint(1,0), new IntPoint(3,0), new IntPoint(1,3), new IntPoint(2,0), new IntPoint(-1,3)};
		IntPoint[] notSoFonkyTriangle = {new IntPoint(0,0), new IntPoint(1,0), new IntPoint(1,0), new IntPoint(1,3)};
		IntPoint[] unCoollist = {new IntPoint(1,1),new IntPoint(1,4),new IntPoint(4,1),new IntPoint(5,4)};
		IntPoint[] coollist = {new IntPoint(1,1),new IntPoint(4,1),new IntPoint(4,4),new IntPoint(1,4)};
		IntPoint[] lessCoollist = {new IntPoint(1,1),new IntPoint(4,1), new IntPoint(-3,2), new IntPoint(2,3), new IntPoint(5,3), new IntPoint(4,4),new IntPoint(1,4)};
		
		System.out.print(PointArrays.checkDefinesProperPolygon(unCoollist));
		
		assert PointArrays.checkDefinesProperPolygon(shorty) == "The given array does not contain enough points to create a polygon!";
		assert PointArrays.checkDefinesProperPolygon(fonkyTriangle) == "The array contains one or more points that lie on the edge of the polygon.";
		assert PointArrays.checkDefinesProperPolygon(notSoFonkyTriangle) == "The array contains two or more points that coincide.";
		assert PointArrays.checkDefinesProperPolygon(unCoollist) == "A proper polygon cannot contain intersecting edges.";
		assert PointArrays.checkDefinesProperPolygon(coollist) == null;
		assert PointArrays.checkDefinesProperPolygon(lessCoollist) == "A proper polygon cannot contain intersecting edges.";
		
		
	}

}
