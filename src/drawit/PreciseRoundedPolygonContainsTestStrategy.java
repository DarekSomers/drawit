package drawit;


/**
 * Each instance of this class tests whether a point is contained by the RoundedPolygon object
 */
public class PreciseRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {

	/**
	 * Tests whether the given IntPoint is contained by the given RoundedPolygon object
	 * @throws IllegalArgumentException when either the given polygon or point is null
	 * 		|!(polygon == null || point == null)
	 * @post Returns true when the given point is contained by the given polygon, in other words when the amount of 
	 * 		 intersections between the polygon and the exit path is odd. Returns false otherwise
	 * 		| polygon.contains(point) == true || polygon.contains(point) == false
	 * @post The given point is also cointained by the boudingBox of the polygon when it is contained by the polygon itself
	 * 		| (polygon.contains(point) == true && polygon.BoxContains(point) == true) ||
	 * 		| polygon.contains(point) == false
	 * @inspects | polygon.getVertices()
	 */
	@Override
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		
		if (polygon == null || point == null)
			throw new IllegalArgumentException("Polygon and/or point is null");
			
		int intersection = 0;
		
		for (IntPoint vertex: polygon.getVertices()) {
			if (point.equals(vertex)) {
				return true;
			}
		}
		IntPoint exitPath = new IntPoint(1000000, point.getY());
		for (int i = 0; i < polygon.getVertices().length; i++) {
			if (i != 0 && point.isOnLineSegment(polygon.getVertices()[i-1], polygon.getVertices()[i])) 
				return true;
			else if (i == 0 && point.isOnLineSegment(polygon.getVertices()[polygon.getVertices().length-1], polygon.getVertices()[i]))
				return true;
			
			if (polygon.getVertices()[i].isOnLineSegment(point, exitPath)) {
				if (i == 0 && polygon.getVertices()[polygon.getVertices().length-1].minus(point).crossProduct(exitPath.minus(point)) * polygon.getVertices()[i+1].minus(point).crossProduct(exitPath.minus(point)) < 0)
					intersection++;
				if (i == polygon.getVertices().length-1 && polygon.getVertices()[i-1].minus(point).crossProduct(exitPath.minus(point)) * polygon.getVertices()[0].minus(point).crossProduct(exitPath.minus(point)) < 0)
					intersection++;
				if (i != 0 && i!= polygon.getVertices().length-1 && polygon.getVertices()[i-1].minus(point).crossProduct(exitPath.minus(point)) * polygon.getVertices()[i+1].minus(point).crossProduct(exitPath.minus(point)) < 0)
					intersection++;
			}
			if (i != 0 && IntPoint.lineSegmentsIntersect(point, exitPath, polygon.getVertices()[i-1], polygon.getVertices()[i]))
				intersection++;
			else if(i == 0 && IntPoint.lineSegmentsIntersect(point, exitPath, polygon.getVertices()[polygon.getVertices().length-1], polygon.getVertices()[i]))
				intersection++;
		}
		
		if (intersection%2 == 1)
			return true;
		
		else
			return false;
	}

}
