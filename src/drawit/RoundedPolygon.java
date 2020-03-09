package drawit;

import java.lang.Math;

/**
 * Creates a RoundedPolygon object and creates the commands for drawing the actual polygon
 */

public class RoundedPolygon { 
	
	/**
	 * Initializes the RoundedPolygon's PointArray
	 * @pre The PointArray cannot be null
	 * 		| vertices != null
	 * @representationObject
	 */
	private IntPoint[] vertices;
	/**
	 * Initializes the RoundedPolygon's radius
	 * @pre Radius cannot be null
	 * 		| rad != null
	 * @representationObject
	 */
	private int rad;
	
	/**
	 * Initiates this RoundedPolygon
	 */
	public RoundedPolygon() {
	}

	/**
	 * Returns a copy of the array of vertices of the RoundedPolygon
	 * @return PointArrays.copy(vertices)
	 */
	public IntPoint[] getVertices() {
		return PointArrays.copy(vertices);
	}
	
	/**
	 * Returns the radius of the RoundedPolygon
	 * @return rad
	 */
	public int getRadius() {
		return rad;
	}
	
	/**
	 * Sets the vertices of this RoundedPolygon to the given PointArray
	 * @post the vertices now equal the given PointArray
	 * 		| this.getVertices()[0].getX() == PointArrays.copy(newVertices)[0].getX()
	 * @throws IllegalArgumentException when the given vertices don't define a proper polygon
	 * 		|!(PointArrays.checkDefinesProperPolygon(newVertices) != null)
	 */
	public void setVertices(IntPoint[] newVertices) {
		if (PointArrays.checkDefinesProperPolygon(newVertices) != null)
			throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(newVertices));
		vertices = PointArrays.copy(newVertices);
		
	}
	
	/**
	 * Sets the radius of this RoundedPolygon to the given radius
	 * @post rad now equals the given radius
	 * 		| getRadius() == radius
	 * @throws IllegalArgumentException when the given radius is negative
	 * 		| !(radius < 0)
	 */
	public void setRadius(int radius) {
		if (radius < 0)
			throw new IllegalArgumentException("The radius cannot be negative.");
		rad = radius;
	}
	
	/**
	 * Inserts the given point into this vertex array at the given index
	 * @mutates | this
	 * @invar | index != null
	 * @throws IllegalArgumentException if the index goes out of the bounds of the IntPoint[] object
	 * 		| !(index < 0 || index > getVertices().length)
	 * @throws IllegalArgumentException when the given vertices don't define a proper polygon
	 * 		|!(PointArrays.checkDefinesProperPolygon(PointArrays.insert(vertices, index, point)) != null)
	 * @post The length of this array is increased by one
	 * 		| getVertices().length == old(getVertices()).length+1
	 * @post the given IntPoint has been inserted into this PointArray at the given index
	 * 		| getVertices()[index] == point
	 */
	public void insert(int index, IntPoint point) {
		
		if (index < 0 || index > getVertices().length)
			throw new IllegalArgumentException("Index out of bounds.");
		/**
		 * Note: Throwing an illegal argument exception here would be illogical as indices can only be placed on the edges of already existing polygons.
		 *		 Doing so will always result in the error of a point laying on the egde
		 * if (PointArrays.checkDefinesProperPolygon(PointArrays.insert(vertices, index, point)) != null) 
		 * 		throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(PointArrays.insert(vertices, index, point))); 
		 */
		vertices = PointArrays.insert(vertices, index, point);
	}
	
	/**
	 * removes the point at the given index from this vertex array
	 * @mutates | this
	 * @invar | index != null
	 * @throws IllegalArgumentException if the index goes out of the bounds of the IntPoint[] object
	 * 		| !(index < 0 || index >= getVertices().length)
	 * @throws IllegalArgumentException when the given vertices don't define a proper polygon
	 * 		|!(PointArrays.checkDefinesProperPolygon(PointArrays.remove(vertices, index)) != null)
	 * @post The length of this array is reduced by one
	 * 		| getVertices().length == old(getVertices()).length-1
	 * @post the IntPoint at the given index has been removed from this PointArray
	 * 		| index > getVertices().length-1 || getVertices()[index] != old(getVertices()[index])
	 */
	public void remove(int index) {
		if (index < 0 || index >= getVertices().length)
			throw new IllegalArgumentException("Index out of bounds.");
		if (PointArrays.checkDefinesProperPolygon(PointArrays.remove(vertices, index)) != null)
			throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(PointArrays.remove(vertices, index)));
		vertices = PointArrays.remove(vertices, index);
	}
	
	/**
	 * Replaces the point at the given index of this vertex array by another
	 * @mutates | this
	 * @invar | index != null
	 * @throws IllegalArgumentException if the index goes out of the bounds of the IntPoint[] object
	 * 		| !(index < 0 || index >= getVertices().length)
	 * @throws IllegalArgumentException when the given vertices don't define a proper polygon
	 * 		|!(PointArrays.checkDefinesProperPolygon(PointArrays.update(vertices, index, point)) != null)
	 * @post The length of this array stays the same
	 * 		| getVertices().length == old(getVertices()).length
	 * @post the IntPoint at the given index of this PointArray has been replaced by the given IntPoint
	 * 		| getVertices()[index] == point
	 */
	public void update(int index, IntPoint point) {
		if (index < 0 || index >= getVertices().length)
			throw new IllegalArgumentException("Index out of bounds.");
		if (PointArrays.checkDefinesProperPolygon(PointArrays.update(vertices, index, point)) != null)
			throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(PointArrays.update(vertices, index, point)));
		vertices = PointArrays.update(vertices, index, point);
	}
	
	/**
	 * Returns whether the given point is contained by this figure
	 * @post returns true when the IntPoint is contained by this RoundedPolygon otherwise returns false 
	 * The IntPoint is contained when it lies either on another vertex, an edge 
	 * or when the amount of intersections between the vector, made by the given point and another at a far distance, and the edges is odd.
	 */
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
	
	/**
	 * Returns a string containing the drawing commands to draw this figure
	 * @post Returns a string ("line" and possibly "arc") containing the drawing commands to draw this RoundedPolygon.
	 * Returns an empty string when there are less than 3 vertices.
	 * Returns two "line" strings when the three vertexes are colinear.
	 * Otherwise returns two "line" and one "arc" string.
	 * @creates | result
	 */
	public String getDrawingCommands() {
		String result = "";
		
		if (vertices.length < 3) {
			result = "";
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
				result += "line " + B.getX() + " " + B.getY() + " " + BAc.getX() + " " + BAc.getY() + "\r\n";
				result += "line " + B.getX() + " " + B.getY() + " " + BCc.getX() + " " + BCc.getY() + "\r\n";
				
			}
			
			else {
				DoubleVector BAu = BA.asDoubleVector().scale(Math.pow(BA.asDoubleVector().getSize(), -1));
				DoubleVector BCu = BC.asDoubleVector().scale(Math.pow(BC.asDoubleVector().getSize(), -1));
				
				DoubleVector BS = BAu.plus(BCu);
				DoubleVector BSu = BS.scale(Math.pow(BS.getSize(), -1));
				
				//DoublePoint centre = B.asDoublePoint().plus(BSu);
				double BAUcutoff = BAu.dotProduct(BSu);
				double unitRadius = Math.abs(BSu.crossProduct(BAu));
				double minimumVector = Math.min(BA.asDoubleVector().getSize(), BC.asDoubleVector().getSize())/2.0;
				double scaleFactor = Math.min(getRadius()/unitRadius, minimumVector/BAUcutoff);

				DoublePoint actualCentre = B.asDoublePoint().plus(BSu.scale(scaleFactor));
				double actualRadius = unitRadius*scaleFactor;
				
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
