import edu.princeton.cs.algs4.StdRandom;

public class KnuthShuffle extends SortAlgorithm {
   
    public static <T> void shuffle(T[] a) {
        int n = a.length, k;
        for (int i = 0; i < n; i++) {
            k = StdRandom.uniform(i);
            exch(a, i, k);  
        }
    }

}
