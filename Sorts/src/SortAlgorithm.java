
public abstract class SortAlgorithm {
    
    protected static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    protected static <T> void exch(T[] a, int v, int w) {
        T swap = a[v];
        a[v] = a[w];
        a[w] = swap;
    } 
}
