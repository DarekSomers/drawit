package drawit.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import drawit.IntPoint;
import drawit.IntVector;
import drawit.RoundedPolygon;
import drawit.shapegroups1.Extent;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.ShapeGroup;
import drawit.shapegroups1.exporter.ShapeGroupExporter;

class ShapeGroupExporterTest {

		static IntPoint p(int x, int y) { return new IntPoint(x, y); }
		
		
//		static boolean assertEquals(int left, int top, int right, int bottom, Extent actual) {
//			if (actual.getLeft() == left && actual.getTop() == top && actual.getRight() == right && actual.getBottom() == bottom)
//				return true;
//			else
//				return false;
//			}
//		
//		static void assertEquals(int x, int y, IntPoint p) {
//			assert p.getX() == x;
//			assert p.getY() == y;
//		}
//		
//		static void assertEquals(int x, int y, IntVector v) {
//			assert v.getX() == x;
//			assert v.getY() == y;
//		}
//		
//		static boolean assertEquals(ShapeGroup expected, ShapeGroup actual) {
//			if (expected instanceof LeafShapeGroup && actual instanceof LeafShapeGroup) {
//				if (((LeafShapeGroup) expected).getShape() == ((LeafShapeGroup) actual).getShape())
//					return true;
//			}
//			else if (expected instanceof NonleafShapeGroup && actual instanceof NonleafShapeGroup) {
//				for (int i = 0; i < ((NonleafShapeGroup) expected).getSubgroupCount(); i++) {
//					if (((NonleafShapeGroup) expected).getSubgroup(i) != ((NonleafShapeGroup) actual).getSubgroup(i))
//						return false;
//				}
//				return true;
//			}
//			return false;
//		}
		
		
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
			
			
			new ShapeGroupExporter();
			Assert.assertEquals(Map.of("originalExtent", Map.of("left", 0, "top", 0, 
						"right", 50, "bottom", 50),
				"extent", Map.of("left", 0, "top", 0, 
						"right", 50, "bottom", 50),
				"previousExtent", Map.of("left", 0, "top", 0, 
						"right", 50, "bottom", 50),
				"subgroups", List.of(
						Map.of("originalExtent", Map.of("left", 0, "top", 0, 
								"right", 12, "bottom", 16),
								"extent", Map.of("left", 0, "top", 0, 
										"right", 12, "bottom", 16),
								"previousExtent", Map.of("left", 0, "top", 0, 
										"right", 12, "bottom", 16),
								"subgroups", List.of(
										Map.of(
												"originalExtent", Map.of("left", 0, "top", 0, 
														"right", 10, "bottom", 10),
														"extent", Map.of("left", 0, "top", 0, 
																"right", 10, "bottom", 10),
														"previousExtent", Map.of("left", 0, "top", 0, 
																"right", 10, "bottom", 10),
												"shape", Map.of(
														"vertices", ShapeGroupExporter.vertices(leaf1),
														"radius", ((LeafShapeGroup) leaf1).getShape().getRadius(),
														"color", Map.of("red", ((LeafShapeGroup) leaf1).getShape().getColor().getRed(), 
																"green", ((LeafShapeGroup) leaf1).getShape().getColor().getGreen(), 
																"blue", ((LeafShapeGroup) leaf1).getShape().getColor().getBlue()))),
										Map.of(
												"originalExtent", Map.of("left", 2, "top", 6, 
														"right", 12, "bottom", 16),
														"extent", Map.of("left", 2, "top", 6, 
																"right", 12, "bottom", 16),
														"previousExtent", Map.of("left", 2, "top", 6, 
																"right", 12, "bottom", 16),
												"shape", Map.of(
														"vertices", ShapeGroupExporter.vertices(leaf2),
														"radius", ((LeafShapeGroup) leaf2).getShape().getRadius(),
														"color", Map.of("red", ((LeafShapeGroup) leaf2).getShape().getColor().getRed(), 
																"green", ((LeafShapeGroup) leaf2).getShape().getColor().getGreen(), 
																"blue", ((LeafShapeGroup) leaf2).getShape().getColor().getBlue()))))),
						Map.of(
								"originalExtent", Map.of("left", 20, "top", 20, 
										"right", 50, "bottom", 50),
										"extent", Map.of("left", 20, "top", 20, 
												"right", 50, "bottom", 50),
										"previousExtent", Map.of("left", 20, "top", 20, 
												"right", 50, "bottom", 50),
								"shape", Map.of(
										"vertices", ShapeGroupExporter.vertices(leaf3),
										"radius", ((LeafShapeGroup) leaf3).getShape().getRadius(),
										"color", Map.of("red", ((LeafShapeGroup) leaf3).getShape().getColor().getRed(), 
												"green", ((LeafShapeGroup) leaf3).getShape().getColor().getGreen(), 
												"blue", ((LeafShapeGroup) leaf3).getShape().getColor().getBlue())))
								)), ShapeGroupExporter.toPlainData(nonleaf2));
	}
