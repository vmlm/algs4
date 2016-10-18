import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

class StacknQueueTester {
    
    private static void queueIf(String s, QueueBehavior<String> queue) {
        if (s.equals("-")) StdOut.print(queue.dequeue());
        else queue.enqueue(s);
    }
    
    private static void stackIf(String s, StackBehavior<String> stack) {
        if (s.equals("-")) StdOut.print(stack.pop());
        else stack.push(s);
    }
    
    public static void main(String[] args) {
        // Stack Test client
//        MyResizingArrayStack<String> snq = new MyResizingArrayStack<String>();
        MyLinkedStack<String> snq = new MyLinkedStack<String>();
//        ResizingArrayQueueOfStrings snq = new ResizingArrayQueueOfStrings();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            stackIf(s, snq);
//            queueIf(s,snq);
        }
    }
}
