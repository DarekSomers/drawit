package drawit.shapes2;

public interface ControlPoint {

	drawit.IntPoint getLocation();
	
	void remove();
	
	void move(drawit.IntVector delta);
	
}
