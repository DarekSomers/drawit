package drawit.shapegroups1;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
	}
	
	
	public ShapeGroup(ShapeGroup[] subgroups) {
		nonLeaf = subgroups;
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
		int widthChange = newEx.getWidth()/ex.getWidth();
		int heightChange = newEx.getHeight()/ex.getHeight();
		int leftChange = newEx.getLeft()-ex.getLeft();
		int topChange = newEx.getTop()-ex.getTop();
		int relativeLeft = (globalCoordinates.getX() - newEx.getLeft()) * (1 - widthChange);
		int relativeTop = (globalCoordinates.getY() - newEx.getTop()) * (1 - heightChange);
		
		
		IntPoint innerCoordinates = new IntPoint(globalCoordinates.getX() + leftChange + relativeLeft, globalCoordinates.getY() + topChange + relativeTop);
		return innerCoordinates;
	}
	
	
	
	public IntPoint toGlobalCoordinates(IntPoint innerCoordinates) {
		int widthChange = newEx.getWidth()/ex.getWidth();
		int heightChange = newEx.getHeight()/ex.getHeight();
		int leftChange = newEx.getLeft()-ex.getLeft();
		int topChange = newEx.getTop()-ex.getTop();
		int relativeLeft = (innerCoordinates.getX() - ex.getLeft()) * (widthChange - 1);
		int relativeTop = (innerCoordinates.getY() - ex.getTop()) * (heightChange - 1);
		
		
		IntPoint globalCoordinates = new IntPoint(innerCoordinates.getX() + leftChange + relativeLeft, innerCoordinates.getY() + topChange + relativeTop);
		return globalCoordinates;
	}
	
	
	
	public IntVector toInnerCoordinates(IntVector globalCoordinates) {
		int widthChange = newEx.getWidth()/ex.getWidth();
		int heightChange = newEx.getHeight()/ex.getHeight();
		
		IntVector innerCoordinates = new IntVector(globalCoordinates.getX() * widthChange, globalCoordinates.getY() * heightChange);
		return innerCoordinates;
	}
	
	
	
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
		for (ShapeGroup child: this.children) {
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
		while (i < parent.children.length-1) {
			parent.children[i] = parent.children[i+1];
			i++;
		}
		parent.children[parent.children.length-1] = this;
	}

	
	
	public java.lang.String getDrawingCommands(){
		

		String result = "";
		
		if (leaf == null) {
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
				if (children[i].getExtent().getBottom() < minY)
					minY = children[i].getExtent().getBottom();
				if (children[i].getExtent().getTop() > maxY)
					maxY = children[i].getExtent().getTop();
			}
				
			setExtent(Extent.ofLeftTopRightBottom(minX, maxY, maxX, minY));
			result += push(result, this);
			result += drawNonLeaf(result, this);
			result += "popTransform \r\n";
			result += "popTransform \r\n";
		}
		
		else if (getOriginalExtent() == null || getExtent() == null) {
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
			
			setExtent(Extent.ofLeftTopRightBottom(minX, maxY, maxX, minY));
			
			result += leaf.getDrawingCommands();
			
		}
		
		else {
			
			result += push(result, this);
		
			result += leaf.getDrawingCommands();
			
			result += "popTransform \r\n";
			result += "popTransform \r\n";
		}

		return result;
	}
	
	
	public java.lang.String drawNonLeaf(String result, ShapeGroup subgroup) {
		
		
		for (ShapeGroup child: subgroup.children) {
			if (child.leaf == null) {
				for (ShapeGroup grandChild: child.children)
					result += push(result, child);
					result += drawNonLeaf(result, child);
					result += "popTransform \r\n";
					result += "popTransform \r\n";
			}
			else {	
				result += push(result, child);
				result += child.leaf.getDrawingCommands();
				result += "popTransform \r\n";
				result += "popTransform \r\n";
			}
		}
		
		return result;
	}
	
	
	
	public java.lang.String push(String result, ShapeGroup subgroup){
		
		double widthFactor = (double)subgroup.getExtent().getWidth()/subgroup.getOriginalExtent().getWidth();
		double heightFactor = (double)subgroup.getExtent().getHeight()/subgroup.getOriginalExtent().getHeight();
		
		
		if (!subgroup.newEx.getTopLeft().equals(subgroup.prevEx.getTopLeft())) {
			double changeR = subgroup.getExtent().getRight() -subgroup.getOriginalExtent().getRight();
			double changeB = -subgroup.getExtent().getBottom() + subgroup.getOriginalExtent().getBottom();
			changeXr = (1-(widthFactor-changeR/subgroup.getOriginalExtent().getWidth()))*subgroup.getOriginalExtent().getRight();
			changeYb = (1-(heightFactor-changeB/subgroup.getOriginalExtent().getHeight()))*subgroup.getOriginalExtent().getBottom();
		}
		
			
		else if (!subgroup.newEx.getBottomRight().equals(subgroup.prevEx.getBottomRight())) {
			double changeL = -subgroup.getExtent().getLeft() + subgroup.getOriginalExtent().getLeft();
			double changeT = subgroup.getExtent().getTop() - subgroup.getOriginalExtent().getTop();
			changeXl = (1-(widthFactor-changeL/subgroup.getOriginalExtent().getWidth()))*subgroup.getOriginalExtent().getLeft();
			changeYt = (1-(heightFactor-changeT/subgroup.getOriginalExtent().getHeight()))*subgroup.getOriginalExtent().getTop();
		}
				
		double changeX = changeXr + changeXl;
		double changeY = changeYb + changeYt;
		
		
		result += "pushTranslate " + changeX + " " + changeY + "\r\n";
		result += "pushScale " + widthFactor + " " + heightFactor + "\r\n";
		
		return result;
	}

}
