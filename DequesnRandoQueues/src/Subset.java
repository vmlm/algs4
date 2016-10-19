import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {

    public static void main(String[] args) {
        int k = 3, numitems = 0, p;
        if (args.length == 1) k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        String s;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            if (numitems < k) {
                queue.enqueue(s);
            } else {
                p = StdRandom.uniform(numitems+1);
                if (p < k) {
                    queue.dequeue();
                    queue.enqueue(s); 
                }
            }
            numitems++;
        }
        
        for (int i = 0; i < k; i++) StdOut.println(queue.dequeue());
    }

}

