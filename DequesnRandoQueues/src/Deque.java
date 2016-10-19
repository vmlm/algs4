import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/** Deque
 * @author VMLM
 *
 * A deque acts both as a stack and a queue. Given the memory requirements of the 
 * assignment, it's very probable that only an array implementation will be 
 * suitable. Therefore, this will be the first structure we implement.
 * 
 * We will use a circular array, indicating the beginning and end of the deque.
 * AddFirst will decrease the array index pointing toward the first element.
 * AddLast will increase the array index pointing toward the last element.
 * 
 * The deque will also resize, to twice its size whenever the number of elements
 * equals the current capacity of the array, or to one fourth its size whenever
 * the number of elements equals one fourth the current capacity of the array.
 *  
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
    
    private Item[] d;
    private int first;
    private int last;
    private int numitems;
    
    /** Construct an empty deque
     * 
     */
    public Deque() {
        d = (Item[]) new Object[2];
        first = 0;
        last = 1;
        numitems = 0;
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
    
    /** add the item to the front
     * 
     * if first == 0, it will circle to d.length() and vice versa.
     * @param item: add to front
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        if (first == 0) first = d.length;
        d[--first] = item;
        numitems++;
        if (numitems == d.length) resize(d.length*2);
    }
    
    /** add the item to the end
     * @param item: add to end
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        if (last == d.length-1) last = -1;
        d[++last] = item;
        numitems++;
        if (numitems == d.length) resize(d.length*2);
    }
    
    /** remove and return the item from the front
     * @return first item in the deque
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = d[first];
        d[first++] = null;
        numitems--;
        if (first == d.length) first = 0;
        if (numitems != 0 && numitems == d.length/4) resize(d.length/2);
        return item;
    }
    
    /** remove and return the item from the end
     * @return last item in the deque
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = d[last];
        d[last--] = null;
        numitems--;
        if (last == -1) last = d.length-1;
        if (numitems != 0 && numitems == d.length/4) resize(d.length/2);
        return item;
    }
    
    private void resize(int capacity) {
        Item[] newdeque = (Item[]) new Object[capacity];
        int newfirst = capacity/2 - 1;
        for (int i = 0; i < numitems; i++) 
            newdeque[newfirst + i] = d[(first+i) % d.length];
        first = newfirst;
        last = newfirst + numitems - 1;
        d = newdeque;
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int current = first;
        @Override
        public boolean hasNext() { return d[(current) % d.length] != null; }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return d[(current++) % d.length]; 
        }

        @Override
        public void remove() { throw new UnsupportedOperationException(); }
    }
    
    /* (non-Javadoc) return an iterator over items in order from front to end
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    /**unit testing
     * @param args arguments
     */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
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
