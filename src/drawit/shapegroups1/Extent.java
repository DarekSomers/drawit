package drawit.shapegroups1;

import drawit.IntPoint;

/**
 * Creates an extent object with a left, top, right and bottom value
 */

public class Extent {
	
	/**
	 * Contains the value of the smallest x-coordinate of the extent
	 * @invar Left has to be positive
	 * 		| left >= 0
	 */
	private int left;
	/**
	 * Contains the value of the smallest y-coordinate of the extent
	 * @invar top has to be positive
	 * 		| top >= 0
	 */
	private int top;
	/**
	 * Contains the value of the largest x-coordinate of the extent
	 * @invar right has to be positive
	 * 		| right >= 0
	 */
	private int right;
	/**
	 * Contains the value of the largest y-coordinate of the extent
	 * @invar bottom has to be positive
	 * 		| bottom >= 0
	 */
	private int bottom;
	
	
	
	/**
	 * Returns the x-coordinate, of the edge parallel to the y-axis, with the smallest value
	 * @return left
	 * @representationObject
	 */
	public int getLeft() {
		return left;
	}
	
	/**
	 * Returns the y-coordinate, of the edge parallel to the x-axis, with the smallest value
	 * @return top
	 * @representationObject
	 */
	public int getTop() {
		return top;
	}
	/**
	 * Returns the x-coordinate, of the edge parallel to the y-axis, with the highest value
	 * @return right
	 * @representationObject
	 */
	public int getRight() {
		return right;
	}
	/**
	 * Returns the y-coordinate, of the edge parallel to the x-axis, with the highest value
	 * @return bottom
	 * @representationObject
	 */
	public int getBottom() {
		return bottom;
	}
	/**
	 * Returns the distance between the left and right values
	 * @return getRight()-getLeft()
	 */
	public int getWidth() {
		return getRight()-getLeft();
	}
	/**
	 * Returns the distance between the top and bottom values
	 * @return getBottom() - getTop();
	 */
	public int getHeight() {
		return getBottom() - getTop();
	}
	
	
	
	/**
	 * Returns the point with the x- and y-values equaling the left- respectively top-values
	 * @return topLeft	 
	 */
	public IntPoint getTopLeft() {
		IntPoint topLeft = new IntPoint(getLeft(), getTop());
		return topLeft;
	}
	/**
	 * Returns the point with the x- and y-values equaling the right- respectively bottom-values
	 * @return bottomRight	 
	 */
	public IntPoint getBottomRight() {
		IntPoint bottomRight = new IntPoint(getRight(), getBottom());
		return bottomRight;
	} 

	
	/**
	 * Returns whether the given point is contained by the closed set of points which form the extent
	 * @post Returns true when the values of the given point fall in between the margins, returns false otherwise
	 * 		| (point.getX() >= getLeft() && point.getX() <= getRight() && point.getY() <= getBottom() && point.getY() >= getTop()) || result == false
	 */
	public boolean contains(IntPoint point) {
		if (point.getX() >= getLeft() && point.getX() <= getRight() && point.getY() <= getBottom() && point.getY() >= getTop())
			return true;
		else
			return false;
		
	}

