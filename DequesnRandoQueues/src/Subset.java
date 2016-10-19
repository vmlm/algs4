import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Subset {

    public static void main(String[] args) {
        int k = 3;
        if (args.length == 1) k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        String s;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            queue.enqueue(s);
        }
        for (int i = 0; i < k; i++) StdOut.println(queue.dequeue());
    }

}
