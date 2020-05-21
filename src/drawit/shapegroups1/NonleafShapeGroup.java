package drawit.shapegroups1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import drawit.IntPoint;

public class NonleafShapeGroup extends ShapeGroup {

	
	

	private ShapeGroup[] nonLeaf;

	
	public NonleafShapeGroup(ShapeGroup[] subgroups) {
		nonLeaf = subgroups;
		ShapeGroup[] subgroup = nonLeaf;
		int minX = subgroup[0].getExtent().getLeft();
		int maxX = subgroup[0].getExtent().getRight();
		int minY = subgroup[0].getExtent().getBottom();
		int maxY = subgroup[0].getExtent().getTop();
		for (int i = 0; i < getSubgroupCount(); i++) {
			
			getSubgroup(i).setParent(this);
			if (getSubgroup(i).getExtent().getLeft() < minX)
				minX = getSubgroup(i).getExtent().getLeft();
			if (getSubgroup(i).getExtent().getRight() > maxX)
				maxX = getSubgroup(i).getExtent().getRight();
			if (getSubgroup(i).getExtent().getTop() < minY)
				minY = getSubgroup(i).getExtent().getTop();
			if (getSubgroup(i).getExtent().getBottom() > maxY)
				maxY = getSubgroup(i).getExtent().getBottom();
		}
		setExtent(Extent.ofLeftTopRightBottom(minX, minY, maxX, maxY));
	}
	
	

	
	public java.util.List<ShapeGroup> getSubgroups(){
		List<ShapeGroup> list = new ArrayList<ShapeGroup>();
		for (ShapeGroup shape: nonLeaf) 
			list.add(shape);
		return list;
	}
	
	
	public void setSubgroups(List<ShapeGroup> subgroups) {
		Iterator<ShapeGroup> it = subgroups.iterator();
		for (int i = 0; i < nonLeaf.length; i++) 
			nonLeaf[i] = it.next();
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
	
	
	public ShapeGroup getSubgroupAt(IntPoint innerCoordinates) {
	for (ShapeGroup child: getSubgroups()) {
		if (child.getExtent().contains(innerCoordinates))
			return child;
	}
	return null;
}

	
	@Override
	public java.lang.String getDrawingCommands() {
			
	
		String result = "";
		
		result += push(this);
		result += drawNonLeaf(this);
		result += "popTransform \r\n";
		result += "popTransform \r\n";

		

		return result;
	}
	
	
	public java.lang.String drawNonLeaf(NonleafShapeGroup subgroup) {
		String result = "";
		
		for (int i = subgroup.getSubgroupCount()-1; i >= 0 ; i--) {
			if (subgroup.getSubgroup(i) instanceof NonleafShapeGroup) {
				result += push(subgroup.getSubgroup(i));
				for (ShapeGroup grandChild: ((NonleafShapeGroup) subgroup.getSubgroup(i)).getSubgroups())
					result += drawNonLeaf((NonleafShapeGroup) subgroup.getSubgroup(i));
				result += "popTransform \r\n";
				result += "popTransform \r\n";

			}
			else {	
				result += push(subgroup.getSubgroup(i));
				result += ((LeafShapeGroup) subgroup.getSubgroup(i)).getShape().getDrawingCommands();
				result += "popTransform \r\n";
				result += "popTransform \r\n";
			}
		}
		
		return result;
	}

	
}
