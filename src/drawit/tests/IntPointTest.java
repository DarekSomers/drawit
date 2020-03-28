package drawit.tests;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.IntVector;

class IntPointTest { 

	@Test
	void test() {
		IntPoint p = new IntPoint(3,4);
		assert p.getX()==3;
		assert p.getY() ==4;
		
		
		assert p.equals(new IntPoint(3,4));
		
		IntPoint p2 = new IntPoint(-2,5);
		assert p.minus(p2).getX() == 5;
		assert p.minus(p2).getY() == -1;
		
		IntPoint p3 = new IntPoint(3,-1);
		
		IntPoint p4 = new IntPoint(3,2);

		assert p4.isOnLineSegment(p, p3) == true;
		assert p.isOnLineSegment(p2, p3) == false;
		
		assert p.asDoublePoint().getX() == 3;
		assert p.asDoublePoint().getY() == 4;

		
		IntVector v1 = new IntVector(2,3);
		assert p.plus(v1).getX() == 5;
		assert p.plus(v1).getY() == 7;
		
		
		IntPoint a = new IntPoint(1,1);
		IntPoint b = new IntPoint(4,1);
		IntPoint c = new IntPoint(1,4);
		IntPoint d = new IntPoint(5,4);
		
		assert IntPoint.lineSegmentsIntersect(a, b, c, d) == false;
		assert IntPoint.lineSegmentsIntersect(a, d, c, b) == true;


		
	}

}
