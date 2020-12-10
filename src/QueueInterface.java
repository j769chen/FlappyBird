public interface QueueInterface {
    //Adds the given object to the end of the queue
    void add (Object o);

    //Removes and returns the first object in the queue
    Object remove ();

    //Returns the first object in the queue
    Object peek ();

    //Returns the number of elements in the queue
    int size ();

    //True if the queue is empty, false otherwise
    boolean isEmpty ();

    //Removes all items from the queue
    void clear ();
}
