package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.FastRoundedPolygonContainsTestStrategy;
import drawit.IntPoint;
import drawit.PreciseRoundedPolygonContainsTestStrategy;
import drawit.RoundedPolygon;

class RoundedPolygonContainsTestStrategyTest {

	
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
		FastRoundedPolygonContainsTestStrategy fastTest = new FastRoundedPolygonContainsTestStrategy();
		PreciseRoundedPolygonContainsTestStrategy preciseTest = new PreciseRoundedPolygonContainsTestStrategy();
		
		assert fastTest.contains(pol1, p(5,5)) == true;
		assert fastTest.contains(pol1, p(7,20)) == false;
		assert preciseTest.contains(pol1, p(5,5));
		assert preciseTest.contains(pol1, p(7,20)) == false;
		assert fastTest.contains(pol3, p(25,45));
		assert preciseTest.contains(pol3, p(25,45)) == false;
		assert preciseTest.contains(pol3, p(40,50));
	}

}
