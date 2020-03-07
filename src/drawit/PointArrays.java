package drawit;

import java.util.Arrays;

/**
 * Creates a list of IntPoint objects and checks the validaty of the polygon they create
 */

public class PointArrays { 
	///**
	 //* Returns the reason why the given PointArray is not a proper polygon or returns null
	 //* @post returns null when none of the conditions of failure are met.
	 //* 		| points.length < 3   ||
	 //* 		| for each i in 0..points.length:
	 //* 		|	points[i].isOnLineSegment(points[i-1],points[i+1])
	 //* 		| 	points[i].isOnLineSegment(points[i-1],points[i+1]) ||
	 //* 		| 	points[j].equals(points[i]) ||
	 //* 		|	IntPoint.linSegmentsIntersect(points[i-3],points[i-2],points[i-1],points[i]) &&
	 //* 		| result != null
	 //*/
	public static String checkDefinesProperPolygon(IntPoint[] points) {
		String result = null;
		if (points.length < 3) {
			result = "The given array does not contain enough points to create a polygon!";
			return result;
		}
		
		for (int i=0; i < points.length; i++) {
			
			if (i == 0 && points[i].isOnLineSegment(points[points.length-1], points[i+1])==true) {
				result = "The array contains one or more points that lie on the edge of the polygon.";
				return result;
			}
			
			else if (i == points.length-1 && points[i].isOnLineSegment(points[i-1], points[0])==true) {
				result = "The array contains one or more points that lie on the edge of the polygon.";
				return result;
			}
			
			else if (i != 0 && i != points.length-1 && points[i].isOnLineSegment(points[i-1],points[i+1])== true) {
				result = "The array contains one or more points that lie on the edge of the polygon.";
				return result;
			}
			
		}
		
		for (int j = 0; j < points.length-1; j++) {
			for (int i = j+1; i < points.length; i++)	{
				if (points[j].equals(points[i])==true) {
					result = "The array contains one or more points that coincide.";
				}
			}
		}
		
		
		if (points.length > 3) {
		
			for (int i = 0; i < points.length; i++) {
				if (i == 0 && IntPoint.lineSegmentsIntersect(points[points.length-3],points[points.length-2],points[points.length-1],points[i]) == true) {
					result = "A proper polygon cannot contain intersecting edges.";
					return result;
				}
				else if (i == 1 && IntPoint.lineSegmentsIntersect(points[points.length-2],points[points.length-1],points[i-1],points[i]) == true) {
					result = "A proper polygon cannot contain intersecting edges.";
					return result;
				}
				else if (i == 2 && IntPoint.lineSegmentsIntersect(points[points.length-1],points[i-2],points[i-1],points[i]) == true) {
					result = "A proper polygon cannot contain intersecting edges.";
					return result;
				}
				else if (i > 2 && IntPoint.lineSegmentsIntersect(points[i-3],points[i-2],points[i-1],points[i]) == true) {
					result = "A proper polygon cannot contain intersecting edges.";
					return result;
				}
			}
		}
		
		return result;
	
	}
	
	/**
	 * Returns a new array with the same contents as the given array.
	 * @post Returns a new array with the same contents as the given array.
	 */
	public static IntPoint[] copy(IntPoint[] points) {
		IntPoint[] copy= new IntPoint[points.length];
		int i = 0;
		for (IntPoint element: points) {
			copy[i]=element;
			i++;
		}
		return copy;
	}

	/**
	 * Returns a new PointArray with the given IntPoint inserted into the given IntPoint array at the given index
	 * @invar | index != null
	 * @pre the index cannot go out of bounds of the IntPoint[] object
	 * 		| index >= 0 && index <= points.length
	 * @post the given IntPoint object has been inserted into the given IntPoint array at the given index
	 */
	public static IntPoint[] insert(IntPoint[] points, int index, IntPoint point) {
		IntPoint[] insert = new IntPoint[points.length+1];
		
		for (int i = points.length; i > index; i--) {
			insert[i] = points[i-1];
		}
		insert[index] = point;
		for (int i = 0; i < index; i++) {
			insert[i] = points[i];
		}
		return insert;
		
	}
	
	/**
	 * Returns a new PointArray with the given IntPoint removed from the given IntPoint array at the given index
	 * @invar | index != null
	 * @pre the index cannot go out of bounds of the IntPoint[] object
	 * 		| index >= 0 && index < points.length
	 * @post  the given IntPoint object has been removed from the given IntPoint array at the given index
	 */
	public static IntPoint[] remove(IntPoint[] points, int index) {
		IntPoint[] remove = new IntPoint[points.length-1];
		
		for (int i = 0; i < remove.length; i++) {
			if (i < index)
				remove[i] = points[i];
			else
				remove[i] = points[i+1];
		}
		return remove;
		
	}
	 /**
	  * Returns a new PointArray where the given IntPoint replaces the old IntPoint at the given index of the IntPoint array
	  * @invar | index != null
	  * @pre the index cannot go out of bounds of the given IntPoint[] object
	  * 	| index >= 0 && index < points.length
	  * @post the given IntPoint object has been replaces the old IntPoint object at the given index of the IntPoint array
	  */
	public static IntPoint[] update(IntPoint[] points, int index, IntPoint value) {
		IntPoint[] update = PointArrays.copy(points);
		update[index] = value;
		return update;
	}
}
