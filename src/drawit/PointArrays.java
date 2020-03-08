package drawit;

import java.util.stream.IntStream;

/**
 * Creates a list of IntPoint objects and checks the validaty of the polygon they create
 */

public class PointArrays { 
		
	//| for each int in 0..points.length
	 //* 		| 	points[i].isOnLineSegment(points[i-1],points[i+1]) ||
	 //* 		| 	points[j].equals(points[i]) ||
	 //* 		|	IntPoint.linSegmentsIntersect(points[i-3],points[i-2],points[i-1],points[i]) &&
	
	/**
	 * Returns the reason why the given PointArray is not a proper polygon or returns null
	 * @post returns null when none of the conditions of failure are met.
	 * 		| (points.length < 3   ||
	 * 		| IntStream.range(1, points.length-1).anyMatch(i -> points[i].isOnLineSegment(points[i-1],points[i+1]) == true) ||
	 * 		| points[0].isOnLineSegment(points[points.length-1], points[1])==true ||
	 * 		| points[points.length-1].isOnLineSegment(points[points.length-2], points[0])==true ||
	 * 		| IntStream.range(0, points.length-1).anyMatch(i -> points[i].equals(points[i+1]) == true) ||
	 * 		| points[points.length-1].equals(points[0]) == true ||
	 * 		| IntPoint.lineSegmentsIntersect(points[points.length-3],points[points.length-2],points[points.length-1],points[0])
	 * 		| == true ||
	 * 		| IntPoint.lineSegmentsIntersect(points[points.length-2],points[points.length-1],points[0],points[1]) == true ||
	 * 		| IntPoint.lineSegmentsIntersect(points[points.length-1],points[0],points[1],points[2]) == true ||
	 * 		| IntStream.range(3, points.length).anyMatch(i -> 
	 * 		| IntPoint.lineSegmentsIntersect(points[i-3],points[i-2],points[i-1],points[i]) == true)) &&
	 * 		| result != null ||
	 * 		| result == null
	 */
	public static String checkDefinesProperPolygon(IntPoint[] points) {
		String result = null;
		if (points.length < 3) {
			result = "The given array does not contain enough points to create a polygon!";
			return result;
		}
		
		if (points[0].isOnLineSegment(points[points.length-1], points[1])==true) {
			result = "The array contains one or more points that lie on the edge of the polygon.";
			return result;
		}
		
		if (points[points.length-1].isOnLineSegment(points[points.length-2], points[0])==true) {
			result = "The array contains one or more points that lie on the edge of the polygon.";
			return result;
		}
		for (int i=1; i < points.length-1; i++) {
			if (points[i].isOnLineSegment(points[i-1],points[i+1])== true) {
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
		
			if (IntPoint.lineSegmentsIntersect(points[points.length-3],points[points.length-2],points[points.length-1],points[0]) == true) {
				result = "A proper polygon cannot contain intersecting edges.";
				return result;
			}
			else if (IntPoint.lineSegmentsIntersect(points[points.length-2],points[points.length-1],points[0],points[1]) == true) {
				result = "A proper polygon cannot contain intersecting edges.";
				return result;
			}
			else if (IntPoint.lineSegmentsIntersect(points[points.length-1],points[0],points[1],points[2]) == true) {
				result = "A proper polygon cannot contain intersecting edges.";
				return result;
			}
			for (int i = 3; i < points.length; i++) {
				if (IntPoint.lineSegmentsIntersect(points[i-3],points[i-2],points[i-1],points[i]) == true) {
					result = "A proper polygon cannot contain intersecting edges.";
					return result;
				}
			}
		}
		
		return result;
	
	}
	
	/**
	 * Returns a new array with the same contents as the given array.
	 * @post The length of the resulting array is equal to that of the given array.
	 * 		| result.length == points.length
	 * @post Returns a new array with the same contents as the given array.
	 * 		| IntStream.range(0, points.length).allMatch(i -> result[i].getX() == points[i].getX() && 
	 * 		| result[i].getY() == points[i].getY())
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
	 * @post The length of the resulting array is one greater than the given array.
	 * 		| result.length == points.length+1
	 * @post the given IntPoint object has been inserted into the given IntPoint array at the given index
	 * 		| IntStream.range(0, index).allMatch(i -> result[i].getX() == points[i].getX() && 
	 * 		| result[i].getY() == points[i].getY()) &&
	 * 		| IntStream.range(index, points.length).allMatch(i -> result[i+1].getX() == points[i].getX() 
	 * 		| && result[i+1].getY() == points[i].getY()) &&
	 * 		| result[index] == point
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
	 * @post The length of the resulting array is one smaller than the given array.
	 * 		| result.length == points.length-1
	 * @post  the given IntPoint object has been removed from the given IntPoint array at the given index
	 * 		| IntStream.range(0, index).allMatch(i -> result[i].getX() == points[i].getX() && 
	 * 		| result[i].getY() == points[i].getY()) &&
	 * 		| IntStream.range(index, result.length).allMatch(i -> result[i].getX() == points[i+1].getX() && 
	 * 		| result[i].getY() == points[i+1].getY())
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
	  * @post The length of the resulting array is equal to the given array.
	 * 		| result.length == points.length
	  * @post the given IntPoint object has been replaces the old IntPoint object at the given index of the IntPoint array
	  * 		| IntStream.range(0, index).allMatch(i -> result[i].getX() == points[i].getX() && 
	  * 		| result[i].getY() == points[i].getY()) &&
	  * 		| IntStream.range(index+1, points.length).allMatch(i -> result[i].getX() == points[i].getX() && 
	  * 		| result[i].getY() == points[i].getY()) &&
	  * 		| result[index].getX() != points[index].getX() && result[index].getY() != points[index].getY() &&
	  * 		| result[index] == value
	  */
	public static IntPoint[] update(IntPoint[] points, int index, IntPoint value) {
		IntPoint[] update = PointArrays.copy(points);
		update[index] = value;
		return update;
	}
}
