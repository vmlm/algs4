import java.util.NoSuchElementException;

public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] heap;
    private int n;
    private int maxn;
    
    MaxPQ() { 
        heap = (Key[]) new Comparable[4];
        n = 0;
        maxn = 4;
    }
    
    private void resizeArray(int size) {
        Key[] newheap = (Key[]) new Comparable[size];
        System.arraycopy(heap, 1, newheap, 1, n);
        heap = newheap;
        maxn = size;
    }
    
    private void exch(int i, int j) {
        Key v = heap[i];
        heap[i] = heap[j];
        heap[j] = v;
    }
    
    private boolean less(int i, int j) {
        return heap[i].compareTo(heap[j]) < 0;
    }
    
    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }
    
    private void swim(int k) {
        while (k > 1 && less(k, k/2)) {
            exch(k, k/2);
            k = k/2;
        }
    }
    
    public void insert(Key v) {
        if (n+1 == maxn) resizeArray(maxn*2);
        heap[++n] = v;
        swim(n);
    }
    
    public Key delMax() {
        if (n == 0) throw new NoSuchElementException(); 
        Key v = heap[1];
        heap[1] = heap[n--];
        heap[n+1] = null;
        sink(1);
        if (n > 1 && n == maxn/4) resizeArray(maxn/2);
        return v;
    }
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    public Key max() {
        return heap[1];
    }
    
    public int size() {
        return n;
    }
}
