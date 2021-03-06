import java.util.Iterator;

public class MyStaticArrayStack<Item> implements StackBehavior<Item> {
    
    private Item[] stack;
    private int first;
    
    public MyStaticArrayStack() {
        first = 0;
        stack = (Item[]) new Object[1];
    }

    private class ArrayIterator implements Iterator<Item> {
        private int current = first;
        public boolean hasNext() { return (current != 0); }
        public void remove() { /* not supported */ }
        public Item next() { return stack[--current]; }
    }
    
    public Iterator<Item> iterator() { return new ArrayIterator(); }

    @Override
    public void push(Item item) {
        if (first == stack.length) resize(stack.length*2); 
        stack[first++] = item;
    }

    @Override
    public Item pop() {
        
        if (first > 0) {
            Item item = stack[--first];
            stack[first] = null;
            if (first > 0 && first == stack.length/4) resize(stack.length/2);
            return item;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return (first == 0);
    }

    @Override
    public int size() {
        return first;
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        
        for (int i = 0; i < first; i++) {
            copy[i] = stack[i];
        }       
        stack = copy;
    }
}
