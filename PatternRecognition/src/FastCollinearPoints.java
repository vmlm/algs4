import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private int numsegments;
    private Node first;
    
    /**
     * Finds all line segments containing 4 or more points
     * @param points
     */
    public FastCollinearPoints(Point[] input) {
        if (input == null) 
            throw new NullPointerException();
        
        int numpoints = input.length;
        Point[] points = new Point[numpoints];
        numsegments = 0;
        first = null;
        
        for (int i = 0; i < numpoints; i++)
            if (input[i] == null) 
                throw new NullPointerException("argument is null");
            else {
                points[i] = input[i];
                for (int j = i-1; j >= 0; j--)
                    if (points[i].compareTo(points[j]) == 0)
                        throw new IllegalArgumentException();
            }
        
        if (numpoints > 3)
            for (int p = 0; p < numpoints; p++) {
              Point referencepoint = input[p];
              Arrays.sort(points, 0, numpoints, referencepoint.slopeOrder());
              
              double nextslope, currentslope;
              int numreps = 1;
              boolean islowest = true;
              Point endpoint = null;
              currentslope = referencepoint.slopeTo(points[1]);
              for (int q = 1; q < numpoints; q++) {
                  if (q+1 == numpoints)
                      nextslope = Double.NEGATIVE_INFINITY;
                  else
                      nextslope = referencepoint.slopeTo(points[q+1]);
                  
                  if (Double.compare(currentslope, nextslope) != 0) {
                      if (points[q].compareTo(referencepoint) < 0)
                          islowest = false;
                      
                      if (numreps > 2 && islowest) {
                          if (endpoint == null || endpoint.compareTo(points[q]) < 0)
                              endpoint = points[q];
                          addLineSegment(new LineSegment(referencepoint, endpoint));
                      }
                      numreps = 1;
                      islowest = true;
                      endpoint = null;
                  } else if (islowest) {
                      numreps++;
                      if (points[q].compareTo(referencepoint) < 0)
                          islowest = false;
                      if (endpoint == null || endpoint.compareTo(points[q]) < 0)
                          endpoint = points[q];
                  }
                  currentslope = nextslope;
              }
           }
    }
    
    private class Node {
        private LineSegment item;
        private Node next;
    }
    
    private void addLineSegment(LineSegment s) {
        Node newitem = new Node();
        newitem.item = s;
        newitem.next = first;
        first = newitem;
        numsegments++;
    }
    
    /**
     * The number of line segments
     * @return
     */
    public int numberOfSegments() {
        return numsegments;
    }
    
    /**
     * The line segments
     * @return
     */
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[numsegments];
        Node aux = first;
        int i = 0;
        while (aux != null) {
            segments[i++] = aux.item;
            aux = aux.next;
        }        
        return segments;
    }
    
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
           StdDraw.setXscale(0, 32768);
           StdDraw.setYscale(0, 32768);
           StdDraw.setPenRadius(0.005);
           
        // read in the input
           String filename;
           if (args.length == 0)
               filename = "D:\\Users\\VMLM\\projects\\algs4"
                       + "\\PatternRecognition\\bin\\collinear\\grid5x5.txt";
           else
               filename = args[0];
           
           In in = new In(filename);
           int N = in.readInt();
           Point[] input = new Point[N];
           
           for (int i = 0; i < N; i++) {
               int x = in.readInt();
               int y = in.readInt();
               Point p = new Point(x, y);
               p.draw();
               input[i] = p;
           }
           StdDraw.show();
           
           FastCollinearPoints b = new FastCollinearPoints(input);
           
           LineSegment[] segments = b.segments();
           
           for (int i = 0; i < segments.length; i++) 
               segments[i].draw();
           
           StdDraw.show();
       }
}
