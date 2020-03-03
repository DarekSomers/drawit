package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.DoubleVector;
import drawit.IntVector;

class IntVectorTest { 

	@Test
	void test() {
			IntVector v = new IntVector(6,9);
			assert v.getX()==6;
			assert v.getY()==9;
			IntVector v2 = new IntVector(1,2);
			
	
			
			
			assert v.dotProduct(v2) == 24;
			
			assert v.crossProduct(v2) == 3;
			
			
			assert v.isCollinearWith(v2) == false;
			IntVector v3 = new IntVector (2,3);
			assert v.isCollinearWith(v3) == true;
			
			DoubleVector dv = new DoubleVector(6,9);
			
			assert v.asDoubleVector().getX() == dv.getX();
			assert v.asDoubleVector().getY() == dv.getY();
		
	}

}
