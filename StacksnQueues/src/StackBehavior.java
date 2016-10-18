
public interface StackBehavior<A> extends Iterable<A> {
    /**Pushes  "item" into the stack.
     * @param item: A String object that will be added to the stack.
     */
    void push(A item);
    
    /**Pops out and returns the top-most item in the stack.
     * @return THe first item in the stack.
     */
    A pop();
    
    /**Indicates whether or not the stack is empty
     * @return: True if the stack is empty, false otherwise.
     */
    boolean isEmpty();
    
    /**SIze of the stack
     * @return: size of the stack
     */
    int size();
}
