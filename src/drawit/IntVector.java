package drawit;

/**
 * Creates an IntVector object
 */

public class IntVector { 
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
	public IntVector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Gives back the x-coordinate of the IntVector object
	 * @return the x coordinate
	 * 		| getX() == x 
	 * @representationObject
	 */
	public int getX() {return this.x;}
	/**
	 * Gives back the y-coordinate of the IntVector object
	 * @return the y coordinate
	 * 		| getY() == y
	 * @representationObject
	 */
	public int getY() {return this.y;}
	
	/**
	 * Returns the crossproduct between this and the other IntVector objects
	 * @post The first vector's x- and y-coordinates are multiplied by the second vector's y-and x-coordinates and those coordinates are then subtracted.
	 * 		| result == this.getX() * other.getY() - this.getY() * other.getX()
	 */
	public long crossProduct(IntVector other) {
		long result = (long)getX()*other.getY() - (long)getY()*other.getX();
		return result;
	}
	
	/**
	 * Returns whether this vector is collinear with the given vector
	 * @post returns true if this IntVector is collinear with the given IntVector, returns false otherwise.
	 * 		| result == (this.crossProduct(other) == 0 ? true : false)
	 */
	public boolean isCollinearWith(IntVector other) {
		boolean result;
		if (this.crossProduct(other)==0) {
			result = true;
			return result;}
		else {
			result = false;
			return result;}
		
	}
	
	/**
	 * Returns the dotproduct between this and the other IntVector objects
	 * @post The first vector's x- and y-coordinates are multiplied by the second vector's x-and y-coordinates and those coordinates are then summed up.
	 * 		| result == (long)this.getX() * other.getX() + (long)this.getY() * other.getY()
	 */
	public long dotProduct(IntVector other) {
		long result = (long)getX()*other.getX() + (long)getY()*other.getY();
		return result;
	}
	
	/**
	 * Returns a DoubleVector object with the same variables as the given IntVector object. 
	 * @post Returns a DoubleVector object with the same variables as the given IntVector object. 
	 * 		| result.getX() == this.getX() &&
	 * 		| result.getY() == this.getY()
	 */
	public DoubleVector asDoubleVector() {
		DoubleVector result = new DoubleVector(getX(), getY());
		return result;
		
	}

}
