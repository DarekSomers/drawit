package drawit.tests;

import org.junit.jupiter.api.Test;

import drawit.DoubleVector;

class DoubleVectorTest { 

	@Test
	void test() {
		DoubleVector v = new DoubleVector(6,9);
		assert v.getX()==6;
		assert v.getY()==9;
		
		
		DoubleVector v2 = v.scale(2);
		assert v2.getX() == 12;
		assert v2.getY() == 18;
		
		assert v.plus(v2).getX() == 18;
		assert v.plus(v2).getY() == 27;
		
		assert v.getSize() == Math.sqrt(117);
		
		assert v.dotProduct(v2) == 234;
		
		assert v.crossProduct(v2) == 0;
		
		DoubleVector v3 = new DoubleVector(0,1);
		assert v3.asAngle() == Math.PI/2;
	}

}
