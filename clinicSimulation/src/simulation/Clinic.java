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

package simulation;

import org.javasim.RestartException;
import org.javasim.Simulation;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class Clinic extends SimulationProcess {
	private static Queue prepQ = new Queue();
	private static Queue opQ = new Queue();
    private static ProcessQueue<Preparation> preparationIdleQ;
    private static ProcessQueue<Recovery> recoveryIdleQ;
    private static Operation op;
    public static double TotalResponseTime = 0.0;
    public static long totalClients = 0;
    public static long ProcessedClients = 0;
    public static long CLientsInQueue = 0;
    public static long CheckFreq = 0;
    
    public Clinic(boolean isBreaks) {
        TotalResponseTime = 0.0;
        totalClients = 0;
        ProcessedClients = 0;
        CLientsInQueue = 0;
        CheckFreq = 0;
    }

    public void run () {
        try {
            Arrivals A = new Arrivals(25, 40, 20, 40);
            
            //Preparation machines
            preparationIdleQ = new ProcessQueue<Preparation>();
            for (int i = 0; i < 3; i++) {
            	preparationIdleQ.enqueue(new Preparation());
            }
            
            //Operation
            op = new Operation();
            
            //Recoveries
            recoveryIdleQ = new ProcessQueue<Recovery>();
            for (int i = 0; i < 5; i++) {
            	recoveryIdleQ.enqueue(new Recovery());
            }
            
            //Monitor the operation and operation queue
            Monitor mon = new Monitor(100, op, opQ);

            mon.activate();
            A.activate();

            Simulation.start();

            while (Clinic.ProcessedClients < 1600)
                hold(1000);

            System.out.println("Current time "+currentTime());
            System.out.println("Total number of jobs present " + totalClients);
            System.out.println("Total number of jobs processed "
                    + ProcessedClients);
            System.out.println("Total response time of " + TotalResponseTime);
            System.out.println("Average response time = "
                    + (TotalResponseTime / ProcessedClients));
            System.out.println("Average number of jobs present = "
                    + (CLientsInQueue / CheckFreq));
            
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
    
    public static ProcessQueue<Preparation> getPreaparationIdleQ() {
		return preparationIdleQ;
	}
    
    public static ProcessQueue<Recovery> getRecoveryIdleQ() {
		return recoveryIdleQ;
	}
    
    public static Queue getPreparationQueue() {
    	return prepQ;
    }
    
    public static Queue getOperationQueue() {
    	return opQ;
    }
    
    public static Operation getOperationTheatre() {
    	return op;
    }
}
