import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedStack<Item> implements StackBehavior<Item> {

    private Node first;
    private int items;
    
    private class Node {
        private Item item;
        private Node next;
    }
    
    private class ListItarator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() { return current != null; }
        public void remove() { /* not supported */ }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public MyLinkedStack() {
        first = null;
        items = 0;
    }
    
    public void push(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        items++;
    }
    
    public Item pop() {
        if (!isEmpty()) {
            Item item = first.item;
            first = first.next;
            items--;
            return item;
        }
        return null;
    }
    

    public boolean isEmpty() {
        return (first == null);
    }
    
    public int size() {
        return items;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListItarator();
    }
}
