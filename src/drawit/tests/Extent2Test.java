package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import drawit.shapegroups2.Extent;

class Extent2Test {

	@Test

	void test() {
		Extent ex = Extent.ofLeftTopWidthHeight(10, 10, 40, 40);
		Extent ex2 = Extent.ofLeftTopWidthHeight(0, 100, 100, 50);
		
		assert ex.equals(Extent.ofLeftTopWidthHeight(10, 10, 40, 40));
		assert !ex.equals(ex2);
		
		
		
		Extent ex3 = Extent.ofLeftTopWidthHeight(1, 2, 3, 4);
		Extent ex4 = Extent.ofLeftTopWidthHeight(50, 50, 10, 10);

		assertEquals(ex.hashCode(), 5581);
		assertEquals(ex2.hashCode(), -1849);
		assertEquals(ex3.hashCode(), 2663);
		assertEquals(ex4.hashCode(), 17161);
		
		
		assertEquals(ex.toString(), "Extent [left=10, top=10, width=40, height=40]");
		assertEquals(ex2.toString(), "Extent [left=0, top=100, width=100, height=50]");
		assertEquals(ex3.toString(), "Extent [left=1, top=2, width=3, height=4]");
		assertEquals(ex4.toString(), "Extent [left=50, top=50, width=10, height=10]");
		
		HashSet<Extent> hash = new HashSet<Extent>();
		hash.add(ex);
		hash.add(ex2);
		hash.add(ex3);
		hash.add(ex4);
		assert hash.contains(Extent.ofLeftTopWidthHeight(1, 2, 3, 4));
		
		ArrayList<Extent> al = new ArrayList<Extent>();
		al.add(ex);
		al.add(ex2);
		al.add(ex3);
		al.add(ex4);
		assert al.contains(Extent.ofLeftTopWidthHeight(0, 100, 100, 50));
	}

}
