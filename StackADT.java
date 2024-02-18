/**
 * This is the StackADT interface, which defines the abstract methods needed for a stack to occur.
 * The dynamic stack class inherits from this interface.
 * 
 * @author Mince Codes
 * @version 01-18-2024
 */

public interface StackADT<T> {
    /**
     * This method will add one element to the top of the stack.
     * 
     * @param element T Object of generic type.
     */
    public void push(T element);
    
    /**
     * This method will remove and return a reference to the top element from the stack.
     * 
     * @return T - T Object of generic type.
     */
    public T pop();
    
    /**
     * This method will return a reference to the top element, without removing it from the stack.
     * 
     * @return T - T Object of generic type.
     */
    public T peek();
    
    /**
     * This method will return true if the stack contains no elements, false otherwise.
     * 
     * @return boolean - Value of either true or false
     */
    public boolean isEmpty();
    
    /**
     * This method will return true if the stack contains no elements, false otherwise.
     * 
     * @return boolean - Value of either true or false
     */
    public int size();
    
    /**
     * This void method will clear all elements from the stack.
     */
    public void clear();
    
    /**
     * This method will return a String representation of the stack.
     * 
     * @return String - A String representation of the object.
     */
    public String toString();
}