//		java.lang.AssertionError: expected:<{previousExtent={left=0, bottom=50, right=50, top=0}, extent={left=0, bottom=50, right=50, top=0}, originalExtent={left=0, bottom=50, right=50, top=0}, subgroups=[{previousExtent={left=0, bottom=16, right=12, top=0}, extent={left=0, bottom=16, right=12, top=0}, originalExtent={left=0, bottom=16, right=12, top=0}, subgroups=[{previousExtent={left=0, bottom=10, right=10, top=0}, extent={left=0, bottom=10, right=10, top=0}, originalExtent={left=0, bottom=10, right=10, top=0}, shape={color={red=255, blue=0, green=255}, vertices=[{x=0, y=0}, {x=10, y=0}, {x=10, y=10}, {x=0, y=10}], radius=0}}, {previousExtent={left=2, bottom=16, right=12, top=6}, extent={left=2, bottom=16, right=12, top=6}, originalExtent={left=2, bottom=16, right=12, top=6}, shape={color={red=255, blue=0, green=255}, vertices=[{x=2, y=6}, {x=12, y=6}, {x=12, y=16}, {x=2, y=16}], radius=0}}]}, {previousExtent={left=0, bottom=10, right=10, top=0}, extent={left=0, bottom=10, right=10, top=0}, originalExtent={left=0, bottom=10, right=10, top=0}, shape={color={red=255, blue=0, green=255}, vertices=[{x=0, y=0}, {x=10, y=0}, {x=10, y=10}, {x=0, y=10}], radius=0}}]}> 
//		but 						   was:<{previousExtent={left=0, bottom=50, right=50, top=0}, extent={left=0, bottom=50, right=50, top=0}, originalExtent={left=0, bottom=50, right=50, top=0}, subgroups=[{previousExtent={left=0, bottom=16, right=12, top=0}, extent={left=0, bottom=16, right=12, top=0}, originalExtent={left=0, bottom=16, right=12, top=0}, subgroups=[{previousExtent={left=0, bottom=10, right=10, top=0}, extent={left=0, bottom=10, right=10, top=0}, originalExtent={left=0, bottom=10, right=10, top=0}, shape={color={red=255, blue=0, green=255}, vertices=[{x=0, y=0}, {x=10, y=0}, {x=10, y=10}, {x=0, y=10}], radius=0}}, {previousExtent={left=2, bottom=16, right=12, top=6}, extent={left=5, bottom=20, right=15, top=10}, originalExtent={left=2, bottom=16, right=12, top=6}, shape={color={red=255, blue=0, green=255}, vertices=[{x=2, y=6}, {x=12, y=6}, {x=12, y=16}, {x=2, y=16}], radius=0}}]}, {previousExtent={left=20, bottom=50, right=50, top=20}, extent={left=20, bottom=50, right=50, top=20}, originalExtent={left=20, bottom=50, right=50, top=20}, shape={color={red=255, blue=0, green=255}, vertices=[{x=20, y=20}, {x=50, y=20}, {x=40, y=50}], radius=0}}]}>
		

}
