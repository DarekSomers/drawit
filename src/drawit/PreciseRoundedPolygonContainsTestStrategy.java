package drawit;

public class PreciseRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {

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
