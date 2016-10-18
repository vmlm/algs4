import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class MyArrayBag<Item> implements BagBehavior<Item> {
    private Item[] b;
    private int numitems;
    
    public Item[] getBag() { return b; }
    public void setBag(Item[] bag) { b = bag; }
    
    public int getNumItems() { return numitems; }
    public void setNumItems(int n) { this.numitems = n; }
    
    private class ArrayIterator implements Iterator<Item> {
        private int current = 0;
        
        public boolean hasNext() { return current < numitems; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return b[current++]; 
        }
    }
    
    public void add(Item x) { 
        b[numitems++] = x;
    }
    
    public int size() {
        return numitems;
    }
    
    @Override
    public Iterator<Item> iterator() { return new ArrayIterator(); }
}
