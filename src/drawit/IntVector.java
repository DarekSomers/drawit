package drawit;

public class IntVector { 
	private int x;
	private int y;
	
	/**
	 * Initializes this object with given x- and y- coordinates.
	 * @post The object's x coordinate equals the given x
	 * 		|getX() == x
	 * @post The object's y coordinate equals the given y
	 * 		|getY() == y
	 */
	public IntVector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	
	/**
	 * @post The first vector's x- and y-coordinates are multiplied by the second vector's y-and x-coordinates and those coordinates are then subtracted.
	 * 		| result == this.getX() * other.getY() - this.getY() * other.getX()
	 */
	public long crossProduct(IntVector other) {
		long result = (long)getX()*other.getY() - (long)getY()*other.getX();
		return result;
	}
	
	/**
	 * @post returns true if this IntVector is collinear with the given IntVector, returns false otherwise.
	 * 		| result == true || result == false
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
	 * @post The first vector's x- and y-coordinates are multiplied by the second vector's x-and y-coordinates and those coordinates are then summed up.
	 * 		| result == this.getX() * other.getX() + this.getY() * other.getY()
	 */
	public long dotProduct(IntVector other) {
		long result = (long)getX()*other.getX() + (long)getY()*other.getY();
		return result;
	}
	
	/**
	 * @post Returns a DoubleVector object with the same variables as the given IntVector object. 
	 * 		| result.getX() == this.getX() &&
	 * 		| result.getY() == this.getY()
	 */
	public DoubleVector asDoubleVector() {
		DoubleVector result = new DoubleVector(getX(), getY());
		return result;
		
	}

}
