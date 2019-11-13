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
import org.javasim.Scheduler;
import org.javasim.SimulationException;

public class Job {
    private double ResponseTime;
    private double ArrivalTime;
    private double ServiceTime;
    
    public Job(double serviceTime) {
		ServiceTime = serviceTime;
        
        boolean empty = false;

        ResponseTime = 0.0;
        ArrivalTime = Scheduler.currentTime();

        empty = MachineShop.JobQ.isEmpty();
        MachineShop.JobQ.enqueue(this);
        MachineShop.TotalJobs++;
        
        ProcessQueue idleQ = MachineShop.getIdleQ();
        
        //Start next machine if one is available and there are no jobs in the job queue ahead of this
        if (!idleQ.IsEmpty() && empty) {
        	Machine next = (Machine) idleQ.getNextProcess();
        	
        	if (!next.processing() && next.isOperational()) {
        		try {
            		next = (Machine) idleQ.Dequeue();
    				next.activate();
    			} catch (SimulationException e) {
    				e.printStackTrace();
    			} catch (RestartException e) {
    				e.printStackTrace();
    			}
        	}
        }
    }

    public void finished () {
        ResponseTime = Scheduler.currentTime() - ArrivalTime;
        MachineShop.TotalResponseTime += ResponseTime;
    }

    public double serviceTime () {
        return ServiceTime;
    }
}
