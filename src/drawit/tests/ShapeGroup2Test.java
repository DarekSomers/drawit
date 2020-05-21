package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;
import drawit.shapegroups2.Extent;
import drawit.shapegroups2.LeafShapeGroup;
import drawit.shapegroups2.NonleafShapeGroup;
import drawit.shapegroups2.ShapeGroup;

class ShapeGroup2Test {

static IntPoint p(int x, int y) { return new IntPoint(x, y); }
	
	
	static boolean assertEquals(int left, int top, int right, int bottom, Extent actual) {
		if (actual.getLeft() == left && actual.getTop() == top && actual.getRight() == right && actual.getBottom() == bottom)
			return true;
		else
			return false;
		}
	
	static void assertEquals(int x, int y, IntPoint p) {
		assert p.getX() == x;
		assert p.getY() == y;
	}
	
	static void assertEquals(int x, int y, IntVector v) {
		assert v.getX() == x;
		assert v.getY() == y;
	}
	
	static boolean assertEquals(ShapeGroup expected, ShapeGroup actual) {
		if (expected instanceof LeafShapeGroup && actual instanceof LeafShapeGroup) {
			if (((LeafShapeGroup) expected).getShape() == ((LeafShapeGroup) actual).getShape())
				return true;
		}
		else if (expected instanceof NonleafShapeGroup && actual instanceof NonleafShapeGroup) {
			for (int i = 0; i < ((NonleafShapeGroup) expected).getSubgroupCount(); i++) {
				if (((NonleafShapeGroup) expected).getSubgroup(i) != ((NonleafShapeGroup) actual).getSubgroup(i))
					return false;
			}
			return true;
		}
		return false;
	}
	
	
	@Test
	void test(){
		
		IntPoint[] vert1 = {p(0,0), p(10,0), p(10,10), p(0,10)};
		IntPoint[] vert2 = {p(2,6), p(12,6), p(12,16), p(2,16)};
		IntPoint[] vert3 = {p(20,20), p(50,20), p(40,50)};
		
		RoundedPolygon pol1 = new RoundedPolygon();
		RoundedPolygon pol2 = new RoundedPolygon();
		RoundedPolygon pol3 = new RoundedPolygon();
		{pol1.setVertices(vert1); 
		pol2.setVertices(vert2);
		pol3.setVertices(vert3);}
		
		ShapeGroup leaf1 = new LeafShapeGroup(pol1);
		LeafShapeGroup leaf2 = new LeafShapeGroup(pol2);
		LeafShapeGroup leaf3 = new LeafShapeGroup(pol3);
		ShapeGroup[] leafArray = {(LeafShapeGroup) leaf1, leaf2};
		ShapeGroup[] leafArrayCopy = new ShapeGroup[leafArray.length];
		for (int i = 0; i < leafArray.length; i++)
			leafArrayCopy[i] = leafArray[i];
			
		NonleafShapeGroup nonleaf1 = new NonleafShapeGroup(leafArray);
		ShapeGroup[] nonleafArray = {nonleaf1, leaf3};
		ShapeGroup[] nonleafArrayCopy = new ShapeGroup[nonleafArray.length];
		for (int i = 0; i < nonleafArray.length; i++)
			nonleafArrayCopy[i] = nonleafArray[i];

		NonleafShapeGroup nonleaf2 = new NonleafShapeGroup(nonleafArrayCopy);
		leaf2.setExtent(Extent.ofLeftTopRightBottom(5, 10, 15, 20));
		
		//LeafShapeGroup
		//getShape
		assert ((LeafShapeGroup) leaf1).getShape() == pol1;
		assert leaf2.getShape() == pol2;
		
		//getExtent/setExtent
		assert assertEquals(0,0,10,10, leaf1.getExtent()) == true;
		assert assertEquals(2,6,12,16, leaf2.getExtent()) == false;
		assert assertEquals(5,10,15,20, leaf2.getExtent()) == true;
		
		//getOriginalExtent
		assert assertEquals(2,6,12,16, leaf2.getOriginalExtent()) == true;
		
		//getParentGroup/setParent
		assert leaf1.getParentGroup() == nonleaf1;
		assert leaf2.getParentGroup() == nonleaf1;
		assert leaf3.getParentGroup() != nonleaf1;
		
		//toInnerCoordinates
		assertEquals(5,5, leaf1.toInnerCoordinates(p(5,5)));
		assertEquals(5,5, leaf2.toInnerCoordinates(p(8,9)));
		
		//toOuterCoordinates
		assertEquals(5,5, leaf1.toGlobalCoordinates(p(5,5)));
		assertEquals(8,9, leaf2.toGlobalCoordinates(p(5,5)));
		
		//toInnerCoordinates (IntVector)
		assertEquals(5,5, leaf1.toInnerCoordinates(new IntVector(5,5)));
		assertEquals(5,5, leaf2.toInnerCoordinates(new IntVector(5,5)));
		
		//toInnerCoordinates (IntVector with scaling)
		leaf3.setExtent(Extent.ofLeftTopRightBottom(10, 10, 25, 25));
		assertEquals(5,5, leaf3.toInnerCoordinates(new IntVector(5,5)));
		
		
		//NonleafShapeGroup
		//getExtent/setExtent
		nonleaf1.setExtent(Extent.ofLeftTopRightBottom(10, 10, 34, 42));
		assert assertEquals(10,10,34,42, nonleaf1.getExtent()) == true;
		nonleaf2.setExtent(Extent.ofLeftTopRightBottom(0, 0, 25, 25));
		assert assertEquals(0,0,25,25, nonleaf2.getExtent()) == true;

		//getOriginalExtent
		assert assertEquals(0,0,12,16, nonleaf1.getOriginalExtent()) == true;
		assert assertEquals(0,0,50,50, nonleaf2.getOriginalExtent()) == true;
				
		//getParentGroup/setParent
		assert nonleaf1.getParentGroup() == nonleaf2;
		assert nonleaf2.getParentGroup() == null;
		
		//getSubgroups
		Iterator<ShapeGroup> it = nonleaf1.getSubgroups().iterator();
			assert it.next() == leafArray[0];
			assert it.next() == leafArray[1];
		Iterator<ShapeGroup> it2 = nonleaf2.getSubgroups().iterator();
			assert it2.next() == nonleaf1;
			assert it2.next() == leaf3;
			
		//getSubgroupCount
		assert nonleaf1.getSubgroupCount() == 2;
		assert nonleaf2.getSubgroupCount() == 2;
		
		//getSubgroup
		assert nonleaf1.getSubgroup(0) == leafArray[0];
		assert nonleaf1.getSubgroup(1) == leafArray[1];
		assert nonleaf2.getSubgroup(0) == nonleafArray[0];
		assert nonleaf2.getSubgroup(1) == nonleafArray[1];
		
		//toInnerCoordinates
		assertEquals(15,15, nonleaf1.toInnerCoordinates(p(20,20)));
		assertEquals(10,10, nonleaf2.toInnerCoordinates(p(5,5)));
		
		//toOuterCoordinates
		assertEquals(10,10, nonleaf1.toGlobalCoordinates(p(5,5)));
		assertEquals(5,5, nonleaf2.toGlobalCoordinates(p(10,10)));
		
		//toInnerCoordinates (IntVector)
		assertEquals(10,10, nonleaf1.toInnerCoordinates(new IntVector(5,5)));
		assertEquals(5,5, nonleaf2.toInnerCoordinates(new IntVector(5,5)));
		
		//bringToFront/sentToBack
		nonleaf1.getSubgroup(1).bringToFront();
		assertEquals(leafArray[1], nonleaf1.getSubgroup(0)); 
		nonleaf2.getSubgroup(0).sendToBack();
		assertEquals(nonleafArray[1], nonleaf2.getSubgroup(1));
		
		//getSubgroupAt
		assert nonleaf1.getSubgroupAt(p(1,1)) == leaf1;
		assert nonleaf2.getSubgroupAt(p(100,100)) == null;

	}

}
