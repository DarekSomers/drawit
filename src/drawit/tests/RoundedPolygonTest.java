package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.PointArrays;
import drawit.RoundedPolygon;

class RoundedPolygonTest {

	@Test
	void test() {
		IntPoint a = new IntPoint(1,1);
		IntPoint b = new IntPoint(2,10);
		IntPoint c = new IntPoint(10,6);
		IntPoint test = new IntPoint(4,5);
		IntPoint test2 = new IntPoint(4,8);
		IntPoint d = new IntPoint(4,3);
		IntPoint e = new IntPoint(3,0);
		IntPoint[] vertixes = {a,b,c,d};
		IntPoint[] vertixxes = {e,a,b,c,d};
		IntPoint[] vertixxxes = {e,b,c,d};
		IntPoint[] verties = {a,b,c};
		IntPoint[] vertis = {a,b};
		IntPoint[] testest = {test2,test,d};
		
		RoundedPolygon poly = new RoundedPolygon();
		poly.setVertices(vertixes);
		poly.setRadius(2);
		
		RoundedPolygon polly = new RoundedPolygon();
		polly.setVertices(PointArrays.copy(vertixes));
		polly.setRadius(5);
		
		//Only works without IllegalArgumentException, gives error otherwise
		//RoundedPolygon pollly = new RoundedPolygon();
		//pollly.setVertices(PointArrays.copy(vertis));
		//pollly.setRadius(3);
		
		//Only works without IllegalArgumentException, gives error otherwise
		//RoundedPolygon polllly = new RoundedPolygon();
		//polllly.setVertices(PointArrays.copy(testest));
		//polllly.setRadius(3);
		
		
		for (int i = 0; i < vertixes.length; i++) {
		assertEquals(poly.getVertices()[i].getX(), vertixes[i].getX());
		assertEquals(poly.getVertices()[i].getY(), vertixes[i].getY());
		}
		assertEquals(poly.getRadius(), 2);
		
		poly.insert(0, e);
		for (int i = 0; i < vertixxes.length; i++) {
			assertEquals(poly.getVertices()[i].getX(), vertixxes[i].getX());
			assertEquals(poly.getVertices()[i].getY(), vertixxes[i].getY());
		}
		
		poly.remove(0);
		for (int i = 0; i < vertixes.length; i++) {
			assertEquals(poly.getVertices()[i].getX(), vertixes[i].getX());
			assertEquals(poly.getVertices()[i].getY(), vertixes[i].getY());
		}
		
		poly.remove(3);
		for (int i = 0; i < verties.length; i++) {
			assertEquals(poly.getVertices()[i].getX(), verties[i].getX());
			assertEquals(poly.getVertices()[i].getY(), verties[i].getY());
		}
		poly.insert(3, d);
		
		poly.update(0, e);
		for (int i = 0; i < vertixxxes.length; i++) {
			assertEquals(poly.getVertices()[i].getX(), vertixxxes[i].getX());
			assertEquals(poly.getVertices()[i].getY(), vertixxxes[i].getY());
		}
		
		assert PointArrays.checkDefinesProperPolygon(polly.getVertices()) == null;
		assert polly.contains(e) == false;
		assert polly.contains(new IntPoint(2,5)) == true;
		assert polly.contains(new IntPoint(1,1)) == true;
		assert polly.contains(new IntPoint(1,1)) == true;
		assert polly.contains(new IntPoint(4,4)) == true;
		assert polly.contains(new IntPoint(4,5)) == true;
		assert polly.contains(new IntPoint(2,3)) == true;
		assert polly.contains(new IntPoint(3,3)) == true;
		assert polly.contains(new IntPoint(0,3)) == false;
		assert polly.contains(new IntPoint(9,6)) == true;
		
		
		
		assertEquals("line 2.5 2.0 2.5 2.0\r\n" + 
				"arc 2.0340137826148394 2.6989793260777413 0.8400686002208528 -0.9827937232473294 -2.2694561515163585\r\n" + 
				"line 1.199083264845299 2.79174938360769 1.5 5.5\r\n" + 
				"line 1.5061352016752052 5.555216815076847 1.5 5.5\r\n" + 
				"arc 4.605425607229834 5.210851214459665 3.1183631419145854 3.0309354324158972 -1.9237867146218064\r\n" + 
				"line 6.0 8.0 6.0 8.0\r\n" + 
				"line 7.0 7.5 6.0 8.0\r\n" + 
				"arc 6.25 6.0 1.6770509831248424 1.1071487177940904 -2.214297435588181\r\n" + 
				"line 7.0 4.5 7.0 4.5\r\n" + 
				"line 4.27842511464326 3.13921255732163 7.0 4.5\r\n" + 
				"arc 6.514493092143049 -1.3329233976779493 5.0 2.0344439357957027 0.12435499454676213\r\n" + 
				"line 3.7409921110169 2.8273280740112665 2.5 2.0\r\n" +
				"",
				polly.getDrawingCommands());
		
		// test only work without setVertices @throw IllegalArgumentException
		//assertEquals("", pollly.getDrawingCommands());
		/*
		assertEquals("line 4 6 4.0 4.5\r\n" + 
				"line 4 6 4.0 5.5\r\n" + 
				"line 4 5 4.0 5.5\r\n" + 
				"line 4 5 4.0 4.0\r\n" + 
				"line 4 3 4.0 4.0\r\n" + 
				"line 4 3 4.0 4.5\r\n" + 
				"", polllly.getDrawingCommands());
		*/
		
		
		
		//Extra's used while making and testing RoundedPolygon
		/*
		IntVector ba = a.minus(b);
		IntVector bc = c.minus(b);
		DoublePoint BAc = new DoublePoint(b.getX(),b.getY());
		DoublePoint BAC = BAc.plus(ba.asDoubleVector().scale(.5));
		double poop = 1/2.0;
		DoublePoint BCc = new DoublePoint(b.getX(),b.getY()).plus(bc.asDoubleVector().scale(poop));
		
		DoubleVector Au = ba.asDoubleVector().scale(Math.pow(ba.asDoubleVector().getSize(), -1));
		DoubleVector BAu = new DoubleVector(Au.getX(), Au.getY());
		DoubleVector Cu = bc.asDoubleVector().scale(Math.pow(bc.asDoubleVector().getSize(), -1));
		DoubleVector BCu = new DoubleVector(Cu.getX(), Cu.getY());
		
		DoubleVector BS = BAu.plus(BCu);
		DoubleVector BSu = BAu.plus(BCu).scale(Math.pow(BS.getSize(), -1));
		double unitRadius = Math.abs(BSu.crossProduct(BAu));
		
		
		
		DoublePoint centre = b.asDoublePoint().plus(BSu);
		double BAUcutoff = BAu.dotProduct(BSu);
		double minimumVector = Math.min(ba.asDoubleVector().getSize(), bc.asDoubleVector().getSize())/2.0;
		double scaleFactor = Math.min(2/unitRadius, minimumVector);
		DoublePoint actualCentre = new DoublePoint(centre.getX()*scaleFactor, centre.getY()*scaleFactor);
		double actualRadius = 2*scaleFactor;
		poly.setRadius(2);
		
		DoublePoint BAStart = b.asDoublePoint().plus(BAu.scale(BAUcutoff*scaleFactor));
		DoublePoint BCEnd = b.asDoublePoint().plus(BCu.scale(BAUcutoff*scaleFactor));
		*/
	}

}
