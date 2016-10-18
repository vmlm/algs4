import java.util.Iterator;

public class MyListBag<Item> implements BagBehavior<Item> {
    
    private Node first;
    private int numitems;
    
    public MyListBag() {
        first = null;
        numitems = 0;
    }
    
    private class Node {
        private Item item;
        private Node next;
    }
    
//    public Node getFirst() { return first; }
//    public void setFirst(Node f) { first = f; }
//    
//    public int getNumItems() { return numitems; }
//    public void setNumItems(int n) { numitems = n; }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() { 
            Item returnvalue = current.item;
            current = current.next;
            return returnvalue;
        }
    }
    
    @Override
    public Iterator<Item> iterator() { return new ListIterator(); }

    @Override
    public void add(Item x) {
        Node newnode = new Node();
        newnode.item = x;
        newnode.next = first;
        first = newnode;
        numitems++;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return numitems;
    }

}
