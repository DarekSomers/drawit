package drawit;

/**
 * Each instance of this class tests whether a point is contained by the boundingBox of a RoundedPolygon object
 */
public class FastRoundedPolygonContainsTestStrategy implements RoundedPolygonContainsTestStrategy {

	
	/**
	 * Tests whether the given point is contained by the boundingBox of the given polygon
	 * @pre the given RoundedPolygon is not null
	 * 		| polygon != null
	 * @pre the given IntPoint is not null
	 * 		| point != null
	 * @inspects | polygon.getBox()
	 * @post Returns true when the point is contained by the boundingBox of the given polygon, or false otherwise
	 * 		| result == true || result == false
	 */
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
