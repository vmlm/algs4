public class MyResizingArrayBag<Item> extends MyArrayBag<Item> {
    private Item[] b;
     
    public MyResizingArrayBag() {
        setBag((Item[]) new Object[1]);
        setNumItems(0);
    }
    
    @Override
    public void setBag(Item[] bag) { 
        b = bag;
        super.setBag(bag);
    }
    
    @Override
    public void add(Item x) { 
        if (getNumItems() == b.length) resize(b.length*2);
        super.add(x);
    }
    
    public void resize(int capacity) {
        Item[] new_b = (Item[]) new Object[capacity];
        for (int i = 0; i < getNumItems(); i++) new_b[i] = b[i];
        setBag(new_b);
    }
}
