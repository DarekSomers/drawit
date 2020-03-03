package drawit;

public class PointArrays { 
	
	public static String checkDefinesProperPolygon(IntPoint[] points) {
		String result = null;
		if (points.length < 3) {
			result = "The given array does not contain enough points to create a polygon!";
			return result;
		}
		
		for (int i=0; i <= points.length; i++) {
			if (points[i].isOnLineSegment(points[i-1],points[i+1])== true) {
				
				result = "The array contains one or more points that lie on the edge of the polygon.";
				return result;
			}
			if (points[points.length-1].isOnLineSegment(points[points.length-2], points[0])==true) {
				result = "The array contains one or more points that lie on the edge of the polygon.";
				return result;
			}
		}
		
		for (int i = 0; i < points.length; i++)	{
			if (points[i].equals(points[i-1])==true) {
				result = "The array contains one or more points that coincide.";
			}
		}
		
		if (points.length > 3) {
		
			for (int i = 3; i < points.length; i++) {
				if (IntPoint.lineSegmentsIntersect(points[i-3],points[i-2],points[i-1],points[i]) == true) {
					result = "A proper polygon cannot contain intersecting edges.";
					return result;
				}
			}
		}
		
		return result;
	
	}
	
	
	public static IntPoint[] copy(IntPoint[] points) {
		IntPoint[] copy = new IntPoint[points.length];
		int i = 0;
		for (IntPoint element: points) {
			copy[i]=element;
			i++;
		}
		return copy;
	}

	public static IntPoint[] insert(IntPoint[] points, int index, IntPoint point) {
		IntPoint[] insert = new IntPoint[points.length];
		
		for (int i = insert.length-1; i > index; i--) {
			insert[i] = insert[i-1];
		}
		insert[index] = point;
		return insert;
		
	}
	
	public static IntPoint[] remove(IntPoint[] points, int index) {
		IntPoint[] remove = new IntPoint[points.length];
		
		for (int i = index; i<remove.length -1; i++) {
			remove[i] = remove[i+1];
		}
		return remove;
		
	}
	public static IntPoint[] update(IntPoint[] points, int index, IntPoint value) {
		IntPoint[] update = PointArrays.copy(points);
		update[index] = value;
		return update;
		
		
		
	}
}
