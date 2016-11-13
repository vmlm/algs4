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
    
    @SuppressWarnings("rawtypes")
    protected static void insertionSort(Object[] a, int lo, int hi, 
            Comparator comparator) {
        for (int i = lo; i < hi; i++)
            for (int j = i + 1; j > lo && less(a[j], a[j-1], comparator); j--)
                exch(a, i, j);
    }
    
    @SuppressWarnings("rawtypes")
    protected static boolean isSorted(Object[] a, int lo, int hi, 
            Comparator comparator) {
        if (lo == hi) return true;
        for (int k = lo+1; k <= hi; k++)
            if (less(a[k], a[k-1], comparator)) return false;
        return true;
    }
    
    public static void main(String[] args) {
        int n = 10000;
        
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform(n);
        }
        
        Comparator<Integer> comparator = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        
        Stopwatch s = new Stopwatch();
        Mergesort.sort(a, comparator);
        double time = s.elapsedTime();
        for (int i = 0; i < a.length; i++) StdOut.println(a[i]);
        StdOut.println(time);
    }
}
