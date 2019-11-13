package org.javasim.examples.basic;

import java.util.NoSuchElementException;

import org.javasim.SimulationProcess;

public class ProcessQueue {
	private PList head;
	private PList tail;
	private long length;
	
	public ProcessQueue() {
		head = null;
		tail = null;
		length = 0;
    }

	public boolean IsEmpty() {
		if (length == 0)
			return true;
		else
			return false;
    }

	public long QueueSize() {
		return length;
    }

	public SimulationProcess Dequeue() throws NoSuchElementException {
		if (IsEmpty())
			throw(new NoSuchElementException());

		PList ptr = head;
		head = head.next;
		length--;
		if (IsEmpty())
			tail = null;

		return ptr.work;
    }

	public void Enqueue(SimulationProcess toAdd) {
		if (toAdd == null)
			return;

		PList ptr = head;

		if (IsEmpty()) {
			head = new PList();
			ptr = head;
			tail = head;
		} else {
			ptr = tail;
			ptr.next = new PList();
			tail = ptr.next;
		}
		
		tail.next= null;
		tail.work= toAdd;
		length++;
    }
	
	/**
	 * Gives pointer to the next process in the queue.
	 * @return next process in the queue
	 */
	public SimulationProcess getNextProcess() {
		return head.work;
	}

};

class PList {
	public SimulationProcess work;
	public PList next;

	public PList() {
		work = null;
		next = null;
    }
};