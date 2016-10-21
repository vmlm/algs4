
public abstract class SortAlgorithm {
    
    protected static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    protected static void exch(Comparable[] a, int v, int w) {
        Comparable swap = a[v];
        a[v] = a[w];
        a[w] = swap;
    } 
}
