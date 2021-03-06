import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] queue;
    private int first;
    private int last;
    private int numitems;

    /** construct an empty randomized queue
     * 
     */
    @SuppressWarnings("unchecked")
    public RandomizedQueue() { 
        queue = (Item[]) new Object[2];
        first = 0;
        last = 0;
        numitems = 0;
    }
    
    /** is the queue empty?
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() { return numitems == 0; }
    
    /** return the number of items on the queue
     * @return numitems
     */
    public int size() { return numitems; }
    
    /** add the item
     * @param item: add item to back of queue.
     */
    public void enqueue(Item item) { 
        if (item == null) throw new NullPointerException();
        queue[last++] = item;
        numitems++;
        if (numitems == queue.length) resize(queue.length*2);
        if (last == queue.length) last = 0;
    }
    
    /**remove and return a random item
     * @return a random item from the queue
     */
    public Item dequeue() {
        if (numitems == 0) throw new NoSuchElementException();
        int k = (first+StdRandom.uniform(numitems)) % queue.length;
        Item item = queue[k];
        queue[k] = queue[first];
        queue[first++] = null;
        numitems--;
        if (numitems != 0 && numitems == queue.length/4) resize(queue.length/2); 
        if (first == queue.length) first = 0; 
        return item;
    }
    
    /**return (but do not remove) a random item
     * @return reference to a random item
     */
    public Item sample() {
        if (numitems == 0) throw new NoSuchElementException();
        return queue[(first+StdRandom.uniform(numitems)) % queue.length]; 
    }
    
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] newqueue = (Item[]) new Object[capacity];
        for (int i = 0; i < numitems; i++) 
            newqueue[i] = queue[(first+i) % queue.length];
        first = 0;
        last = numitems;
        queue = newqueue;
        
    }
    
    private int [] randomIndexArray() {
        int k, val;
        int[] shuffledarray = new int[numitems];
        for (int i = 0; i < numitems; i++) shuffledarray[i] = i;
        for (int i = 1; i < numitems; i--) {
            k = StdRandom.uniform(i+1);
            val = shuffledarray[k]; 
            shuffledarray[k] = shuffledarray[i];
            shuffledarray[i] = val;
        }
        return shuffledarray; 
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int[] shuffled = randomIndexArray();
        private int unsampled = shuffled.length;
        @Override
        public boolean hasNext() { return unsampled != 0; }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            Item item = queue[(first+shuffled[--unsampled]) % queue.length];
            return item; 
        }

        @Override
        public void remove() { throw new UnsupportedOperationException(); }
    }
    
    /* (non-Javadoc) return an independent iterator over items in random order
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Item> iterator() { return new ArrayIterator(); }
    
    /**unit testing
     * @param args
     */
    public static void main(String[] args) { 
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) StdOut.print(queue.dequeue());
            else queue.enqueue(s);
        }
        
        for (Iterator<String> iter = queue.iterator(); iter.hasNext();) {
            String item = iter.next();
            StdOut.print(item + " ");
        }
        StdOut.println();
    }

}
