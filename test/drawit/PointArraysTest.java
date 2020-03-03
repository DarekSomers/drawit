package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.PointArrays;
import org.junit.Assert;

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

		IntPoint[] ipCopy = PointArrays.copy(points);
		assert points[0] == ipCopy[0];
		assert points[1] == ipCopy[1];
		assert points[2] == ipCopy[2];
		assert points[3] == ipCopy[3];
		assert points[4] == ipCopy[4];
		
		assertEquals(PointArrays.insert(points, 2, new IntPoint(0,0))[2].getX(), 0);
		assertEquals(PointArrays.insert(points, 2, new IntPoint(0,0))[2].getY(), 0);
		
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
		}
	}

}
