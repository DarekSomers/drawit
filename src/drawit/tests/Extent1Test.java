package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import drawit.shapegroups1.Extent;
import junit.framework.Assert;

class Extent1Test {

	@Test
	void test() {
		Extent ex = Extent.ofLeftTopRightBottom(10, 10, 50, 50);
		Extent ex2 = Extent.ofLeftTopRightBottom(0, 100, 100, 150);
		
		assert ex.equals(Extent.ofLeftTopRightBottom(10, 10, 50, 50));
		assert !ex.equals(ex2);
		
		
		
		Extent ex3 = Extent.ofLeftTopRightBottom(1, 2, 3, 4);
		Extent ex4 = Extent.ofLeftTopRightBottom(50, 50, 10, 10);

		assertEquals(ex.hashCode(), 5921);
		assertEquals(ex2.hashCode(), 6451);
		assertEquals(ex3.hashCode(), 2817);
		assertEquals(ex4.hashCode(), 21921);
		
		
		assertEquals(ex.toString(), "Extent [left=10, top=10, right=50, bottom=50]");
		assertEquals(ex2.toString(), "Extent [left=0, top=100, right=100, bottom=150]");
		assertEquals(ex3.toString(), "Extent [left=1, top=2, right=3, bottom=4]");
		assertEquals(ex4.toString(), "Extent [left=50, top=50, right=10, bottom=10]");
		
		HashSet<Extent> hash = new HashSet<Extent>();
		hash.add(ex);
		hash.add(ex2);
		hash.add(ex3);
		hash.add(ex4);
		assert hash.contains(Extent.ofLeftTopRightBottom(1, 2, 3, 4));
		
		ArrayList<Extent> al = new ArrayList<Extent>();
		al.add(ex);
		al.add(ex2);
		al.add(ex3);
		al.add(ex4);
		assert al.contains(Extent.ofLeftTopRightBottom(0, 100, 100, 150));
	
	}

}
