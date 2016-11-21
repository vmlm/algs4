import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;

/**
 * Maintain an ordered array of key-value pairs.
 * @author VMLM
 *
 * @param <Key>
 * @param <Value>
 */
public class OrderedST<Key extends Comparable<Key>, Value> 
implements ST<Key, Value> {
    private Key[] keys;
    private Value[] vals;
    private int n;
    private int maxn;
    
    @SuppressWarnings("unchecked")
    public OrderedST() { 
        //ordered resizing array.
        keys = (Key[]) new Comparable[2];
        vals = (Value[]) new Comparable[2];
        n = 0;
        maxn = 2;
    }
    
    private int rank(Key key) {
        int lo, mid, hi, cmp;
        lo = 0;
        hi = n-1;

        while (lo <= hi) {
            mid = lo + (hi-lo)/2;
            cmp = key.compareTo(keys[mid]);
            if (cmp > 0) lo = mid + 1;
            else if (cmp < 0) hi = mid - 1;
            else return mid;
        }
        return lo;
    }
    
    private void resizeKeys(int capacity) {
        @SuppressWarnings("unchecked")
        Key[] copy = (Key[]) new Comparable[capacity];
        System.arraycopy(keys, 0, copy, 0, n);
        keys = copy;
    }
    
    private void resizeVals(int capacity) {
        @SuppressWarnings("unchecked")
        Value[] copy = (Value[]) new Object[capacity];
        System.arraycopy(vals, 0, copy, 0, n);
        vals = copy;
    }
    
    public void put(Key key, Value val) {
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0)
            vals[i] = val;
        else {
            if (n+1 == maxn) {
                maxn *= 2;
                resizeKeys(maxn);
                resizeVals(maxn);
            }
            
            n++;
            for (int k = n-1; k > i; k--) {
                keys[k] = keys[k-1];
                vals[k] = vals[k-1];
            }
            keys[i] = key;
            vals[i] = val;    
        }
    }
    
    public Value get(Key key) {
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) return vals[i];
        else return null;
    }
    
    public void delete(Key key) {
        if (isEmpty()) throw new NullPointerException();
        int i = rank(key);
        if (i == n || key.compareTo(keys[i]) != 0)
            throw new NoSuchElementException();
        for (int k = i; k < n-1; k++) {
            keys[k] = keys[k+1];
            vals[k] = vals[k+1];
        }
        keys[n-1] = null;
        vals[n-1] = null;
        n--;
        if (n > 1 && n <= maxn/4) {
            maxn /= 4;
            resizeKeys(maxn);
            resizeVals(maxn);
        }
    }
    
    public boolean contains(Key key) {
        int i = rank(key);
        return (i < n && keys[i].compareTo(key) == 0);
    }
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    public int size() {
        return n;
    }
    
    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        for (int i = 0; i < n; i++)
            q.enqueue(keys[i]);
        return q;
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
