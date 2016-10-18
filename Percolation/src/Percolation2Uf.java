import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/** Percolation model
 * @author VMLM
 *
 * * Percolation is defined as the top of a lattice being connected to the bottom.
 * 
 * We are also interested in knowing which sites of the lattice are currently filled.
 * 
 * The lattice is represented by a flat byte array of size n*n (total memory usage: 
 * tends to n*n bytes for larger lattices), navigated by mapping i and j to a one 
 * dimensional index. Each site uses two bits to store information: The first 
 * indicates whether the site is open and the other indicates whether it's filled.
 * 
 * We use a union-find tree represented by a flat int array of size n*n+2 (total 
 * memory usage: 4*n*n bytes), where 0 represents the source of the lattice and 
 * n+1 represents the bottom. Accordingly, percolation occurs when connected(0,n+1) 
 * evaluates to true.
 * 
 * We define "filled" as "connected to the source," however, attempting to use the 
 * union-find tree to determine the filled state of each site has the drawback of 
 * causing "backwash." This means that sites that are only connected to the source 
 * through the bottom of the lattice are also filled. To avoid this, we must use a 
 * second algorithm to evaluate whether a site is filled or not.
 *      Options:
 *      ->  A second uf object that is connected to the source but not the bottom. 
 *          Since there is no bottom element, the problem is solved. Here the 
 *          drawback is the duplication of the necessary memory and of the number 
 *          of operations.
 *          
 *      ->  Storing and updating two bits of information to indicate when a 
 *          site is connected to the source (element 0 of the uf data structure) 
 *          and the basin (element n+1) instead of one to indicate that a site is 
 *          "full". To do this, we would check the root of each opened site, if 
 *          the root = 0, then we change the first bit to 1. Furthermore, if the 
 *          site is in the last row of the lattice (i = n), then we change the 
 *          second bit to 1.
 *          
 *      ->  Defining a function "fill" which changes the "filled" byte to 1 for
 *          the last opened site if it's adjacent to a filled site or to the 
 *          source (0) and then doing the same for all sites connected to it. 
 *      
 *      We will implement all three options and compare running times. 
 *          
 * The operations defined are:
 *      -> open(site): Opens the site, if closed, and calls fill for that site.
 *      -> fill(site): if the site is open and empty, fills it and and the open 
 *         sites around it.
 *      -> isOpen(site): checks if a site is open.
 *      -> isFilled(site): checks if a site is filled.
*/
public class Percolation2Uf {
    private byte[] lattice;
    private int n;
    private int basin;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF filledSites;

    /**Percolation constructor.
     * Sets up the lattice and the union find data structure. 
     * Throws an exception if n < 1.
     * @param n: size of grid
     */
    public Percolation2Uf(int n) {
        checkLatticeSize(n);
        this.n = n;
        this.basin = n*n+1;
        this.lattice = new byte[(n*n)];
        this.uf = new WeightedQuickUnionUF(n*n+2);
        this.filledSites = new WeightedQuickUnionUF(n*n+1);
    }    
    
    /**Checks if site (i,j) of the lattice is open (Represented by the first byte).
     * Throws an exception if either index is less than 1 or greater than n. 
     * @param i: Vertical index
     * @param j: Horizontal index
     * @return: true if open, false otherwise.
    */
    public boolean isOpen(int i, int j) {
        checkLatticeBounds(i, j);
        return (this.lattice[getIndex(i, j)] & 1) == 1;
    }   
    
    /**Checks if site (i,j) of the lattice is full (Represented by the second byte). 
     * Throws an exception if either index is less than 1 or greater than n.
     * @param i: Vertical index.
     * @param j: Horizontal index.
     * @return: true if full, false otherwise.
    */
    public boolean isFull(int i, int j) {
        checkLatticeBounds(i, j);
        return this.filledSites.connected(0, getIndex(i, j)+1);
    }
    
    /**Opens site (i,j), if it isn't open already. Then, check if the site is 
     * adjacent to a filled site. If so, runs fill(i, j).
     * Throws an exception if either index is less than 1 or greater than n.
     * @param i: Vertical index.
     * @param j: Horizontal index.
    */
    public void open(int i, int j) {
        checkLatticeBounds(i, j);
        if (!isOpen(i, j)) {
            int index = getIndex(i, j);
            
            this.lattice[index] = 1;
            
            if (i == 1) {
                uf.union(index+1, 0);
                filledSites.union(index+1, 0);
            }

            if (i == this.n) {
                uf.union(index+1, this.basin);
            }

            // check above
            if ((i > 1) && isOpen(i-1, j)) {
                uf.union(index+1, getIndex(i-1, j)+1);
                filledSites.union(index+1, getIndex(i-1, j)+1);
            }
            
            // check below
            if ((i < this.n) && isOpen(i+1, j)) {
                uf.union(index+1, getIndex(i+1, j)+1);
                filledSites.union(index+1, getIndex(i+1, j)+1);
            }

            // check left
            if ((j > 1) && isOpen(i, j-1)) {
                uf.union(index+1, getIndex(i, j-1)+1);
                filledSites.union(index+1, getIndex(i, j-1)+1);
            }

            // check right
            if ((j < this.n) && isOpen(i, j+1)) {
                uf.union(index+1, getIndex(i, j+1)+1);
                filledSites.union(index+1, getIndex(i, j+1)+1);
            }
        }
    }
    
 
    /**Checks if the source (lattice[0] is connected to the basin (lattice[n+1]).
     * @return: true if percolates.
     */
    public boolean percolates() {
        // does the system percolate?
        return uf.connected(0, this.basin);
    }

    private void checkLatticeSize(int size) {
        if (size < 1) {
            throw new java.lang.IllegalArgumentException("N must be at least 1.");
        }
    }

    private void checkLatticeBounds(int i, int j) {
        if (!checkBounds(i) || !checkBounds(j)) {
            throw new java.lang.IndexOutOfBoundsException("indeces i and j "
                    + "must be between 1 and n.");
        }
    }

    private boolean checkBounds(int i) {
        return ((i > this.n) || (1 > i)) ? false : true;
    }

    private int getIndex(int i, int j) {
        return (i-1)*this.n+(j-1);
    }

    private void printGrid() {
        for (int i = 0; i < this.n*n; i++) {
            StdOut.print(this.lattice[i]);
            if (i != 0 && (i+1) % n == 0) StdOut.println(); 
            else StdOut.print(" ");
        }
    }

    public static void main(String[] args) {
        // test client
        int i, j;
        int n = StdIn.readInt();
        Percolation2Uf p = new Percolation2Uf(n);
        p.printGrid();
        while (!StdIn.isEmpty()) {
            i = StdIn.readInt();
            j = StdIn.readInt();
            if (!p.isOpen(i, j)) p.open(i, j);
            else StdOut.println("(" + i + "," + j + ") already open!");
            p.printGrid();
            if (p.percolates()) StdOut.println("Percolates!");
            else StdOut.println("Doesn't percolate!");
        }
    }
}
