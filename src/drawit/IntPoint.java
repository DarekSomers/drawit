package drawit;

/**
 * Creates an IntPoint object 
 */

public class IntPoint { 
	/**
	 * @pre x and y cannot be null
	 * 		| x != null && y != null
	 */
	private int x;
	private int y;

	/**
	 * Initializes this object with given x- and y- coordinates.
	 * @invar the coordinates cannot be null
	 * 		| x != null && y != null
	 * @post The object's x coordinate equals the given x
	 * 		|getX() == x
	 * @post The object's y coordinate equals the given y
	 * 		|getY() == y
	 */
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gives back the x-coordinate of the IntPoint object
	 * @return the x coordinate
	 * 		| getX() == x 
	 * @representationObject
	 */
	public int getX() {return this.x;}
	/**
	 * Gives back the y-coordinate of the IntPoint object
	 * @return the y coordinate
	 * 		| getY() == y
	 * @representationObject
	 */
	public int getY() {return this.y;}
	
	/**
	 * Returns whether this point's coordinates are equal to the given point's coordinates
	 * @post returns true if this IntPoint is equal to the given IntPoint.
	 * 		| (getX() == other.x && getY() == other.y) == true || 
	 * 		| (getX() == other.x && getY() == other.y) == false
	 */
	public boolean equals(IntPoint other) {
		return this.x == other.x && this.y == other.y;
	}

	/**
	 * Returns the vector from the given point to this point
	 * @post The given IntPoint's coordinates are substracted from this IntPoint's coordinates.
	 * 		| result.getX() == this.getX() - other.getX() &&
	 * 		| result.getY() == this.getY() - other.getY()
	 */
	public IntVector minus(IntPoint other) {
		int resultX = this.getX() - other.getX();
		int resultY = this.getY() - other.getY();
		IntVector result = new IntVector(resultX, resultY);
		return result;
	}
	
	/**
	 * Returns whether this point lies on the line made by the two given points.
	 * @post returns true if this IntPoint object lies on the line made by the two given IntPoint objects.
	 * 		| result == (c.minus(b).isCollinearWith(this.minus(b)) == true && this.minus(b).dotProduct(c.minus(b)) > 0 && 
	 * 		| this.minus(b).dotProduct(c.minus(b)) < c.minus(b).dotProduct(c.minus(b)) ? true : false)
	 */
	public boolean isOnLineSegment(IntPoint b, IntPoint c) {
		IntVector bc = c.minus(b);
		IntVector ba = this.minus(b);
		boolean result;
		if(bc.isCollinearWith(ba) && ba.dotProduct(bc) > 0 && ba.dotProduct(bc) < bc.dotProduct(bc)) {
			result = true;
			return result;
		}
		else {
			result = false;
			return result;
		}
	}
	
	/**
	 * Returns a DoublePoint object with the same variables as the given IntPoint object. 
	 * @post Returns a DoublePoint object with the same variables as the given IntPoint object. 
	 * 		| result.getX() == this.getX() &&
	 * 		| result.getY() == this.getY()
	 */
	public DoublePoint asDoublePoint() {
		double resultX = this.getX();
		double resultY = this.getY();
		DoublePoint result = new DoublePoint(resultX, resultY);
		return result;
	}
	
	/**
	 * Returns a point whose coordinates are the addition of this point's coordinates and the given point's coordinates
	 * @post The IntPoint is displaced over the given vector.
	 * 		| result.getX() == this.getX() + vector.getX() &&
	 * 		| result.getY() == this.getY() + vector.getY()
	 */
	public IntPoint plus(IntVector vector) {
		int resultX = this.getX() + vector.getX();
		int resultY = this.getY() + vector.getY();
		IntPoint result = new IntPoint(resultX, resultY);
		return result;
	}
	
	/**
	 * Checks whether the two vectors created by the four given points instersect
	 * @post returns true if the given IntVectors ab and cd, made by the four given IntPoints, have intersecting lines, otherwise returns false.
	 * 		| result == true || result == false
	 */
	public static boolean lineSegmentsIntersect(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
		IntVector ab = b.minus(a);
		IntVector cd = d.minus(c);
		IntVector ac = c.minus(a);
		IntVector ad = d.minus(a);
		IntVector bc = c.minus(b);
		
		boolean result;
		
		if (ac.crossProduct(ab) * ad.crossProduct(ab) >= 0 ) {
			result = false;
			return result;}
		if (ac.crossProduct(cd) * bc.crossProduct(cd) >= 0 ) {
			result = false;
			return result;}
		else {
			result = true;
			return result;}
		}
	
	
}
