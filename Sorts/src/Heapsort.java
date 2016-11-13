import java.util.Comparator;

public class Heapsort extends SortAlgorithm {
    
    //Array is assumed to start at position 1, NOT 0.
    public static <T> void sort(T[] a, Comparator<T> comparator) {
        int n = a.length;
        for (int k = n/2; k > 0; k--) 
            sink(a, k, n, comparator);
        while (n > 1) {
            exch(a, 1, --n);
            sink(a, 1, n, comparator);
        }
    }
    
    @SuppressWarnings("rawtypes")
    private static void sink(Object[] a, int k, int n, 
            Comparator comparator) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(a, k, j);
            k = j;
        }
    }
}
