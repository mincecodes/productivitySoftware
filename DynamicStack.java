import java.util.LinkedList;

/**
 * This is the Dynamic Stack class, which implements the stackADT interface, to create a stack
 * which can be used for the deleted tasks in the productivity app.
 * 
 * @author Mince Codes
 * @version 01-18-2024
 */
class DynamicStack<T> implements StackADT<T> {
    private LinkedList<T> myList;
    
    /**
     * No-argument constructor that defines the linkedlist which holds the dynamic stack in it.
     * 
     */ 
    public DynamicStack() {
        myList = new LinkedList<T>();
    }
    
    /**
     * This push method adds the specified element to the top of the stack
     * 
     * @param element This is an element object of generic type T.
     */
    public void push( T element ) {
        myList.addFirst( element );
    }
    
    /**
     * This pop method removes the element from the top of the stack and returns a reference to it, 
     * or null (if empty).
     * 
     * @return T - This returns a generic type object
     */
    public T pop() {
        return myList.removeFirst(); 
    }
    
    
    /**
     * This peek method returns a reference to the element at the top of the stack, or null (if empty).
     * 
     * @return T - This returns a generic type object
     */
    public T peek() {
        return myList.peekFirst();
    }
    
    /**
     * This isEmpty() method checks if the stack is empty.
     * It returns true if the stack contains no elements, false otherwise.
     * 
     * @return boolean - true or false depending on if stack is empty.
     */
    public boolean isEmpty() {
        return myList.isEmpty();
    }
    
    /**
     * This size() method returns the number of elements in the stack.
     * 
     * @return int - number of items in the stack.
     */
    public int size() {
        return myList.size();
    }
    
    /**
     * This void clear() method clears all items currently in the stack.
     */
    public void clear() {
        myList.clear();
    }
    
    /**
     * This void clear() method returns a String representation of the stack.
     * 
     * @return String - The String representation of the object.
     */
    public String toString() {
        return myList.toString();
    }
}  