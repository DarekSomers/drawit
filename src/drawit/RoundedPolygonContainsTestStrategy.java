package drawit;

/**
 * 
 * @immutable
 * @invar A RoundedPolygonContainsTestStrategy is either a Fast- or PreciseRoundedPolygonContainsTestStrategy
 * 		| (this instanceof FastRoundedPolygonContainsTestStrategy || this instanceof PreciseRoundedPolygonContainsTestStrategy) && 
 * 		| !(this instanceof FastRoundedPolygonContainsTestStrategy && this instanceof PreciseRoundedPolygonContainsTestStrategy)
 */
public interface RoundedPolygonContainsTestStrategy {
	
	/**
	 * Returns true when the conditions of the requested subclass are fulfilled, the point is contained whithin the bounds of the bounding box or the polygon.
	 * @post If the contains call is for the fast subclass, returns true when the point is contained by the bounding box and false otherwise.
	 * 		 If the contains call is for the precise subclass, returns true when the point is contained by the polygon and false otherwise.
	 * 		 (polygon.BoxContains(point) ? true:false) || (polygon.contains(point) ? true:false)
	 */
	public boolean contains(RoundedPolygon polygon, IntPoint point);
	
}
