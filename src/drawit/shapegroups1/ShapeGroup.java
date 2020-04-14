package drawit.shapegroups1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import drawit.DoublePoint;
import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;

public class ShapeGroup {
	private RoundedPolygon leaf;
	private ShapeGroup[] nonLeaf;
	private Extent ex;
	private Extent newEx;
	private Extent prevEx;
	private ShapeGroup parent;
	private ShapeGroup[] children;
	
	private double changeXl = 0;
	private double changeXr = 0;
	private double changeYt = 0;
	private double changeYb = 0;


	
	
	public ShapeGroup(RoundedPolygon shape) {
		leaf = shape;
		int minX = leaf.getVertices()[0].getX();
		int maxX = leaf.getVertices()[0].getX();
		int minY = leaf.getVertices()[0].getY();
		int maxY = leaf.getVertices()[0].getY();
		
		for (IntPoint vertix: leaf.getVertices()) {
			if (vertix.getX() < minX)
				minX = vertix.getX();
			if (vertix.getX() > maxX)
				maxX = vertix.getX();
			if (vertix.getY() < minY)
				minY = vertix.getY();
			if (vertix.getY() > maxY)
				maxY = vertix.getY();
		}
		setExtent(Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY));
	}
	
	
	public ShapeGroup(ShapeGroup[] subgroups) {
		nonLeaf = subgroups;
		ShapeGroup[] subgroup = nonLeaf;
		while (subgroup[0].leaf == null) {
			subgroup = subgroup[0].nonLeaf;
		}
		int minX = subgroup[0].getExtent().getLeft();
		int maxX = subgroup[0].getExtent().getRight();
		int minY = subgroup[0].getExtent().getBottom();
		int maxY = subgroup[0].getExtent().getTop();
		children = new ShapeGroup[getSubgroupCount()];
		for (int i = 0; i < getSubgroupCount(); i++) {
			children[i] = getSubgroup(i);
			children[i].parent = this;
			if (children[i].getExtent().getLeft() < minX)
				minX = children[i].getExtent().getLeft();
			if (children[i].getExtent().getRight() > maxX)
				maxX = children[i].getExtent().getRight();
			if (children[i].getExtent().getTop() < minY)
				minY = children[i].getExtent().getTop();
			if (children[i].getExtent().getBottom() > maxY)
				maxY = children[i].getExtent().getBottom();
		}
		setExtent(Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY));
	}
	
	
	public Extent getExtent() {
		return newEx;
	}
	
	
	public Extent getOriginalExtent() {
		return ex;
	}
	
	
	public ShapeGroup getParentGroup() {
		if (parent == null)
			return null;
		else
			return parent;
	}
	
	
	public RoundedPolygon getShape() {
		if (nonLeaf != null)
			return null;
		else
			return leaf;
	}
	
	
	
	public java.util.List<ShapeGroup> getSubgroups(){
		if (leaf != null)
			return null;
		else {
			List<ShapeGroup> list = new ArrayList<ShapeGroup>();
			for (ShapeGroup shape: nonLeaf) 
				list.add(shape);
			return list;
		}
			
			 
	}
	
	
	
	public int getSubgroupCount() {
		return nonLeaf.length;
	}
	
	
	
	public ShapeGroup getSubgroup(int index) {
		if (index >= nonLeaf.length)
			throw new IllegalArgumentException("Index out of bounds");
		else
			return nonLeaf[index];
	}
	
	
	
	
	public IntPoint toInnerCoordinates(IntPoint globalCoordinates) {
		if (children != null) {
			for (ShapeGroup child: children) {
				child.toInnerCoordinates(globalCoordinates);
			}
		}
		double newX;
		double newY;
		ShapeGroup Pgroup = this;
		double scaleX = (double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth();
		double scaleY = (double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight();
		double translateX = 0;
		double translateY = 0;
		while (Pgroup.parent != null) {
			Pgroup = Pgroup.parent;
			//scaleX *= (double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth();
			translateX += (double)(globalCoordinates.getX() + translateX - Pgroup.getExtent().getLeft()) / 
					((double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth()) + Pgroup.getOriginalExtent().getLeft() - globalCoordinates.getX() - translateX;
			//scaleY *= (double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight();
			translateY += (double)(globalCoordinates.getY() + translateY - Pgroup.getExtent().getTop()) / 
					((double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight()) + Pgroup.getOriginalExtent().getTop() - globalCoordinates.getY() - translateY;
		}
	
		if (Pgroup != this) {
			newX = (globalCoordinates.getX() - getExtent().getLeft() + translateX) / 
					scaleX + getOriginalExtent().getLeft();
			newY = ((globalCoordinates.getY() - getExtent().getTop()) + translateY) / 
					scaleY + getOriginalExtent().getTop();
		}

		else {
			newX = (double)(globalCoordinates.getX() - getExtent().getLeft()) / ((double)newEx.getWidth() / ex.getWidth()) + 
					getOriginalExtent().getLeft();
			newY = (double)(globalCoordinates.getY() - getExtent().getTop()) / ((double)newEx.getHeight() / ex.getHeight()) + 
					getOriginalExtent().getTop();
		}
		
		IntPoint innerCoordinates = new DoublePoint(newX, newY).round();
		return innerCoordinates;
	}
	
	
	
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		if (children != null) {
			for (ShapeGroup child: children) {
				child.toGlobalCoordinates(innerCoordinates);
			}
		}
		double newX;
		double newY;
		ShapeGroup Pgroup = this;
		double scaleX = (double)newEx.getWidth() / ex.getWidth();
		double scaleY = (double)newEx.getHeight() / ex.getHeight();
		double translateX = 0;
		double translateY = 0;
		while (Pgroup.parent != null) {
			Pgroup = Pgroup.parent;
			scaleX *= (double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth();
			translateX += (double)(getExtent().getLeft() + translateX - Pgroup.getOriginalExtent().getLeft()) * 
					((double)Pgroup.newEx.getWidth() / Pgroup.ex.getWidth()) + Pgroup.getExtent().getLeft() - getExtent().getLeft() - translateX;
			scaleY *= (double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight();
			translateY += (double)(getExtent().getTop() + translateY - Pgroup.getOriginalExtent().getTop()) * 
					((double)Pgroup.newEx.getHeight() / Pgroup.ex.getHeight()) + Pgroup.getExtent().getTop() - getExtent().getTop() - translateY;
		}
		
		if (Pgroup != this) {
			newX = (double)(innerCoordinates.getX() - getOriginalExtent().getLeft()) * 
					scaleX + translateX + getExtent().getLeft();
			newY = (double)(innerCoordinates.getY() - getOriginalExtent().getTop()) * 
					scaleY + translateY + getExtent().getTop();
		}
		
		else {
			newX = (double)(innerCoordinates.getX() - getOriginalExtent().getLeft()) * 
					((double)newEx.getWidth() / ex.getWidth()) + getExtent().getLeft();
			newY = (double)(innerCoordinates.getY() - getOriginalExtent().getTop()) * 
					((double)newEx.getHeight() / ex.getHeight()) + getExtent().getTop();
		}
	
		IntPoint globalCoordinates = new DoublePoint(newX, newY).round();
		return globalCoordinates;
	}
	
	
	
	public IntVector toInnerCoordinates(IntVector globalCoordinates) {
		if (children != null) {
			for (ShapeGroup child: children) {
				child.toInnerCoordinates(globalCoordinates);
			}
		}
		double widthChange = 1;
		double heightChange = 1;
		ShapeGroup Pgroup = this;
		 
		while (Pgroup.parent != null) {
			widthChange *= (double)Pgroup.newEx.getWidth()/Pgroup.ex.getWidth();
			heightChange *= (double)Pgroup.newEx.getHeight()/Pgroup.ex.getHeight();
			Pgroup = Pgroup.parent;
		}
		
		if (Pgroup != this) {
			widthChange *= (double)Pgroup.newEx.getWidth()/Pgroup.ex.getWidth();
			heightChange *= (double)Pgroup.newEx.getHeight()/Pgroup.ex.getHeight();
		}
		
		else {
			widthChange = (double)newEx.getWidth()/ex.getWidth();
			heightChange = (double)newEx.getHeight()/ex.getHeight();
		}
		
		IntVector innerCoordinates = new IntVector((int)(globalCoordinates.getX() / widthChange), 
				(int)(globalCoordinates.getY() / heightChange));
		return innerCoordinates;
	}
	
	
	
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		for (ShapeGroup child: children) {
			if (child.newEx.contains(innerCoordinates))
				return child;
		}
		return null;
	}
	
	
	
	public void setExtent(Extent newExtent) {
		if (newExtent == null)
			throw new IllegalArgumentException("Extent cannot be null!");
		else
			if (ex == null) { 
				ex = newExtent;
				prevEx = newExtent;
			}
			else
				prevEx = newEx;
			newEx = newExtent;
	}
	
	
	public void bringToFront() {
		int i = 0;
		while (this != parent.children[i])
			i++;
		while (i > 0) {
			parent.children[i] = parent.children[i-1];
			i--;
		}
		parent.children[0] = this;
	}
	
	
	
	public void sendToBack() {
		int i = 0;
		while (this != parent.children[i])
			i++;
		while (i < parent.getSubgroupCount()-1) {
			parent.children[i] = parent.children[i+1];
			i++;
		}
		parent.children[parent.children.length-1] = this;
		
	}

	
	
	public java.lang.String getDrawingCommands(){
		

		String result = "";
		
		if (nonLeaf != null) {
		
			result += push(this);
			result += drawNonLeaf(this);
			result += "popTransform \r\n";
			result += "popTransform \r\n";
		}

		
		else if (leaf != null) {
			
			result += push(this);
			
			result += leaf.getDrawingCommands();
			
			result += "popTransform \r\n";
			result += "popTransform \r\n";
			
		}
		

		return result;
	}
	
	
	public java.lang.String drawNonLeaf(ShapeGroup subgroup) {
		String result = "";
		
		for (int i = subgroup.getSubgroupCount()-1; i >= 0; i--) {
			if (subgroup.children[i].leaf == null) {
				result += push(subgroup.children[i]);
				for (ShapeGroup grandChild: subgroup.children[i].children)
					result += drawNonLeaf(subgroup.children[i]);
				result += "popTransform \r\n";
				result += "popTransform \r\n";

			}
			else {	
				result += push(subgroup.children[i]);
				result += subgroup.children[i].leaf.getDrawingCommands();
				result += "popTransform \r\n";
				result += "popTransform \r\n";
			}
		}
		
		return result;
	}
	
	
	
	public java.lang.String push(ShapeGroup subgroup){
		String result = "";
		
		double widthFactor = (double)subgroup.getExtent().getWidth()/subgroup.getOriginalExtent().getWidth();
		double heightFactor = (double)subgroup.getExtent().getHeight()/subgroup.getOriginalExtent().getHeight();
		
		
		if (!subgroup.newEx.getTopLeft().equals(subgroup.prevEx.getTopLeft())) {
			double changeR = subgroup.getExtent().getRight() -subgroup.getOriginalExtent().getRight();
			double changeB = subgroup.getExtent().getBottom() - subgroup.getOriginalExtent().getBottom();
			subgroup.changeXr = (1-(widthFactor-changeR/subgroup.getOriginalExtent().getWidth()))*subgroup.getOriginalExtent().getRight();
			subgroup.changeYb = (1-(heightFactor-changeB/subgroup.getOriginalExtent().getHeight()))*subgroup.getOriginalExtent().getBottom();
		}
		
			
		else if (!subgroup.newEx.getBottomRight().equals(subgroup.prevEx.getBottomRight())) {
			double changeL = -subgroup.getExtent().getLeft() + subgroup.getOriginalExtent().getLeft();
			double changeT = -subgroup.getExtent().getTop() + subgroup.getOriginalExtent().getTop();
			subgroup.changeXl = (1-(widthFactor-changeL/subgroup.getOriginalExtent().getWidth()))*subgroup.getOriginalExtent().getLeft();
			subgroup.changeYt = (1-(heightFactor-changeT/subgroup.getOriginalExtent().getHeight()))*subgroup.getOriginalExtent().getTop();
		}
				
		double changeX = subgroup.changeXr + subgroup.changeXl;
		double changeY = subgroup.changeYb + subgroup.changeYt;
		
		
		result += "pushTranslate " + changeX + " " + changeY + "\r\n";
		result += "pushScale " + widthFactor + " " + heightFactor + "\r\n";
		
		return result;
	}
	
}