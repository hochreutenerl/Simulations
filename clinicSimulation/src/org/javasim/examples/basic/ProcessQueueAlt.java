package org.javasim.examples.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Basically same as ProcessQueue, but created using Java's List.
 *
 * @param <T> the type of objects to store in the list
 */
public class ProcessQueueAlt<T> {
	private List<T> q = new ArrayList<T>();
	
	public ProcessQueueAlt() {
		
	}
	
	public boolean isEmpty() {
		if (q.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public int queueSize() {
		return q.size();
	}
	
	public T dequeue() throws NoSuchElementException {
		if (isEmpty()) {
			throw(new NoSuchElementException());
		}
		
		T elem = q.get(0);
		q.remove(0);
		return elem;
	}
	
	public void enqueue(T elem) {
		q.add(elem);
	}
	
	/**
	 * Gives the first object in the list without removing it from the list
	 * @return first object in the list
	 */
	public T getFirst() {
		return q.get(0);
	}
}
