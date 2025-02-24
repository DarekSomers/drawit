package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.DoublePoint;
import drawit.DoubleVector;

class DoublePointTest {

	@Test
	void test() {
		DoublePoint v = new DoublePoint(6.6,9.9);
		
		assert v.getX()==6.6;
		assert v.getY()==9.9;
		
		DoublePoint v2 = new DoublePoint(1.1,2.2);
		DoubleVector v3 = new DoubleVector(1.1,2.2);
		
		assertEquals(v.plus(v3).getX(), 7.7, 0.1);
		assertEquals(v.plus(v3).getY(), 12.1, 0.1);
		//System.out.print(v.plus(v3).getX());
		//System.out.print(v.plus(v3).getY());
		
		assertEquals(v.minus(v2).getX(), 5.5, 0.1);
		assertEquals(v.minus(v2).getY(), 7.7, 0.1);

		//System.out.print(v.minus(v2).getX());
		//System.out.print(v.minus(v2).getY());
		
		DoublePoint d = new DoublePoint(2.3, 7.8);
		
		assert d.round().getX() == 2;
		assert d.round().getY() == 8;
	}

}
