package drawit;

public class RoundedPolygon { 
	
	private IntPoint[] vertices;
	private int rad;
	
	
	public RoundedPolygon() {
	}

	public IntPoint[] getVertices() {
		return PointArrays.copy(vertices);
	}
	
	public int getRadius() {
		return rad;
	}
	
	public void setVertices(IntPoint[] newVertices) {
		vertices = PointArrays.copy(newVertices);
	}
	
	public void setRadius(int radius) {
		rad = radius;
	}
	
	public void insert(int index, IntPoint point) {
		vertices = PointArrays.insert(vertices, index, point);
	}
	
	public void remove(int index) {
		vertices = PointArrays.remove(vertices, index);
	}
	
	public void update(int index, IntPoint point) {
		vertices = PointArrays.update(vertices, index, point);
	}
	
	public boolean contains(IntPoint point) {
		int intersection = 0;
		
		for (IntPoint vertex: vertices) {
			if (point.equals(vertex)) {
				return true;
			}
		}
		
		for (int i = 0; i < vertices.length; i++) {
			if (point.isOnLineSegment(vertices[i-1], vertices[i]))
				return true;
			
			if (IntPoint.lineSegmentsIntersect(point, new IntPoint(1000000, point.getY()), vertices[i-1], vertices[i]))
				intersection++;
		}
		
		if (intersection%2 == 1)
			return true;
		
		else
			return false;
	}
	public String getDrawingCommands() {
		String result;
		
		if (vertices.length < 3) {
			result = null;
			return result;
		}
		
		
	}
	
}
