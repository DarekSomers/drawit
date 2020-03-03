package drawit;

public class RoundedPolygon { 
	
	public IntPoint[] vertices;
	
	public int rad;
	
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
		
	}
	public String getDrawingCommands() {
		
	}
	
}
