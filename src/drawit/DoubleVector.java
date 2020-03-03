package drawit;

import java.lang.Math; 


public class DoubleVector { 
	
	private double x;
	private double y;
	
	/**
	 * Initializes this object with given x- and y- coordinates.
	 * @post The object's x coordinate equals the given x
	 * 		|getX() == x
	 * @post The object's y coordinate equals the given y
	 * 		|getY() == y
	 */
	public DoubleVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {return this.x;}
	public double getY() {return this.y;}
	
	// Nog niet zeker!
	/**
	 * @post The object's coordinates are multiplied by d.
	 * 		| result.getX() == d * getX() && 
	 * 		| result.getY() == d * getY()
	 */
	public DoubleVector scale(double d) {
		
		double a = d * this.x;
		double b = d * this.y;
		DoubleVector result = new DoubleVector(a,b);
		return result;
		}
	
	/**
	 * @post The second vector's coordinates are added to first vector's coordinates.
	 * 		| result.getX() == this.getX() + other.getX() &&
	 * 		| result.getY() == this.getY() + other.getY()
	 */
	public DoubleVector plus(DoubleVector other) {
		double a = this.x + other.getX();
		double b = this.y + other.getY();
		DoubleVector result = new DoubleVector(a,b);
		return result;
	}
	

	/**
	 * @post The vector's size will be returned
	 * 		| result == Math.sqrt(getX() * getX() + getY() * getY())
	 */
	public double getSize() {
		double result = Math.sqrt(this.x * this.x + this.y * this.y);
		return result;
	}
	
	/**
	 * @post The first vector's x- and y-coordinates are multiplied by the second vector's x-and y-coordinates and those coordinates are then summed up.
	 * 		| result == this.getX() * other.getX() + this.getY() * other.getY()
	 */
	public double dotProduct(DoubleVector other) {
		double result = getX()*other.getX() + getY()*other.getY();
		return result;
	}
		
	/**
	 * @post The first vector's x- and y-coordinates are multiplied by the second vector's y-and x-coordinates and those coordinates are then subtracted.
	 * 		| result == this.getX() * other.getY() - this.getY() * other.getX()
	 */
	public double crossProduct(DoubleVector other) {
		double result = getX()*other.getY() - getY()*other.getX();
		return result;
	}
	
	/**
	 * @post The vector's angle to positive x-axis will be returned in radians.
	 * 		| result == Math.atan2(getY(), getX())
	 */
	public double asAngle() {
		double result = Math.atan2(this.y, this.x);
		return result;
	}	
	
		
	

}


