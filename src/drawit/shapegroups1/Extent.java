package drawit.shapegroups1;

import drawit.IntPoint;

public class Extent {
	
	private int left;
	private int top;
	private int right;
	private int bottom;
	private int width;
	private int height;
	
	public int getLeft() {
		return left;
	}
	
	public int getTop() {
		return top;
	}

	public int getRight() {
		return right;
	}

	public int getBottom() {
		return bottom;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public IntPoint getTopLeft() {
		IntPoint topLeft = new IntPoint(getLeft(), getTop());
		return topLeft;
	}
	
	public IntPoint getBottomRight() {
		IntPoint bottomRight = new IntPoint(getRight(), getBottom());
		return bottomRight;
	} 

	
	public boolean contains(IntPoint point) {
		if (point.getX() >= getLeft() && point.getX() <= getRight() && point.getY() >= getBottom() && point.getY() <= getTop())
			return true;
		else
			return false;
		
	}

	
	public static Extent ofLeftTopWidthHeight(int left, int top, int width, int height) {
		Extent ex = new Extent();
		ex.left = left;
		ex.right = left + width;
		ex.top = top;
		ex.bottom = top - height;
		ex.width = width;
		ex.height = height;
		return ex;
	}
	
	
	public static Extent ofLeftTopRightBottom(int left, int top, int right, int bottom) {
		Extent ex = new Extent();
		ex.left = left;
		ex.top = top;
		ex.right = right;
		ex.bottom = bottom;
		ex.width = right - left;
		ex.height = top - bottom;
		return ex;
	}
	
	
	public Extent withLeft(int newLeft) {
		Extent ex = new Extent();
		ex.left = newLeft;
		ex.top = getTop();
		ex.right = getRight();
		ex.bottom = getBottom();
		ex.width = getRight() - newLeft;
		ex.height = getTop() - getBottom();
		return ex;
	}
	
	
	public Extent withTop(int newTop) {
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = newTop;
		ex.right = getRight();
		ex.bottom = getBottom();
		ex.width = getRight() - getLeft();
		ex.height = newTop - getBottom();
		return ex;
	}
	
	
	public Extent withRight(int newRight) {
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.right = newRight;
		ex.bottom = getBottom();
		ex.width = newRight - getLeft();
		ex.height = getTop() - getBottom();
		return ex;
	}
	
	
	public Extent withBottom(int newBottom) {
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.right = getRight();
		ex.bottom = newBottom;
		ex.width = getRight() - getLeft();
		ex.height = getTop() - newBottom;
		return ex;
	}
	
	
	public Extent withWidth(int newWidth) {
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.right = getLeft() + newWidth;
		ex.bottom = getBottom();
		ex.width = newWidth;
		ex.height = getTop() - getBottom();
		return ex;
	}
	
	
	public Extent withHeigth(int newHeight) {
		Extent ex = new Extent();
		ex.left = getLeft();
		ex.top = getTop();
		ex.right = getRight();
		ex.bottom = getTop() - newHeight;
		ex.width = getRight() - getLeft();
		ex.height = newHeight;
		return ex;
	}
	
}
