
public class MyLinkedQueue<Item> implements QueueBehavior<Item> {
    
    private class Node {
        private Item item;
        private Node next;
    }
    
    private Node first;
    private Node last;
    private int numItems;
    
    public MyLinkedQueue() {
        first = null;
        last = null;
        numItems = 0;
    }
    
    @Override
    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        
        if (isEmpty()) first = last;
        else last.next = oldlast;
        numItems++;
    }    
    
    @Override
    public Item dequeue() {
        if (first == null) return null;
        Item item = first.item;
        first = first.next;
        numItems--;
        if (isEmpty()) last = null;
        return item;
    }
    
    @Override
    public boolean isEmpty() {
        return (first == null);
    }
    
    @Override
    public int size() {
        return numItems;
    }
}
