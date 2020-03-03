package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PointArraysTest { 

	@Test
	void test() {
		IntPoint p1 = new IntPoint(3,4);
		IntPoint p2 = new IntPoint(1,2);
		
		IntPoint[] ip = new IntPoint[2];
		ip[0] =  p1;
		ip[1] = p2;
		
		IntPoint[] ipCopy = PointArrays.copy(ip);
		assert ip[0] == ipCopy[0];
		assert ip[1] == ipCopy[1];
		
	}

}
