package drawit;

/**
 * 
 * @immutable
 *
 */
public interface RoundedPolygonContainsTestStrategy {
	
//	/**
//	 * Returns true when the conditions of the requested subclass are fulfilled, the point is contained whithin the bounds of the bounding box or the polygon.
//	 * @post If the contains call is for the fast subclass, returns true when the point is contained by the bounding box and false otherwise.
//	 * 		 If the contains call is for the precise subclass, returns true when the point is contained by the polygon and false otherwise.
//	 * 		| (this instanceof FastRoundedPolygonContainsTestStrategy && polygon.BoxContains(point) ? true:false) ||
//	 * 		| (this instanceof PreciseRoundedPolygonContainsTestStrategy && polygon.contains(point) ? true:false)
//	 * @return true || false
//	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point);
	
}
