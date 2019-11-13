package org.javasim.examples.basic;

import java.util.ArrayList;
import java.util.List;
import org.javasim.RestartException;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class Monitor extends SimulationProcess {
	private Machine mTarget;
	private Queue qTarget;
	private double interval;
	private int checkCount = 0;
	private List<Long> queueLength = new ArrayList<Long>();
	private List<Integer> utilization = new ArrayList<Integer>();
	
	/**
	 * Monitor's constructor
	 * @param interval scan interval
	 * @param machine to be monitored
	 * @param queue to be monitored
	 */
	public Monitor(double interval, Machine machine, Queue queue) {
		this.interval = interval;
		mTarget = machine;
		qTarget = queue;
	}
	
	/**
	 * Start monitoring machine's utilization and queue's length
	 */
	public void run() {
		while(true) {
			queueLength.add(qTarget.queueSize());
			utilization.add(mTarget.processing() ? 1 : 0);
			checkCount++;
			try {
				hold(interval);
			} catch (SimulationException e) {
				e.printStackTrace();
			} catch (RestartException e) {
				e.printStackTrace();
			}
		}
	}
	
	public double avgQueueLength() {
		return queueLength.stream().mapToLong(i -> i.longValue()).sum() / checkCount;
	}
	
	public double utilization() {
		return new Double(utilization.stream().mapToInt(i -> i.intValue()).sum()) / checkCount;
	}
}
