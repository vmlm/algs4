import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**Performs trials independent experiments on an n-by-n grid*/
public class PercolationStats {
    
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    
    private class IndexSampler {
        private int[] indexTree;
        private int unsampled;
        
        IndexSampler(int n) {
            this.unsampled = n;
            this.indexTree = new int[n];
            for (int i = 0; i < n; i++) {
                this.indexTree[i] = i;
            }
        }
        
        public int sample() {
            int k = StdRandom.uniform(this.unsampled);
            int value = this.indexTree[k];
            for (int i = k; i < this.unsampled - 1; i++) 
                this.indexTree[i] = this.indexTree[i+1];
            this.unsampled--;
            return value;
        }
    }
    /**Performs trials independent experiments on an n-by-n grid
     * @param n: Dimension of the grid is n*n. n > 0.
     * @param trials: Number of trials to be performed.
    */
    public PercolationStats(int n, int trials) {
        checkArgument(n);
        checkArgument(trials);

        int i, j, index, sites;
        int totalSites = n*n;
        double [] thresholds = new double [trials]; 
        double confidence;
        this.mean = 0;
        this.stddev = 0;
        this.confidenceLo = 0;
        this.confidenceHi = 0;
        
        Percolation p;
        IndexSampler sampler;
        for (int trial = 0; trial < trials; trial++) {
            p = new Percolation(n);
            sampler = new IndexSampler(totalSites);
            sites = 0;
            do {
                index = sampler.sample();
                i = index / n + 1;
                j = index % n + 1;
                p.open(i, j);
                sites++;
            } while (!p.percolates());
            thresholds[trial] = (double) (sites)/totalSites;
            this.mean += thresholds[trial];
        }
        this.mean = this.mean / trials;
        this.stddev = StdStats.stddev(thresholds);
        confidence = (1.96 * this.stddev)/Math.sqrt(trials);
        this.confidenceLo = this.mean - confidence;
        this.confidenceHi = this.mean + confidence;
    }
    
    public double mean() {
        // sample mean of percolation threshold
        return this.mean;
    }
      
    public double stddev() { 
        // sample standard deviation of percolation threshold
        return this.stddev;
    }
    
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return this.confidenceLo;
    }
  
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return this.confidenceHi;
    }
    
    private void checkArgument(int n) {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException("n and trials must be > 0");
        }
    }

    public static void main(String[] args) {
        // test client
        int n = 100;
        int trials = 1000;
        double start, stop;
        
        Stopwatch stopwatch = new Stopwatch();
        
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        
        start = stopwatch.elapsedTime();
        PercolationStats pStats = new PercolationStats(n, trials);
        stop = stopwatch.elapsedTime();
        
        StdOut.println("mean                    = " + pStats.mean);
        StdOut.println("stddev                  = " + pStats.stddev);
        StdOut.println("95% confidence interval = " + 
                pStats.confidenceLo + ", " + pStats.confidenceHi);
        
        StdOut.println("elapsed time            = " + (stop - start));
    }

}
