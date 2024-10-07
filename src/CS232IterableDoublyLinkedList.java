package src;

import java.util.NoSuchElementException;

/**
 * Doubly linked list implementation of the CS232List interface.
 * 
 * @author Grant Braught
 * @author Dickinson College
 * @version Feb 18, 2016
 */
public class CS232IterableDoublyLinkedList<E> implements CS232List<E>, CS232Iterable<E> {

    private DLLNode head;
    private DLLNode tail;
    private int size;

    /**
     * Construct a new empty CS232DoublyLinkedList.
     */
    public CS232IterableDoublyLinkedList() {
        // Dummy head and tail nodes to simplify insert/remove/add operations
        head = new DLLNode(null, null, null);
        tail = new DLLNode(null, head, null);
        head.next = tail;
        size = 0;
    }

    // Other methods...

    public int size() {
        return size;
    }

    public void add(E element) {
        DLLNode pred = tail.prev;
        DLLNode node = new DLLNode(element, pred, tail);
        pred.next = node;
        tail.prev = node;
        size++;
    }

    private DLLNode getNode(int index) {
        checkBounds(index);
        DLLNode cur = head.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + " on List of size " + size + ".");
        }
    }

    public E get(int index) throws IndexOutOfBoundsException {
        return getNode(index).element;
    }

    public void set(int index, E element) throws IndexOutOfBoundsException {
        getNode(index).element = element;
    }

    public void insert(int index, E element) throws IndexOutOfBoundsException {
        // Allow inserting at the end
        DLLNode succ = (index == size) ? tail : getNode(index);
        DLLNode node = new DLLNode(element, succ.prev, succ);
        succ.prev.next = node;
        succ.prev = node;
        size++;
    }

    public E remove(int index) throws IndexOutOfBoundsException {
        DLLNode node = getNode(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.element;
    }

    private class DLLNode {
        public E element;
        public DLLNode prev;
        public DLLNode next;

        public DLLNode(E element, DLLNode prev, DLLNode next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    public CS232Iterator<E> getIterator() {
        return new DLLIterator();
    }

    private class DLLIterator implements CS232Iterator<E> {

        private DLLNode cursor;

        public DLLIterator() {
            cursor = head; // Start at the dummy head
        }

        public boolean hasNext() {
            return cursor.next != tail; // Check if next node is not tail
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element.");
            }
            cursor = cursor.next; // Move cursor to next node
            return cursor.element; // Return the element at the new cursor position
        }

        public boolean hasPrevious() {
            return cursor.prev != head; // Check if cursor is not at the first actual element
        }

        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("There is no previous element.");
            }
            cursor = cursor.prev; // Move cursor back
            return cursor.element; // Return the previous element
        }

        public void insert(E element) {
            DLLNode node;
            if (cursor == head) {
                // Inserting at the start of the list
                node = new DLLNode(element, head, head.next);
                head.next.prev = node;
                head.next = node;
            } else {
                // Inserting after the current cursor position
                node = new DLLNode(element, cursor, cursor.next);
                cursor.next.prev = node;
                cursor.next = node;
            }
            cursor = node; // Move cursor to the new node
            size++;
        }

        public E remove() {
            if (cursor == head || cursor == tail) {
                throw new NoSuchElementException("Cannot remove from this position.");
            }
            E element = cursor.element; // Store the element to return
            // Re-link previous and next nodes
            cursor.prev.next = cursor.next;
            cursor.next.prev = cursor.prev;
            cursor = cursor.next; // Move cursor forward after removal
            size--;
            return element; // Return the removed element
        }
    }

    public boolean checkListIntegrity() {
        if (size == 0) {
            return head.next == tail && tail.prev == head;
        } else {
            DLLNode cur = head.next;
            while (cur != tail) {
                if (cur.prev.next != cur || cur.next.prev != cur) {
                    return false;
                }
                cur = cur.next;
            }
        }
        return true;
    }
}