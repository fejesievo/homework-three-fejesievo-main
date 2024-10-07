package src;

import src.CS232ArrayList;

public class CS232ArrayStack<E> implements CS232Stack<E> {


    public CS232ArrayList<E> backingstore; 
    public int currentSize;


    public CS232ArrayStack(){
        backingstore = new CS232ArrayList<>();
        currentSize = 0;
    }

    public void push(E obj) {
        backingstore.add(obj);
        currentSize++;
    }

    /**
     * Remove the element from of the top of the stack and return it.
     * 
     * @return the element from the top of the stack or null if the stack is
     *         empty.
     */
    public E pop() throws IndexOutOfBoundsException {
        if (currentSize == 0){
            throw new IndexOutOfBoundsException("Accessing empty stack");
        }
        E element = backingstore.get(currentSize - 1);
        backingstore.remove(currentSize - 1);
        currentSize--;
        return element;

    }

    /**
     * Return a reference to the element on the top of the stack without
     * removing it.
     * 
     * @return a reference to the element on the top of the stack or null if the
     *         stack is empty.
     */
    public E peek() throws IndexOutOfBoundsException {
        if (currentSize == 0){
            throw new IndexOutOfBoundsException("Accessing empty stack");
        }
        E element = backingstore.get(currentSize - 1);
        return element;

    }
    
    /**
     * Return the number of elements contained in the stack.
     * 
     * @return the size of the stack.
     */
    public int size() {
        return currentSize;
    }


}
