import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    
    private int numsegments;           // the number of line segments
    private Node first;
    
    public BruteCollinearPoints(Point[] input) {
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
        
        Arrays.sort(points, 0, numpoints);
        
        for (int p = 0; p < numpoints-3; p++) {
            for (int q = p + 1; q < numpoints; q++) {
                for (int r = q + 1; r < numpoints; r++) {
                    for (int s = r + 1; s < numpoints; s++) {                        
                       if (compare4Points(points[p], points[q], 
                               points[r], points[s]))
                           addLineSegment(new LineSegment(points[p], points[s]));
                    }
                }
            }
        }
    }
    
    public int numberOfSegments() {
        return numsegments;
    }
    
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
    
    private class Node {
        private LineSegment item;
        private Node next;
    }
    
    private void addLineSegment(LineSegment s) {
        Node newnode = new Node();
        newnode.item = s;
        newnode.next = first;
        first = newnode;
        numsegments++;
    }
    
    private boolean compare4Points(Point p, Point q, Point r, Point s) {
        Comparator<Point> c = p.slopeOrder();
        if (c.compare(q, r) == 0 && c.compare(q, s) == 0) return true;
        return false; 
    }
    
    //testing methods
    
    
    public static void main(String[] args) {
     // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.005);
        StdDraw.show();
        
     // read in the input
        String filename;
        if (args.length == 0)
            filename = "D:\\Users\\VMLM\\projects\\algs4"
                    + "\\PatternRecognition\\bin\\grid5x5.txt";
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

        // display to screen all at once
        StdDraw.show();
        
        BruteCollinearPoints b = new BruteCollinearPoints(input);
        
        LineSegment[] segments = b.segments();
        
        for (int i = 0; i < segments.length; i++) 
            segments[i].draw();
        
        StdDraw.show();
    }
}
