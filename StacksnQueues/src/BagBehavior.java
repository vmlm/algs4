
public interface BagBehavior<T> extends Iterable<T> {
    void add(T x);
    int size();
}
