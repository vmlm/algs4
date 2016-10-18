
public class MyStaticArrayQueue<Item> implements QueueBehavior<Item> {
    
    private Item[] queue;
    private int first;
    private int last;
    private int numitems;
    
    public MyStaticArrayQueue(int n) {
        queue = (Item[]) new Object[n];
        first = 0;
        last = 0;
        numitems = 0;
    }
    
    @Override
    public void enqueue(Item item) {
        if (!(numitems == queue.length)) {
            queue[last++] = item;
            numitems++;
        }
    }

    @Override
    public Item dequeue() {
        if (!(first == last)) {
            Item item = queue[first];
            queue[first++] = null;
            numitems--;
            if (numitems != 0 && numitems == queue.length/4) resize(queue.length/2); 
            if (first == queue.length) first = 0;
            return item;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return (numitems == 0);
    }

    @Override
    public int size() {
        return numitems;
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        
        for (int i = 0; i < numitems; i++) copy[i] = queue[(first+i) % queue.length];
        first = 0;
        last = numitems;
        queue = copy;
    }
}
