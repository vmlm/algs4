
public interface QueueBehavior<A> {
    
    /**Adds String to end of queue.
     * @param item: String to be added to end of queue
     */
    void enqueue(A item);
    
    /**Returns the first String in the queue.
     * @return: First string in the queue.
     */
    A dequeue();
    
    /**Indicates whether or not the queue is empty
     * @return: True if the queue is empty, false otherwise.
     */
    boolean isEmpty();
    
    
    /**SIze of the queue
     * @return: size of the queue.
     */
    int size();
}
