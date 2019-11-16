/*
 * Copyright 1990-2008, Mark Little, University of Newcastle upon Tyne
 * and others contributors as indicated 
 * by the @authors tag. All rights reserved. 
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors. 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 * 
 * (C) 1990-2008,
 */

package org.javasim.examples.basic;

import org.javasim.RestartException;
import org.javasim.Simulation;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class MachineShop extends SimulationProcess {
	private static Queue prepQ = new Queue();
	private static Queue opQ = new Queue();
	private static Queue recQ = new Queue();
    private static ProcessQueue idleQ1;
    private static ProcessQueueAlt<Machine3> idleQ2;
    private static Machine2 op;
    public static double TotalResponseTime = 0.0;
    public static long TotalJobs = 0;
    public static long ProcessedJobs = 0;
    public static long JobsInQueue = 0;
    public static long CheckFreq = 0;
    public static double MachineActiveTime = 0.0;
    public static double MachineFailedTime = 0.0;
    
    public MachineShop(boolean isBreaks) {
        TotalResponseTime = 0.0;
        TotalJobs = 0;
        ProcessedJobs = 0;
        JobsInQueue = 0;
        CheckFreq = 0;
        MachineActiveTime = 0.0;
        MachineFailedTime = 0.0;
    }

    public void run () {
        try {
            Arrivals A = new Arrivals(25, 40, 20, 40);
            
            //Preparation machines
            idleQ1 = new ProcessQueue();
            for (int i = 0; i < 3; i++) {
            	idleQ1.Enqueue(new Machine1());
            }
            
            //Operation machine
            op = new Machine2();
            
            //Recovery machines
            idleQ2 = new ProcessQueueAlt<Machine3>();
            for (int i = 0; i < 3; i++) {
            	idleQ2.enqueue(new Machine3());
            }
            
            //Monitor the operation machine and operation queue
            Monitor mon = new Monitor(100, op, opQ);

            mon.activate();
            A.activate();

            Simulation.start();

            while (MachineShop.ProcessedJobs < 1000)
                hold(1000);

            System.out.println("Current time "+currentTime());
            System.out.println("Total number of jobs present " + TotalJobs);
            System.out.println("Total number of jobs processed "
                    + ProcessedJobs);
            System.out.println("Total response time of " + TotalResponseTime);
            System.out.println("Average response time = "
                    + (TotalResponseTime / ProcessedJobs));
            /*
            System.out
                    .println("Probability that machine is working = "
                            + ((MachineActiveTime - MachineFailedTime) / currentTime()));
            System.out.println("Probability that machine has failed = "
                    + (MachineFailedTime / MachineActiveTime));
            */
            System.out.println("Average number of jobs present = "
                    + (JobsInQueue / CheckFreq));
            
            System.out.println("Average queue length of monitored machine = " + mon.avgQueueLength());
            System.out.println("Utilization of monitored machine = " + mon.utilization());

            Simulation.stop();

            A.terminate();
            mon.terminate();

            SimulationProcess.mainResume();
        }
        catch (SimulationException e) {
        	
        }
        catch (RestartException e) {
        	
        }
    }

    public void await () {
        this.resumeProcess();
        SimulationProcess.mainSuspend();
    }
    
    public static ProcessQueue getIdleQ1() {
		return idleQ1;
	}
    
    public static ProcessQueueAlt<Machine3> getIdleQ2() {
		return idleQ2;
	}
    
    public static Queue getPreparationQueue() {
    	return prepQ;
    }
    
    public static Queue getOperationQueue() {
    	return opQ;
    }
    
    public static Queue getRecoveryQueue() {
    	return recQ;
    }
    
    public static Machine2 getOperationTheatre() {
    	return op;
    }
}