	/**
	 * Returns a new extent with the given values
	 * @creates ex
	 * @post The new extent is given left and top values and calculates the right and bottom values with the given width and height
	 * 		| result.getLeft() == left && result.getTop() == top && result.getRight() == left + width && result.getBottom() == top + height
	 * @throws IllegalArgumentException when the given values are invalid
	 * 		| !(left < 0 || top < 0 || width <= 0 || height <= 0)
	 */
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		if (left < 0 || top < 0 || width <= 0 || height <= 0)
			throw new IllegalArgumentException("The values cannot be negative");
		Extent ex = new Extent();
		ex.left = left;
		ex.right = left + width;
		ex.top = top;
		ex.bottom = top + height;
		return ex;
	}
	
	/**
	 * Returns a new extent with the given values
	 * @creates ex
	 * @post The new extent is given left, top, right and bottom values
	 * 		| result.getLeft() == left && result.getTop() == top && result.getRight() == right && result.getBottom() == bottom
	 * @throws IllegalArgumentException when the given values are invalid
	 * 		| !(left < 0 || top < 0 || right < 0 || bottom < 0)
	 */
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		if (left < 0 || top < 0 || right < 0 || bottom < 0)
			throw new IllegalArgumentException("The values cannot be negative");
		Extent ex = new Extent();
		ex.left = left;
		ex.top = top;
		ex.right = right;
		ex.bottom = bottom;
		return ex;
	}
	
	/**
	 * Returns a new extent with a new left value
	 * @creates ex
	 * @post The new extent is given a new left value, the other values remain unchanged
	 * 		| result.getLeft() == newLeft && result.getTop() == getTop() && result.getRight() == getRight() && result.getBottom() == getBottom()
	 * @throws IllegalArgumentException when the given left value is invalid
	 * 		| !(newLeft < 0)
	 */
	public Extent withLeft(int newLeft) {
		if (newLeft < 0)
			throw new IllegalArgumentException("The new left value cannot be negative");
		Extent ex = new Extent();
		ex.left = newLeft;
		ex.top = getTop();
		ex.right = getRight();
		ex.bottom = getBottom();
		return ex;
	}
	
	/**
	 * Returns a new extent with a new top value
	 * @creates ex
	 * @post The new extent is given a new top value, the other values remain unchanged
	 * 		| result.getLeft() == getLeft() && result.getTop() == newTop && result.getRight() == getRight() && result.getBottom() == getBottom()
	 * @throws IllegalArgumentException when the given top value is invalid
	 * 		| !(newTop < 0)
	 */
	public Extent withTop(int newTop) {
		if (newTop < 0)
			throw new IllegalArgumentException("The new top value cannot be negative");
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = newTop;
		ex.right = getRight();
		ex.bottom = getBottom();
		return ex;
	}
	
	/**
	 * Returns a new extent with a new right value
	 * @creates ex
	 * @post The new extent is given a new right value, the other values remain unchanged
	 * 		| result.getLeft() == getLeft() && result.getTop() == getTop() && result.getRight() == newRight && result.getBottom() == getBottom()
	 * @throws IllegalArgumentException when the given right value is invalid
	 * 		| !(newRight < 0)
	 */
	public Extent withRight(int newRight) {
		if (newRight < 0)
			throw new IllegalArgumentException("The new right value cannot be negative");
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.right = newRight;
		ex.bottom = getBottom();
		return ex;
	}
	
	/**
	 * Returns a new extent with a new bottom value
	 * @creates ex
	 * @post The new extent is given a new bottom value, the other values remain unchanged
	 * 		| result.getLeft() == getLeft() && result.getTop() == getTop() && result.getRight() == getRight() && result.getBottom() == newBottom
	 * @throws IllegalArgumentException when the given bottom value is invalid
	 * 		| !(newBottom < 0)
	 */
	public Extent withBottom(int newBottom) {
		if (newBottom < 0)
			throw new IllegalArgumentException("The new bottom value cannot be negative");
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.right = getRight();
		ex.bottom = newBottom;
		return ex;
	}
	
	/**
	 * Returns a new extent with a new width value
	 * @creates ex
	 * @post The new extent is given a new width value changing the right value
	 * 		| result.getLeft() == getLeft() && result.getTop() == getTop() && result.getRight() == getLeft() + newWidth && result.getBottom() == getBottom()
	 * @throws IllegalArgumentException when the given width value is invalid
	 * 		| !(newWidth <= 0)
	 */
	public Extent withWidth(int newWidth) {
		if (newWidth <= 0)
			throw new IllegalArgumentException("The new width value cannot be negative");
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.right = getLeft() + newWidth;
		ex.bottom = getBottom();
		return ex;
	}
	
	/**
	 * Returns a new extent with a new height value
	 * @creates ex
	 * @post The new extent is given a new height value changing the bottom value
	 * 		| result.getLeft() == getLeft() && result.getTop() == getTop() && result.getRight() == getRight() && result.getBottom() == getTop() + newHeight
	 * @throws IllegalArgumentException when the given height value is invalid
	 * 		| !(newHeight <= 0)
	 */
	public Extent withHeigth(int newHeight) {
		if (newHeight <= 0)
			throw new IllegalArgumentException("The new height value cannot be negative");
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.right = getRight();
		ex.bottom = getTop() + newHeight;
		return ex;
	}
	
	
	
	@Override
	public boolean equals(Object exo) {
		if (exo instanceof Extent) {
			Extent other = (Extent) exo;
			if (other.getLeft() == left && other.getTop() == top && other.getRight() == right && other.getBottom() == bottom)
				return true;
			else 
				return false;
		}
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result = 1;
		result = result*prime + left;
		result = result*prime + top;
		result = result*prime - right;
		result = result*prime - bottom;
		return result;
	}
	
	
	@Override
	public String toString() {
		return "Extent [left=" + left + ", top=" + top + ", right=" + right + ", bottom=" + bottom + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
