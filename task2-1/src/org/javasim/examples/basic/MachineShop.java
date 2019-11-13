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
	public static Queue JobQ = new Queue();
    private static ProcessQueue idleQ;
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
            Arrivals A = new Arrivals(8,8);
            
            //Create idle queue and add machines to it
            idleQ = new ProcessQueue();
            for (int i = 0; i < 3; i++) {
            	idleQ.Enqueue(new Machine());
            }
            
            //Monitor the first machine in the idle queue
            Monitor mon = new Monitor(1, (Machine) idleQ.getNextProcess(), JobQ);

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
            System.out
                    .println("Probability that machine is working = "
                            + ((MachineActiveTime - MachineFailedTime) / currentTime()));
            System.out.println("Probability that machine has failed = "
                    + (MachineFailedTime / MachineActiveTime));
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
    
    public static ProcessQueue getIdleQ() {
		return idleQ;
	}
}
