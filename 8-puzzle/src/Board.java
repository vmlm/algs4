import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int n;
    private int[][] blocks;
    private int key;
    private int zeroposition;
    
    /**
     * construct a board from an n-by-n array of blocks
     * @param blocks: The initial board.
     */
    public Board(int[][] blocks) {
        n = blocks.length;
        key = -1; 
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (this.blocks[i][j] == 0)
                    zeroposition = i*n+j;
            }
    }
    
    /**
     * board dimension n
     * @return n
     */
    public int dimension() {
        return n;
    }
    
    /**
     * number of blocks out of place
     * @return number of blocks out of place
     */
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (blocks[i][j] != 0 && i*n+j != blocks[i][j]-1) sum++;
        return sum;
    }
    
    /**
     * Calculates and returns Manhattan distances between blocks and goal
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        
        if (key == -1) {
            key = 0;
            int solutionx, solutiony, dx, dy;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    if (blocks[i][j] != 0) {
                        solutionx = (blocks[i][j]-1) / n;
                        solutiony = (blocks[i][j]-1) % n;
                        dx = (i < solutionx) ? 
                                solutionx - i : i - solutionx;
                        dy = (j < solutiony) ? 
                                solutiony - j : j - solutiony;
                        key += dx + dy;
                    }
                }
        }
        int value = key;
        return value;
    }
    
    /**
     * is this board the goal board?
     * @return true if the board corresponds to the goal board. 
     */
    public boolean isGoal() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (blocks[i][j] != 0 && i*n+j != blocks[i][j]-1) return false;
        return true;
    }
    
    /**
     * a board that is obtained by exchanging any pair of blocks
     * @return a new board with one pair of blocks swapped.
     */
    public Board twin() {
        int index = (zeroposition >= 2) ? 0 : zeroposition + 1;
        return mutateBoard(index / n, index % n, (index+1) / n, (index+1) % n);
    }
    
    /* 
     * (non-Javadoc)
     * does this board equal y?
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object y) {
        if (y == null || !(y instanceof Board)) return false;
        Board that = (Board) y;
        if (this.n != that.n || this.zeroposition != that.zeroposition) return false;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
        return true;
    }
    
    /**
     * all neighboring boards
     * @return all neighboring boards.
     */
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    private Board[] neighbors = getNeighbors(); 
                    private int current = 0;
                    public boolean hasNext() { return current != neighbors.length; }
                    public void remove() { 
                        throw new UnsupportedOperationException(); 
                    }
                    public Board next() {
                        if (!hasNext()) throw new NoSuchElementException();
                        Board item = neighbors[current++];
                        return item;
                    }
                };
            }
        };
    }
    
    private Board[] getNeighbors() {
        int numneighbors = 4;
        int zerox = zeroposition / n;
        int zeroy = zeroposition % n;
        if (zerox == 0 || zerox == n-1) numneighbors--;
        if (zeroy == 0 || zeroy == n-1) numneighbors--;
        Board[] neighbors = new Board[numneighbors];
        
        if (zerox - 1 >= 0)
            neighbors[--numneighbors] = makeNeighbor(zerox, zeroy, zerox-1, zeroy);
        if (zerox + 1 < n)
            neighbors[--numneighbors] = makeNeighbor(zerox, zeroy, zerox+1, zeroy);
        if (zeroy - 1 >= 0) 
            neighbors[--numneighbors] = makeNeighbor(zerox, zeroy, zerox, zeroy-1);
        if (zeroy + 1 < n)  
            neighbors[--numneighbors] = makeNeighbor(zerox, zeroy, zerox, zeroy+1);
        return neighbors;
    }
    
    private Board makeNeighbor(int zerox, int zeroy, int nx, int ny) {
        Board newboard = mutateBoard(zerox, zeroy, nx, ny);
        newboard.zeroposition = (nx)*n + ny;
        return newboard;
    }
    
    private Board mutateBoard(int cx, int cy, int nx, int ny) {
        Board newboard = new Board(this.blocks);
        int temp = newboard.blocks[cx][cy];
        newboard.blocks[cx][cy] = newboard.blocks[nx][ny];
        newboard.blocks[nx][ny] = temp;
        newboard.key = -1;
        return newboard;
    }
    
    /* 
     * (non-Javadoc)
     * string representation of this board (in the output format specified below)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String rep = "\n" + n + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                rep += " " + blocks[i][j] + " ";
            rep += "\n";
        }
        return rep;
    }
    
    /**
     * Unit tests
     * @param args
     */
    public static void main(String[] args) {

        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            StdOut.println(initial.toString());
        }
    }
}
