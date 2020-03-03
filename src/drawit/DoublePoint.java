package drawit;


public class DoublePoint { 
	private double x;
	private double y;
	
	
	/**
	 * Initializes this object with given x- and y- coordinates.
	 * @post The object's x coordinate equals the given x
	 * 		|getX() == x
	 * @post The object's y coordinate equals the given y
	 * 		|getY() == y
	 */
	public DoublePoint(double x, double y) { 
		this.x = x;
		this.y = y;
	}
	
	public double getX() {return this.x;}
	public double getY() {return this.y;}
		
	/**
	 * @post The vector's coordinates are added to the point's coordinates (so the point will be displaced in space).
	 * 		| result.getX() == this.getX() + other.getX() &&
	 * 		| result.getY() == this.getY() + other.getY()
	 */
	public DoublePoint plus(DoubleVector other) {  
		double resultX = this.getX() + other.getX();
		double resultY = this.getY() + other.getY();
		DoublePoint result = new DoublePoint(resultX, resultY);
		return result;
	}
	
	/**
	 * @post The point's coordinates are substracted from the vector's coordinates.
	 * 		| result.getX() == this.getX() - other.getX() &&
	 * 		| result.getY() == this.getY() - other.getY() 
	 */
	public DoubleVector minus(DoublePoint other) {
		double resultX = this.getX() - other.getX();
		double resultY = this.getY() - other.getY();
		DoubleVector result = new DoubleVector(resultX, resultY);
		return result;
		
	}
	
	 //**
	  //* @post The IntPoint obejct's coordinates are rounded to the nearest integers.
	  //* 		| result.getX() == ix &&
	  //* 		| result.getY() == iy &&
	  //* 		| result == DoublePoint(ix,iy)
	  //*/
	public IntPoint round() {
		int ix = 0;
		int iy = 0;
		while(ix < this.getX()-1) { 
			ix++;}
		double decimalX = this.getX() - ix ;
		if (decimalX >= 0.5) {
			ix++;}
		while(iy < this.getY()-1) {
			iy++;}
		double decimalY = this.getY() - iy ;
		if (decimalY >= 0.5) {
			iy++;}
		IntPoint result = new IntPoint(ix,iy);
		return result;
			
	}
}
