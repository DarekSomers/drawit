package drawit;

import java.lang.Math;

public class RoundedPolygon { 
	
	private IntPoint[] vertices;
	private int rad;
	
	
	public RoundedPolygon() {
	}

	public IntPoint[] getVertices() {
		return PointArrays.copy(vertices);
	}
	
	public int getRadius() {
		return rad;
	}
	
	public void setVertices(IntPoint[] newVertices) {
		
		vertices = PointArrays.copy(newVertices);
	}
	
	public void setRadius(int radius) {
		rad = radius;
	}
	
	public void insert(int index, IntPoint point) {
		vertices = PointArrays.insert(vertices, index, point);
	}
	
	public void remove(int index) {
		vertices = PointArrays.remove(vertices, index);
	}
	
	public void update(int index, IntPoint point) {
		vertices = PointArrays.update(vertices, index, point);
	}
	
	public boolean contains(IntPoint point) {
		int intersection = 0;
		
		for (IntPoint vertex: vertices) {
			if (point.equals(vertex)) {
				return true;
			}
		}
		
		for (int i = 0; i < vertices.length; i++) {
			if (i != 0 && point.isOnLineSegment(vertices[i-1], vertices[i])) 
				return true;
			else if (i == 0 && point.isOnLineSegment(vertices[vertices.length-1], vertices[i]))
				return true;
			
			if (vertices[i].isOnLineSegment(point, new IntPoint(1000000, point.getY())))
				intersection++;
			if (i != 0 && IntPoint.lineSegmentsIntersect(point, new IntPoint(1000000, point.getY()), vertices[i-1], vertices[i]))
				intersection++;
			else if(i == 0 && IntPoint.lineSegmentsIntersect(point, new IntPoint(1000000, point.getY()), vertices[vertices.length-1], vertices[i]))
				intersection++;
		}
		
		if (intersection%2 == 1)
			return true;
		
		else
			return false;
	}
	public String getDrawingCommands() {
		String result = "";
		
		if (vertices.length < 3) {
			result = null;
			return result;
		}
		
		for (int i = 0; i < vertices.length; i++) {
			IntPoint B = getVertices()[i];
			IntPoint A;
			IntPoint C;
			if (i == 0) {
				A = getVertices()[vertices.length-1];
				C = getVertices()[i+1];
			}
			else if (i == vertices.length-1) {
				A = getVertices()[i-1];
				C = getVertices()[0];
			}
				
			else {
				A = getVertices()[i-1];
				C = getVertices()[i+1];
			}
			IntVector BA = A.minus(B);
			IntVector BC = C.minus(B);
			DoublePoint BAc = new DoublePoint(B.getX(),B.getY()).plus(BA.asDoubleVector().scale(.5));
			DoublePoint BCc = new DoublePoint(B.getX(),B.getY()).plus(BC.asDoubleVector().scale(.5));
			
			if (BA.isCollinearWith(BC)) {
				result += "line " + BA.getX() + " " + BA.getY() + " " + BC.getX() + " " + BC.getY() + "\r\n";
				result += "arc " + 0 + " " + 0 + " " + 0 + " " + 0 + " " + 0 + "\r\n";
			}
			
			else {
				DoubleVector BAu = BA.asDoubleVector().scale(Math.pow(BA.asDoubleVector().getSize(), -1));
				DoubleVector BCu = BC.asDoubleVector().scale(Math.pow(BC.asDoubleVector().getSize(), -1));
				
				DoubleVector BS = BAu.plus(BCu);
				DoubleVector BSu = BS.scale(Math.pow(BS.getSize(), -1));
				
				DoublePoint centre = B.asDoublePoint().plus(BSu);
				double BAUcutoff = BAu.dotProduct(BSu);
				double unitRadius = Math.abs(BSu.crossProduct(BAu));
				double minimumVector = Math.min(BA.asDoubleVector().getSize(), BC.asDoubleVector().getSize())/2.0;
				double scaleFactor = Math.min(getRadius()/unitRadius, minimumVector/BAUcutoff);
				
				DoublePoint actualCentre = B.asDoublePoint().plus(BSu.scale(scaleFactor));
				double actualRadius = getRadius()/scaleFactor;
				
				DoublePoint BAStart = B.asDoublePoint().plus(BAu.scale(BAUcutoff*scaleFactor));
				DoublePoint BCEnd = B.asDoublePoint().plus(BCu.scale(BAUcutoff*scaleFactor));
				
				double startAngle = BAStart.minus(actualCentre).asAngle();
				double endAngle = BCEnd.minus(actualCentre).asAngle();
				double extentAngle = (endAngle - startAngle);
				while (extentAngle < -Math.PI)
					extentAngle += 2*Math.PI;
				while (extentAngle > Math.PI)
					extentAngle -= 2*Math.PI;
				
				result += "line " + BAStart.getX() + " " + BAStart.getY() + " " + BAc.getX() + " " + BAc.getY() + "\r\n";
				result += "arc " + actualCentre.getX() + " " + actualCentre.getY() + " " + actualRadius + " " + startAngle + " " + extentAngle + "\r\n";
				result += "line " + BCEnd.getX() + " " + BCEnd.getY() + " " + BCc.getX() + " " + BCc.getY() + "\r\n";			}

		}
		return result;
		
	}
	
}
