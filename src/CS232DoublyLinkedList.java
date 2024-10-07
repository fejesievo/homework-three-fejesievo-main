package src;

/**
 * Doubly linked list implementation of the CS232List interface.
 * 
 * @author Grant Braught
 * @author Dickinson College
 * @version Feb 18, 2016
 */
public class CS232DoublyLinkedList<E> implements CS232List<E> {

	private DLLNode head;
	private DLLNode tail;
	private int size;

	/**
	 * Construct a new empty CS232DoublyLinkedList.
	 */
	public CS232DoublyLinkedList() {
		/*
		 * This implementation uses dummy head and tail nodes to simplify the
		 * implementation of insert/remove/add operations at the start or end of
		 * the list.
		 */
		head = new DLLNode(null, null, null);
		tail = new DLLNode(null, head, null);
		head.next = tail;
		size = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(E element) {
		DLLNode pred = tail.prev;
		DLLNode node = new DLLNode(element, pred, tail);
		pred.next = node;
		tail.prev = node;

		size++;
	}

	/*
	 * Helper method to get the DLLNode at the specified index.
	 * 
	 * Throws exception if the index is not valid.
	 */
	private DLLNode getNode(int index) {
		checkBounds(index);
		DLLNode cur = head.next;
		for (int i = 0; i < index; i++) {
			cur = cur.next;
		}
		return cur;
	}

	/*
	 * Helper method that throws an exception if the index is not valid.
	 */
	private void checkBounds(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Invalid index: " + index
					+ " on List of size " + size + ".");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public E get(int index) throws IndexOutOfBoundsException {
		DLLNode node = getNode(index);
		return node.element;
	}

	/**
	 * {@inheritDoc}
	 */
	public void set(int index, E element) throws IndexOutOfBoundsException {
		DLLNode node = getNode(index);
		node.element = element;
	}

	/**
	 * {@inheritDoc}
	 */
	public void insert(int index, E element) throws IndexOutOfBoundsException {
		/*
		 * If the index is passed the end of the list, then tail will succeed
		 * (appear immediately after) the new node. Otherwise, the node at index
		 * succeeds the new node.  Need this because, getNode throws an exception
		 * when index is out of range.
		 */
		DLLNode succ = tail;
		if (index != size) {
			succ = getNode(index);
		}

		// Whatever succ points to now comes after new node.
		DLLNode node = new DLLNode(element, succ.prev, succ);
		succ.prev.next = node;
		succ.prev = node;

		size++;
	}

	/**
	 * {@inheritDoc}
	 */
	public E remove(int index) throws IndexOutOfBoundsException {
		checkBounds(index);

		DLLNode holder = getNode(index);
		E data = holder.element;

		holder.prev.next = holder.next;
		holder.next.prev = holder.prev;

		size--;

		return data;

		
	}

	/**
	 * Clear all elements of the list up to and including index. The element, if
	 * any, at index+1 becomes the first element in the list.
	 * 
	 * @param index
	 *            the index up to which to clear the list.
	 * @throws IndexOutOfBoundsException
	 *             if index < 0 or index >= size()
	 */
	public void clearTo(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("accessed " + index + " but size is " + size);
        }
		


		DLLNode holder = head.next;

		for (int i = 0; i <= index; i++){
			DLLNode holderNext = holder.next;

			holder.prev = null;
			holder.next = null;

			holder = holderNext;
		}

		head.next = holder;

		if (holder != null) {
			holder.prev = head;
		} else {
			tail.prev = head;
		}
		size -= (index + 1);
		
	}

	/**
	 * Add all of the elements of the provided list into this list. The first
	 * element of the provided list appears at the specified index in this list.
	 * The element at index, if any, in this list appears immediately following
	 * the last element of list. Note that it is possible to addAllAt the end of
	 * the list by providing the list's size as the index.
	 * 
	 * @param index
	 *            the index at which to add the elements.
	 * @param list
	 *            the list containing the elements to be added.
	 * @throws IndexOutOfBoundsException
	 *             if index < 0 or index > size()
	 * @throws IllegalArgumentException
	 *             if list is empty.
	 */
	public void addAllAt(int index, CS232DoublyLinkedList<E> list)
			throws IndexOutOfBoundsException {
				if (index < 0 || index > size){
					throw new IndexOutOfBoundsException("accessed " + index + " but size is " + size);
				}

				if (list.size() == 0) {
					throw new IllegalArgumentException("List is empty");
				}

				DLLNode holder = head.next;

				for (int i = 0; i < index; i++) {
					holder = holder.next;
				}


				DLLNode newHolder = holder;
				DLLNode listHead = list.head.next;
				DLLNode listTail = list.tail.prev;
				
			
				if (index == 0){
					head.next = listHead;
					listHead.prev = head;
				} else {

					DLLNode indexPrev = holder.prev;
					indexPrev.next = listHead;
					listHead.prev = indexPrev;
				}

				listTail.next = newHolder;
				if (newHolder != null) {
					listTail.next = newHolder;
					newHolder.prev = listTail;
				} else {
					tail.prev = listTail;
					listTail.next = tail;
				}

				size += list.size;	
	}

	/*
	 * Defines the node object for the doubly linked list. Note: Fields are
	 * public so that they can be accessed directly rather than via accessors
	 * and mutators. This make the implementations of the doubly linked list
	 * methods above easier to implement and read. And because the DLLNode class
	 * is private to this class it is not an egregious violation of
	 * encapsulation.
	 */
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

	/**
	 * Helper method for testing that checks that all of the links are
	 * symmetric.
	 * 
	 * @return true if all of the links are correct.
	 */
	public boolean checkListIntegrity() {
		if (size == 0) {
			return head.next == tail && tail.prev == head;
		} else {
			DLLNode cur = head.next;

			while (cur.next != tail) {

				DLLNode prev = cur.prev;
				DLLNode next = cur.next;

				if (prev.next != cur || next.prev != cur) {
					return false;
				}

				cur = cur.next;
			}
		}
		return true;
	}
}