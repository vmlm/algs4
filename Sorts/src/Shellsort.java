
public class Shellsort extends SortAlgorithm {
    
    public static <T extends Comparable<T>> void sort(T[] a) {
        int h = 1;
        int n = a.length;
        
        while (h < n/3) h = 3*h + 1;
        
        //array is h sorted
        while (h > 0) {
            //everything to the left of i is  h-sorted
            for (int i = h; i < n; i++)
                //every h value to the left of j is h-shorted
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                    exch(a, j, j-h);
            h /= 3;
        }
    }
}
