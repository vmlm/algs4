import edu.princeton.cs.algs4.StdOut;

public class Selection extends SortAlgorithm {
    
    public static <T extends Comparable<T>> void sort(T[] a) {
        int length = a.length, min;
        for (int i = 0; i < length; i++) {
            min = i;
            for (int j = i+1; j < length; j++) if (less(a[j], a[i])) min = j;
            exch(a, i, min);
        }
    }
    
    public static void main(String[] args) {
        String[] a = new String[2];
        a[0] = "Hello";
        a[1] = "World";
        sort(a);
        for (int i = 0; i < a.length; i++) StdOut.println(a[i]);
    }
}
