package drawit;


public class IntPoint { 
	
	private int x;
	private int y;

	/**
	 * Initializes this object with given x- and y- coordinates.
	 * @post The object's x coordinate equals the given x
	 * 		|getX() == x
	 * @post The object's y coordinate equals the given y
	 * 		|getY() == y
	 */
	
	public IntPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {return this.x;}
	public int getY() {return this.y;}
	
	/**
	 * @post returns true if this IntPoint is equal to the given IntPoint.
	 * 		| (getX() == other.x && getY() == other.y) == true || 
	 * 		| (getX() == other.x && getY() == other.y) == false
	 */
	public boolean equals(IntPoint other) {
		return this.x == other.x && this.y == other.y;
	}

	/**
	 * @post The point's coordinates are substracted from the vector's coordinates.
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
	 * @post returns true if this IntPoint object lies on the line made by the two given IntPoint objects.
	 * 		| result == true || result == false
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
	 * @post The point is displaced over the given vector.
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
	 * @post returns true if the given points have intersecting lines, otherwise returns false.
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
