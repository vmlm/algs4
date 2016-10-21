
public class Insertion extends SortAlgorithm {
    
    public static <T extends Comparable<T>> void sort(T[] a) {
        int length = a.length;
        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0 && (less(a[j], a[j-1])); j--) {
                exch(a, j, j-1);
            }
        }
    }
}
