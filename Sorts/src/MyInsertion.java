import java.util.Comparator;

public class MyInsertion extends SortAlgorithm {
    
    public static <T> void sort(T[] a, int lo, int hi, Comparator<T> comparator) {
        for (int i = lo+1; i < hi; i++) {
            for (int j = i; j > lo && (less(a[j], a[j-1], comparator)); j--) {
                exch(a, j, j-1);
            }
        }
    }
}
