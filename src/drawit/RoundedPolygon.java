package drawit;

import java.awt.Color;
import java.lang.Math;
import java.lang.reflect.Array;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Creates a RoundedPolygon object and creates the commands for drawing the actual polygon
 */

public class RoundedPolygon { 
	
	/**
	 * Initializes the RoundedPolygon's PointArray
	 * @pre The PointArray cannot be null
	 * 		| vertices != null
	 */
	private IntPoint[] vertices;
	/**
	 * Initializes the RoundedPolygon's radius
	 * @pre Radius cannot be null
	 * 		| rad != null
	 */
	private int rad;
	/**
	 * Initializes the RoundedPolygon's colour
	 * @pre colour cannot be null
	 * 		| colour != null
	 */
	private java.awt.Color colour;
	
	/**
	 * Initializes the RoundedPolygon's boundingBox
	 * @pre boundingBox cannot be null
	 * 		| boundingBox != null
	 */
	private int[] boundingBox = new int[4];
	
	
	/**
	 * Initiates this RoundedPolygon with a given set of vertices, a radius for the corners, a colour and a boudingBox
	 */
	public RoundedPolygon() {
		IntPoint[] Initializer = {new IntPoint(0,0), new IntPoint(1,0), new IntPoint(1,1), new IntPoint(0,1)};
		this.vertices = Initializer;
		this.rad = 0;
		setColor(Color.yellow);
		BoundingBox();
		boundingBox = getBox();
	}

	/**
	 * Returns a copy of the array of vertices of the RoundedPolygon
	 * @throws IllegalArgumentException when the given PointArray is null
	 * 		|!(getVertices() == null)
	 * @return PointArrays.copy(vertices)
	 * @representationObject
	 */
	public IntPoint[] getVertices() {
		return PointArrays.copy(vertices);
	}
	
	/**
	 * Returns the radius of the RoundedPolygon
	 * @return rad
	 * @representationObject
	 */
	public int getRadius() {
		return rad;
	}
	
