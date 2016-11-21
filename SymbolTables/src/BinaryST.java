import edu.princeton.cs.algs4.StdIn;

public class BinaryST<Key, Value> implements ST<Key, Value>  {
    
    public BinaryST() { }
    
    public void put(Key key, Value val) {
        
    }
    
    public Value get(Key key) {
        return null;
    }
    
    public void delete(Key key) {
        
    }
    
    public boolean contains(Key key) {
        return false;
    }
    
    public boolean isEmpty() {
        return true;
    }
    
    public int size() {
        return 0;
    }
    
    public Iterable<Key> keys() {
        return null;
    }
    
    public static void main(String[] args) {
        OrderedST<String, Integer> st = new OrderedST<String, Integer>();
        for (int i = 0; StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }

}
