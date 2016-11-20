import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<SearchNode> pq1;
    private MinPQ<SearchNode> pq2;
    private SearchNode solution;
    private boolean solvable;
    
    
    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode previous;
    }
    
    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial
     */
    public Solver(Board initial) {
        Comparator<SearchNode> nodeCompare = getNodeComparator();
        
        pq1 = new MinPQ<SearchNode>(nodeCompare);
        pq2 = new MinPQ<SearchNode>(nodeCompare);
        SearchNode first = new SearchNode();
        first.board = initial;
        pq1.insert(first);
        first = new SearchNode();
        first.board = initial.twin();
        pq2.insert(first);
        solution = null;
        solvable = false;
        
        while (true) {
            solution = aStarIteration(pq1);
            if (solution != null) {
                solvable = true;
                break;
            }
            solution = aStarIteration(pq2);
            if (solution != null) break;
        }
    }
    
    private Comparator<SearchNode> getNodeComparator() {
        return new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode a, SearchNode b) {
                // compare boards by manhattan distance
                return (a.board.manhattan() + a.moves) - 
                        (b.board.manhattan() + b.moves);
            }
        };
    }
    
    private SearchNode aStarIteration(MinPQ<SearchNode> pq) {
        SearchNode v = pq.delMin();
        if (v.board.isGoal()) return v;
        for (Iterator<Board> neighbors = 
                v.board.neighbors().iterator(); neighbors.hasNext();) {
            Board neighbor = neighbors.next();
            if (v.previous == null || !v.previous.board.equals(neighbor)) {
                SearchNode next = new SearchNode();
                next.board = neighbor;
                next.moves = v.moves+1;
                next.previous = v;
                pq.insert(next);
            }           
        }
        return null;
    }
    
    private Board[] getSolutionArray(SearchNode n) {
        return getSolutionArray(n, 1);
    }
    
    private Board[] getSolutionArray(SearchNode n, int level) {
        Board [] solutionArray;
        if (n.previous == null)
            solutionArray = new Board[level];
        else
            solutionArray = getSolutionArray(n.previous, level+1);
        solutionArray[solutionArray.length - level] = n.board;
        return solutionArray;
    }
    
    /**
     * is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
        return solvable;
    }
    
    /**
     * min number of moves to solve initial board; -1 if unsolvable
     * @return
     */
    public int moves() {
        if (solvable)
            return solution.moves;
        else return -1;
    }
    
    /**
     * sequence of boards in a shortest solution; null if unsolvable
     * @return
     */
    public Iterable<Board> solution() {
        if (solvable)
            return new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {
                    return new Iterator<Board>() {
                        private Board[] boards = getSolutionArray(solution); 
                        private int current = 0;
                        public boolean hasNext() { return current != boards.length; }
                        public void remove() { 
                            throw new UnsupportedOperationException(); 
                        }
                        public Board next() {
                            if (!hasNext()) throw new NoSuchElementException();
                            Board item = boards[current++];
                            return item;
                        }
                    };
                }
            };
        else return null;
    }
    
    /**
     * solve a slider puzzle (given below)
     * @param args
     */
    public static void main(String[] args) {
        String filename;
        if (args.length == 0)
            filename = "D:\\Users\\VMLM\\projects\\algs4"
                    + "\\8-puzzle\\bin\\8puzzle\\puzzle3x3-unsolvable.txt";
        else
            filename = args[0];
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
        Solver solver = new Solver(initial);
        StdOut.println(filename + ": " + solver.moves());
    }
}
