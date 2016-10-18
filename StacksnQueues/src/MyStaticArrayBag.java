
public class MyStaticArrayBag<Item> extends MyArrayBag<Item> {
    
    public MyStaticArrayBag(int n) {
        setBag((Item[]) new Object[n]);
        setNumItems(0);
    }    
    
    @Override
    public void add(Item x) {
        if (getNumItems() == getBag().length) 
            throw new ArrayIndexOutOfBoundsException();
        super.add(x);
    }
}
