import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {
    
    private SET<Point2D> s;
    
    /**
     * construct an empty set of points 
     */
    public KdTree() {
        s = new SET<Point2D>();
    }
    
    /**
     * is the set empty?
     * @return true if empty
     */
    public boolean isEmpty() {
        return s.isEmpty();
    }
    
    /**
     * number of points in the set
     * @return number of points in the set.
     */
    public int size() {
        return s.size();
    }
    
    public void insert(Point2D p) {
        s.add(p);
    }
    
    /**
     * does the set contain point p?
     * @param p
     * @return true if set contains p.
     */
    public boolean contains(Point2D p) {
        return s.contains(p);
    }
    
    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : s)
            p.draw();
    }
    
    /**
     * all points that are inside the rectangle
     * @param rect
     * @return iterable set of points that fall inside the query rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        Queue<Point2D> q = new Queue<Point2D>();
        for (Point2D p : s) 
            if (rect.contains(p)) q.enqueue(p);
        return q;
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        Point2D nearest = null;
        for (Point2D q : s) 
            if (nearest == null || 
                    Double.compare(p.distanceTo(q), p.distanceTo(nearest)) < 0) 
                nearest = q;
        return nearest;
    }

    /**
     * unit testing of the methods (optional)
     * @param args
     */
    public static void main(String[] args) {
        
    }

}
