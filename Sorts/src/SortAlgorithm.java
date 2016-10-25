import java.util.Comparator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public abstract class SortAlgorithm {
    
    protected static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    protected static boolean less(Object v, Object w, Comparator comparator) {
        return comparator.compare(v, w) < 0;
    }
    
    protected static <T> void exch(T[] a, int v, int w) {
        T swap = a[v];
        a[v] = a[w];
        a[w] = swap;
    }
    
    public static void main(String[] args) {
        int n = 100;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform(n);
        }
        
        Comparator<Integer> comparator = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        
        Stopwatch s = new Stopwatch();
        Mergesort.sort(a, comparator);
        double time = s.elapsedTime();
        for (int i = 0; i < a.length; i++) StdOut.println(a[i]);
        StdOut.println(time);
    }
}
