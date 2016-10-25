import java.util.Comparator;

public class Mergesort extends SortAlgorithm {
    private static final int CUTOFF = 8;
    public static <T> void sort(T[] a, Comparator<T> comparator) {
        int hi = a.length;
        Object[] aux = new Object[hi];
//        for (int k = 0; k < hi; k++) aux[k] = a[k];
        mergesort(a, aux, 0, hi-1, comparator);
    }
    
    private static void mergesort(Object[] a, Object[] aux, int lo, int hi, 
            Comparator comparator) {
        if (hi <= lo + CUTOFF - 1) {
            MyInsertion.sort(a, lo, hi+1, comparator);
            return;
        }
        int mid = lo + (hi-lo)/2;
        mergesort(a, aux, lo, mid, comparator);
        mergesort(a, aux, mid+1, hi, comparator);
        if (!less(a[mid+1], a[mid], comparator)) return;
        merge(a, aux, lo, mid, hi, comparator);
    }
    
    private static void merge(Object[] a, Object[] aux, 
            int lo, int mid, int hi, Comparator comparator) {
        assert isSorted(a, lo, mid, comparator);
        assert isSorted(a, mid+1, hi, comparator);
        
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) aux[k] = a[k];
        for (int k = lo; k <= hi; k++) {
           if      (i > mid)              a[k] = aux[j++];
           else if (j > hi)               a[k] = aux[i++];
           else if (less(aux[j], aux[i], comparator)) a[k] = aux[j++];
           else                           a[k] = aux[i++];
        }
        assert isSorted(a, lo, hi, comparator);
    }
    
    private static boolean isSorted(Object[] a, int lo, int hi, 
            Comparator comparator) {
        if (lo == hi) return true;
        for (int k = lo+1; k <= hi; k++)
            if (less(a[k], a[k-1], comparator)) return false;
        return true;
    }
}
