package drawit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class RoundedPolygonTest {

	@Test
	void test() {
		IntPoint a = new IntPoint(1,1);
		IntPoint b = new IntPoint(2,10);
		IntPoint c = new IntPoint(4,6);
		IntPoint test = new IntPoint(4,5);
		IntPoint d = new IntPoint(4,3);
		IntPoint e = new IntPoint(2,0);
		IntPoint[] vertixes = {a,b,c,d};
		IntPoint[] vertixxes = {e,a,b,c,d};
		IntPoint[] vertixxxes = {e,b,c,d};
		IntPoint[] verties = {a,b,c};
		IntPoint[] vertis = {a,b};
		IntPoint[] testest = {c,test,d};
		
		RoundedPolygon poly = new RoundedPolygon();
		poly.setVertices(vertixes);
		poly.setRadius(2);
		
		RoundedPolygon polly = new RoundedPolygon();
		polly.setVertices(PointArrays.copy(vertixes));
		polly.setRadius(5);
		
		/*Only works without IllegalArgumentException
		RoundedPolygon pollly = new RoundedPolygon();
		pollly.setVertices(PointArrays.copy(vertis));
		pollly.setRadius(3);
		
		RoundedPolygon polllly = new RoundedPolygon();
		polllly.setVertices(PointArrays.copy(testest));
		polllly.setRadius(3);
		*/
		
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
		
		assertEquals("line 2.5 2.0 2.5 2.0\r\n" + 
				"arc 2.0340137826148394 2.6989793260777413 0.8400686002208528 -0.9827937232473294 -2.2694561515163585\r\n" + 
				"line 1.199083264845299 2.79174938360769 1.5 5.5\r\n" + 
				"line 1.7530676008376025 7.777608407538423 1.5 5.5\r\n" + 
				"arc 2.4093715033969842 7.7046857516984915 0.6603427339264235 3.030935432415897 -2.5672878234150898\r\n" + 
				"line 3.0 8.0 3.0 8.0\r\n" + 
				"line 3.4721359549995796 7.055728090000841 3.0 8.0\r\n" + 
				"arc -1.0000000000000009 4.819660112501051 5.0 0.46364760900080604 -0.46364760900080587\r\n" + 
				"line 4.0 4.819660112501052 4.0 4.5\r\n" + 
				"line 4.0 4.5 4.0 4.5\r\n" + 
				"arc 1.1972243622680048 4.5 2.8027756377319952 0.0 -0.9827937232473288\r\n" + 
				"line 2.7519245584932346 2.1679497056621564 2.5 2.0\r\n" + 
				"", 
				polly.getDrawingCommands());
		
		/* tests only work without setVertices @throw IllegalArgumentException
		assertEquals("", pollly.getDrawingCommands());
		
		assertEquals("line 4 6 4.0 4.5\r\n" + 
				"line 4 6 4.0 5.5\r\n" + 
				"line 4 5 4.0 5.5\r\n" + 
				"line 4 5 4.0 4.0\r\n" + 
				"line 4 3 4.0 4.0\r\n" + 
				"line 4 3 4.0 4.5\r\n" + 
				"", polllly.getDrawingCommands());
		*/
		
		
		
		//Extra's
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
		
	}

}
