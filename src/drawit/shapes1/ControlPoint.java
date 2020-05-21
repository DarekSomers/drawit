package drawit.shapes1;

public interface ControlPoint {

	drawit.IntPoint getLocation();
	
	void remove();
	
	void move(drawit.IntVector delta);
	
}
