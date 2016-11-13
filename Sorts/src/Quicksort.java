import edu.princeton.cs.algs4.StdRandom;
import java.util.Comparator;

public class Quicksort extends SortAlgorithm {
    private static final int INSERTION_SORT_CUTOFF = 10;
    public static <T> void sort(T[] a, Comparator<T> comparator) {
        int hi = a.length-1;
        StdRandom.shuffle(a);
        sort(a, 0, hi, comparator);
        assert isSorted(a, 0, hi, comparator);
    }
    
    private static void sort(Object[] a, int lo, int hi, Comparator c) {
        if (hi <= lo + INSERTION_SORT_CUTOFF) {
            insertionSort(a, lo, hi, c);
            return;
        }
        int lt = lo, gt = hi+1, cmp;
        Object v = a[lo];
        int i = lo+1;
        while (i < gt) {
            cmp = c.compare(a[i], v);
            if (cmp < 0)
               exch(a, i++, lt++);
            else if (cmp > 0)
                exch(a, i, --gt);
            else i++;
        }
        sort(a, lo, lt-1, c);
        sort(a, gt, hi, c);
        assert isSorted(a, lo, hi, c);
    }
}
