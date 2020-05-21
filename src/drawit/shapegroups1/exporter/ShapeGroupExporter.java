package drawit.shapegroups1.exporter;

import drawit.IntPoint;
import drawit.shapegroups1.LeafShapeGroup;
import drawit.shapegroups1.NonleafShapeGroup;
import drawit.shapegroups1.ShapeGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShapeGroupExporter {
//Kaput
	public static Object toPlainData(ShapeGroup shapeGroup) {
		Object result;
		
		if (shapeGroup instanceof LeafShapeGroup) {
			result = Map.of(
					"originalExtent", Map.of("left", shapeGroup.getOriginalExtent().getLeft(), "top", shapeGroup.getOriginalExtent().getTop(), 
							"right", shapeGroup.getOriginalExtent().getRight(), "bottom", shapeGroup.getOriginalExtent().getBottom()),
					"extent", Map.of("left", shapeGroup.getExtent().getLeft(), "top", shapeGroup.getExtent().getTop(), 
							"right", shapeGroup.getExtent().getRight(), "bottom", shapeGroup.getExtent().getBottom()),
					"previousExtent", Map.of("left", shapeGroup.getPreviousExtent().getLeft(), "top", shapeGroup.getPreviousExtent().getTop(), 
							"right", shapeGroup.getPreviousExtent().getRight(), "bottom", shapeGroup.getPreviousExtent().getBottom()),
					"shape", Map.of(
							"vertices", vertices(shapeGroup),
							"radius", ((LeafShapeGroup) shapeGroup).getShape().getRadius(),
							"color", Map.of("red", ((LeafShapeGroup) shapeGroup).getShape().getColor().getRed(), "green", ((LeafShapeGroup) shapeGroup).getShape().getColor().getGreen(), "blue", ((LeafShapeGroup) shapeGroup).getShape().getColor().getBlue()))
					
					);
		}
		else {
		result = Map.of(
				"originalExtent", Map.of("left", shapeGroup.getOriginalExtent().getLeft(), "top", shapeGroup.getOriginalExtent().getTop(), 
						"right", shapeGroup.getOriginalExtent().getRight(), "bottom", shapeGroup.getOriginalExtent().getBottom()),
				"extent", Map.of("left", shapeGroup.getExtent().getLeft(), "top", shapeGroup.getExtent().getTop(), 
						"right", shapeGroup.getExtent().getRight(), "bottom", shapeGroup.getExtent().getBottom()),
				"previousExtent", Map.of("left", shapeGroup.getPreviousExtent().getLeft(), "top", shapeGroup.getPreviousExtent().getTop(), 
						"right", shapeGroup.getPreviousExtent().getRight(), "bottom", shapeGroup.getPreviousExtent().getBottom()),
				"subgroups", subs(shapeGroup));
		}
		
		
		
		return result;
	}
	
	public static List<Object> vertices(ShapeGroup shapeGroup){
		List<Object> result = new ArrayList<Object>();
		for (int i = 0; i < ((LeafShapeGroup) shapeGroup).getShape().getVertices().length; i++) {
			result.add(Map.of("x", ((LeafShapeGroup) shapeGroup).getShape().getVertices()[i].getX(), "y", ((LeafShapeGroup) shapeGroup).getShape().getVertices()[i].getY()));
		}
		return result;
	}
	
	public static List<Object> subs(ShapeGroup shapeGroup) {
		List<Object> result = new ArrayList<Object>();
		for (ShapeGroup child: ((NonleafShapeGroup) shapeGroup).getSubgroups()) {
			result.add(toPlainData(child));
		}
		return result;
	}
	
}
