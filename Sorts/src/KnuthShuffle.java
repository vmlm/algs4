import edu.princeton.cs.algs4.StdRandom;

public class KnuthShuffle extends SortAlgorithm {
   
    public static <T> void shuffle(T[] a) {
        int n = a.length, k;
        for (int i = 1; i < n; i++) {
            k = StdRandom.uniform(i+1);
            exch(a, i, k);  
        }
    }

}
