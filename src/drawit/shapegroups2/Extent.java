package drawit.shapegroups2;

import drawit.IntPoint;


/**
 * Creates an extent object with a left, top, width and height value
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
	 * Contains the value of the width of the extent
	 * @invar width cannot be smaller or equal to 0
	 * 		| width > 0
	 */
	private int width;
	/**
	 * Contains the value of the height of the extent
	 * @invar height cannot be smaller or equal to 0
	 * 		| height > 0
	 */
	private int height;
	
	
	
	
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
	 * Returns width value. This equals the distance between the highest and lowest x-values of the extent
	 * @return width
	 * @representationObject
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Returns height value. This equals the distance between the highest and lowest y-values of the extent
	 * @return height
	 * @representationObject
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Returns the x-coordinate, of the edge parallel to the y-axis, at a distance width of the left value
	 * @return getLeft()+getWidth();
	 */
	public int getRight() {
		return getLeft()+getWidth();
	}
	/**
	 * Returns the y-coordinate, of the edge parallel to the x-axis, at a distance height of the top value
	 * @return getTop() + getHeight();
	 */
	public int getBottom() {
		return getTop() + getHeight();
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
	 * @post The new extent is given left, top, width and height values
	 * 		| result.getLeft() == left && result.getTop() == top && result.getWidth() == width && result.getHeight() == height
	 * @throws IllegalArgumentException when the given values are invalid
	 * 		| !(left < 0 || top < 0 || width <= 0 || height <= 0)
	 */
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		if (left < 0 || top < 0 || width <= 0 || height <= 0)
			throw new IllegalArgumentException("The values cannot be negative");
		Extent ex = new Extent();
		ex.left = left;
		ex.top = top;
		ex.width = width;
		ex.height = height;
		return ex;
	}
	
	/**
	 * Returns a new extent with the given values
	 * @creates ex
	 * @post The new extent is given left and top values and calculates the width and height values with the right and bottom
	 * 		| result.getLeft() == left && result.getTop() == top && result.getWidth() == right - left && result.getHeight() == bottom - top
	 * @throws IllegalArgumentException when the given values are invalid
	 * 		| !(left < 0 || top < 0 || right < 0 || bottom < 0)
	 */
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		if (left < 0 || top < 0 || right < 0 || bottom < 0)
			throw new IllegalArgumentException("The values cannot be negative");
		Extent ex = new Extent();
		ex.left = left;
		ex.top = top;
		ex.width = right - left;
		ex.height = bottom - top;
		return ex;
	}
	
	/**
	 * Returns a new extent with a new left value
	 * @creates ex
	 * @post The new extent is given a new left value changing the width
	 * 		| result.getLeft() == newLeft && result.getTop() == getTop() && result.getWidth() == getRight() - newLeft && result.getHeight() == getHeight()
	 * @throws IllegalArgumentException when the given left value is invalid
	 * 		| !(newLeft < 0)
	 */
	public Extent withLeft(int newLeft) {
		if (newLeft < 0)
			throw new IllegalArgumentException("The new left value cannot be negative");
		Extent ex = new Extent();
		ex.left = newLeft;
		ex.top = getTop();
		ex.width = getRight() - newLeft;
		ex.height = getHeight();
		return ex;
	}
	
	/**
	 * Returns a new extent with a new top value
	 * @creates ex
	 * @post The new extent is given a new top value changing the height
	 * 		| result.getLeft() == getLeft() && result.getTop() == getTop() && result.getWidth() == getWidth() && result.getHeight() == getBottom() - newTop
	 *  @throws IllegalArgumentException when the given top value is invalid
	 * 		| !(newTop < 0)
	 */
	public Extent withTop(int newTop) {
		if (newTop < 0)
			throw new IllegalArgumentException("The new top value cannot be negative");
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = newTop;
		ex.width = getWidth();
		ex.height = getBottom() - newTop;
		return ex;
	}
	
	/**
	 * Returns a new extent with a new right value
	 * @creates ex
	 * @post The new extent is given a new right value changing the width
	 * 		| result.getLeft() == getLeft() && result.getTop() == getTop() && result.getWidth() == newRight - getLeft() && result.getHeight() == getHeight()
	 * @throws IllegalArgumentException when the given right value is invalid
	 * 		| !(newRight < 0)
	 */
	public Extent withRight(int newRight) {
		if (newRight < 0)
			throw new IllegalArgumentException("The new right value cannot be negative");
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.width = newRight - getLeft();
		ex.height = getHeight();
		return ex;
	}
	
	/**
	 * Returns a new extent with a new bottom value
	 * @creates ex
	 * @post The new extent is given a new bottom value changing the height
	 * 		| result.getLeft() == getLeft() && result.getTop() == getTop() && result.getWidth() == getWidth() && result.getHeight() == newBottom - getTop()
	 *  @throws IllegalArgumentException when the given bottom value is invalid
	 * 		| !(newBottom < 0)
	 */
	public Extent withBottom(int newBottom) {
		if (newBottom < 0)
			throw new IllegalArgumentException("The new bottom value cannot be negative");
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.width = getWidth();
		ex.height = newBottom - getTop();
		return ex;
	}
	
	/**
	 * Returns a new extent with a new width value
	 * @creates ex
	 * @post The new extent is given a new width value, the other values remain unchanged
	 * 		| result.getLeft() == getLeft() && result.getTop() == getTop() && result.getWidth() == newWidth && result.getHeight() == getHeight()
	 * @throws IllegalArgumentException when the given width value is invalid
	 * 		| !(newWidth <= 0)
	 */
	public Extent withWidth(int newWidth) {
		if (newWidth <= 0)
			throw new IllegalArgumentException("The new width value cannot be negative");
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.width = newWidth;
		ex.height = getHeight();
		return ex;
	}
	
	/**
	 * Returns a new extent with a new height value
	 * @creates ex
	 * @post The new extent is given a new height value, the other values remain unchanged
	 * 		| result.getLeft() == getLeft() && result.getTop() == getTop() && result.getWidth() == getWidth() && result.getHeight() == newHeight
	 * @throws IllegalArgumentException when the given height value is invalid
	 * 		| !(newHeight <= 0)
	 */
	public Extent withHeigth(int newHeight) {
		if (newHeight <= 0)
			throw new IllegalArgumentException("The new height value cannot be negative");
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.width = getWidth();
		ex.height = newHeight;
		return ex;
	}
	
	
	
	@Override
	public boolean equals(Object exo) {
		if (exo instanceof Extent) {
			Extent other = (Extent) exo;
			if (other.getLeft() == left && other.getTop() == top && other.getWidth() == width && other.getHeight() == height)
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
		result = result*prime - top;
		result = result*prime + width;
		result = result*prime - height;
		return result;
	}
	
	
	@Override
	public String toString() {
		return "Extent [left=" + left + ", top=" + top + ", width=" + width + ", height=" + height + "]";
	}
}
