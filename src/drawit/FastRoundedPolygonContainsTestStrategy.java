package drawit;

public class FastRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {

	@Override
	public boolean contains(RoundedPolygon polygon, IntPoint point) {
		if (point.getX() >= polygon.getBox()[0] && point.getY() >= polygon.getBox()[1] &&
				point.getX() <= polygon.getBox()[2] && point.getY() <= polygon.getBox()[3]) {
			return true;
		}
		else
			return false;
	}

}