	/**
	 * Returns the colour of the given polygon
	 * @return colour
	 * @representationObject
	 */
	public java.awt.Color getColor(){
		return colour;
	}
	
	
	/**
	 * Sets the vertices of this RoundedPolygon to the given PointArray
	 * @post Returns a new array with the same contents as the given array.
	 * 		| IntStream.range(0, newVertices.length).allMatch(i -> getVertices()[i].getX() == newVertices[i].getX() && 
	 * 		| getVertices()[i].getY() == newVertices[i].getY())
	 * @throws IllegalArgumentException when the given vertices don't define a proper polygon
	 * 		|!(PointArrays.checkDefinesProperPolygon(newVertices) != null)
	 */
	public void setVertices(IntPoint[] newVertices) {
		vertices = newVertices;
		if (PointArrays.checkDefinesProperPolygon(newVertices) != null) 
			throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(newVertices));
		BoundingBox();
			
	}
	
	/**
	 * Sets the radius of this RoundedPolygon to the given radius
	 * @post rad now equals the given radius
	 * 		| getRadius() == radius
	 * @throws IllegalArgumentException when the given radius is negative
	 * 		| !(radius < 0)
	 * @throws IllegalArgumentException when the given vertices don't define a proper polygon
	 * 		|!(PointArrays.checkDefinesProperPolygon(getVertices()) != null)
	 */
	public void setRadius(int radius) {
		if (radius >= 0) {
			rad = radius;
			if (PointArrays.checkDefinesProperPolygon(getVertices()) != null) 
				throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(getVertices()));
		}
		else
			throw new IllegalArgumentException("The radius cannot be negative.");
	}
	
	/**
	 * Sets the colour of this RoundedPolygon to the given colour
	 * @post colour now equals the given colour
	 * 		| getColor() == color
	 * @throws IllegalArgumentException when the given colour is null
	 * 		| getColor() == null
	 * @param colour
	 */
	public void setColor(java.awt.Color color) {
		if (color == null)
			throw new IllegalArgumentException("Colour cannot be null.");
		else
			this.colour = color;
	}
	
	
	/**
	 * Inserts the given point into this vertex array at the given index
	 * @mutates | this
	 * @mutates | getBox()
	 * @throws IllegalArgumentException if the index goes out of the bounds of the IntPoint[] object
	 * 		| !(index < 0 || index > getVertices().length)
	 * @throws IllegalArgumentException when the given vertices don't define a proper polygon
	 * 		|PointArrays.checkDefinesProperPolygon(PointArrays.insert(getVertices(), index, point)) == null
	 * @post The length of this array is increased by one
	 * 		| getVertices().length == old(getVertices()).length+1
	 * @post the given IntPoint has been inserted into this PointArray at the given index
	 * 		| getVertices()[index] == point
	 */
	public void insert(int index, IntPoint point) {
		if (index < 0 || index > getVertices().length)
			throw new IllegalArgumentException("Index out of bounds.");
		if (PointArrays.checkDefinesProperPolygon(PointArrays.insert(vertices, index, point)) == null) {
			vertices = (PointArrays.insert(getVertices(), index, point)); 
			BoundingBox();
		}
		else
			throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(PointArrays.insert(vertices, index, point))); 
			
	}
	
	/**
	 * removes the point at the given index from this vertex array
	 * @mutates | this
	 * @mutates | getBox()
	 * @throws IllegalArgumentException if the index goes out of the bounds of the IntPoint[] object
	 * 		| !(index < 0 || index >= getVertices().length)
	 * @throws IllegalArgumentException when the given vertices don't define a proper polygon
	 * 		|PointArrays.checkDefinesProperPolygon(PointArrays.remove(getVertices(), index)) == null
	 * @post The length of this array is reduced by one
	 * 		| getVertices().length == old(getVertices()).length-1
	 * @post the IntPoint at the given index has been removed from this PointArray
	 * 		| index > getVertices().length-1 || getVertices()[index] != old(getVertices()[index])
	 */
	public void remove(int index) {
		if (index < 0 || index >= getVertices().length)
			throw new IllegalArgumentException("Index out of bounds.");
		if (PointArrays.checkDefinesProperPolygon(PointArrays.remove(getVertices(), index)) == null) {
			vertices = (PointArrays.remove(getVertices(), index));
			BoundingBox();
		}
		else
			throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(PointArrays.remove(vertices, index)));
			
		
	}
	
	/**
	 * Replaces the point at the given index of this vertex array by another
	 * @mutates | this
	 * @mutates | getBox()
	 * @throws IllegalArgumentException if the index goes out of the bounds of the IntPoint[] object
	 * 		| !(index < 0 || index >= getVertices().length)
	 * @throws IllegalArgumentException when the given vertices don't define a proper polygon
	 * 		|PointArrays.checkDefinesProperPolygon(PointArrays.update(getVertices(), index, point)) == null
	 * @post The length of this array stays the same
	 * 		| getVertices().length == old(getVertices()).length
	 * @post the IntPoint at the given index of this PointArray has been replaced by the given IntPoint
	 * 		| getVertices()[index].getX() == point.getX() && getVertices()[index].getY() == point.getY()
	 */
	public void update(int index, IntPoint point) {
		if (index < 0 || index >= getVertices().length)
			throw new IllegalArgumentException("Index out of bounds.");
		if (PointArrays.checkDefinesProperPolygon(PointArrays.update(getVertices(), index, point)) == null) {
			vertices = (PointArrays.update(getVertices(), index, point));
			BoundingBox();
		}
		else
			throw new IllegalArgumentException(PointArrays.checkDefinesProperPolygon(PointArrays.update(vertices, index, point)));
			
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
		IntPoint exitPath = new IntPoint(1000000, point.getY());
		for (int i = 0; i < vertices.length; i++) {
			if (i != 0 && point.isOnLineSegment(vertices[i-1], vertices[i])) 
				return true;
			else if (i == 0 && point.isOnLineSegment(vertices[vertices.length-1], vertices[i]))
				return true;
			
			if (vertices[i].isOnLineSegment(point, exitPath)) {
				if (i == 0 && vertices[vertices.length-1].minus(point).crossProduct(exitPath.minus(point)) * vertices[i+1].minus(point).crossProduct(exitPath.minus(point)) < 0)
					intersection++;
				if (i == vertices.length-1 && vertices[i-1].minus(point).crossProduct(exitPath.minus(point)) * vertices[0].minus(point).crossProduct(exitPath.minus(point)) < 0)
					intersection++;
				if (i != 0 && i!= vertices.length-1 && vertices[i-1].minus(point).crossProduct(exitPath.minus(point)) * vertices[i+1].minus(point).crossProduct(exitPath.minus(point)) < 0)
					intersection++;
			}
			if (i != 0 && IntPoint.lineSegmentsIntersect(point, exitPath, vertices[i-1], vertices[i]))
				intersection++;
			else if(i == 0 && IntPoint.lineSegmentsIntersect(point, exitPath, vertices[vertices.length-1], vertices[i]))
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
	 * Otherwise returns two "line" and one "arc" strings.
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
				result += "line " + BCEnd.getX() + " " + BCEnd.getY() + " " + BCc.getX() + " " + BCc.getY() + "\r\n";
				}

		}
		result += "fill " + colour.getRed() + " " + colour.getGreen() + " " + colour.getBlue() + "\r\n";
		return result;
		
		
	}
	

	/**
	 * Creates the polygon's boundingBox based on the positions of the polygon's vertices
	 * @mutates | getBox()
	 * @inspects | getVertices()
	 * @post The polygon's boundingBox now equals the smallest possible rectangle containing all of the vertices
	 * 		| IntStream.range(0, getVertices().length).allMatch(i -> BoxContains(getVertices()[i]))
	 */
	public void BoundingBox() {
			int minX = getVertices()[0].getX();
			int maxX = getVertices()[0].getX();
			int minY = getVertices()[0].getY();
			int maxY = getVertices()[0].getY();
			
			for (IntPoint vertix: getVertices()) {
				if (vertix.getX() < minX)
					minX = vertix.getX();
				if (vertix.getX() > maxX)
					maxX = vertix.getX();
				if (vertix.getY() < minY)
					minY = vertix.getY();
				if (vertix.getY() > maxY)
					maxY = vertix.getY();
			}
			boundingBox[0] = minX;
			boundingBox[1] = minY;
			boundingBox[2] = maxX;
			boundingBox[3] = maxY;
	}
	
	/**
	 * Returns the boundingBox of this RoundedPolygon
	 * @return boundingBox
	 * @representationObject
	 */
	public int[] getBox() {
		return boundingBox;
	}


	/**
	 * Tests whether the given IntPoint is contained by this RoundedPolygon
	 * @pre The given IntPoint is not null
	 * 		| point != null
	 * @post Returns true when the point is contained by this polygon, or false ortherwise
	 * 		| result == (point.getX() < getBox()[0] || point.getY() < getBox()[1] ||
			| point.getX() > getBox()[2] || point.getY() > getBox()[3]) || result == true
	 */
	public boolean BoxContains(IntPoint point) {
		if (point.getX() >= boundingBox[0] && point.getY() >= boundingBox[1] &&
				point.getX() <= boundingBox[2] && point.getY() <= boundingBox[3]) {
			return true;
		}
		else 
			return false;
	}

}
