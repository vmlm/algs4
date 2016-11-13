import java.util.Comparator;

public class Mergesort extends SortAlgorithm {
    private static final int CUTOFF = 7;
    public static <T> void sort(T[] a, Comparator<T> comparator) {
        int hi = a.length-1;
        Object[] aux = a.clone();
        sort(aux, a, 0, hi, comparator);
        assert isSorted(a, 0, hi, comparator);
    }
    
    private static void sort(Object[] src, Object[] dst, int lo, int hi, 
            Comparator comparator) {
        
        if (hi <= lo + CUTOFF) {
            insertionSort(dst, lo, hi, comparator);
            return;
        }
        int mid = lo + (hi-lo)/2;
        sort(dst, src, lo, mid, comparator);
        sort(dst, src, mid+1, hi, comparator);
        
        if (!less(src[mid+1], src[mid], comparator)) {
            System.arraycopy(src, lo, dst, lo, hi-lo+1);
            return;
        }
        
        merge(src, dst, lo, mid, hi, comparator);
    }
    
    private static void merge(Object[] src, Object[] dst, 
            int lo, int mid, int hi, Comparator comparator) {
        assert isSorted(src, lo, mid, comparator);
        assert isSorted(src, mid+1, hi, comparator);
        
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
           if      (i > mid)                          dst[k] = src[j++];
           else if (j > hi)                           dst[k] = src[i++];
           else if (less(src[j], src[i], comparator)) dst[k] = src[j++];
           else                                       dst[k] = src[i++];
        }
        assert isSorted(dst, lo, hi, comparator);
    }
}
