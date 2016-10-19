import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LinkedDeque<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int numitems;
    
    public LinkedDeque() {
        first = null;
        last = null;
        numitems = 0;
    }
    
    private class Node {
        private Node next;
        private Node previous;
        private Item item;
    }
    
    /** is the deque empty?
     * @returns true if empty, false otherwise
     */
    public boolean isEmpty() {
        return numitems == 0;
    }
    
    /** return the number of items on the deque
     * @return number of items
     */
    public int size() {
        return numitems;
    }
    
    private void addInitialNode(Node newnode) {
        first = newnode;
        last = newnode;
        newnode.next = null;
        newnode.previous = null;
    }
    
    /** add the item to the front
     * 
     * if first == 0, it will circle to d.length() and vice versa.
     * @param item: add to front
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node newnode = new Node();
        newnode.item = item;
        if (first == null && last == null) {
            addInitialNode(newnode);
        } else {
            newnode.next = first;
            first.previous = newnode;
            newnode.previous = null;
            first = newnode;
        }
        numitems++;
    }
    
    /** add the item to the end
     * @param item: add to end
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node newnode = new Node();
        newnode.item = item;
        if (first == null && last == null) {
            addInitialNode(newnode);
        } else {
            newnode.previous = last;
            last.next = newnode;
            newnode.next = null;
            last = newnode;
        }
        numitems++;
    }
    
    /** remove and return the item from the front
     * @return first item in the deque
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        if (first.next != null) {
            first = first.next;
            first.previous = null;
        } else {
            first = null;
            last = null;
        }
        numitems--;
        return item;
    }
    
    /** remove and return the item from the end
     * @return last item in the deque
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        if (last.previous != null) {
            last = last.previous;
            last.next = null;
        } else {
            first = null;
            last = null;
        }
        numitems--;
        return item;
    }
    
    private class LinkedIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() { return current != null; }
        
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        public void remove() { throw new UnsupportedOperationException(); }
    }
    
    /* (non-Javadoc) return an iterator over items in order from front to end
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }
    
    /** unit testing
     * @param args arguments
     */
    public static void main(String[] args) {
        LinkedDeque<Integer> deque = new LinkedDeque<Integer>();
        int i = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) StdOut.println(deque.removeLast());
            else if (s.equals("+")) StdOut.println(deque.removeFirst());
            else if (s.equals("/")) deque.addFirst(++i);
            else if (s.equals("*")) deque.addLast(++i);
        }
        
        for (Iterator<Integer> iter = deque.iterator(); iter.hasNext();) {
            int item = iter.next();
            StdOut.print(item + " ");
        }
        StdOut.println();
    }
}
