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
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class Machine1 extends SimulationProcess {
	private boolean operational;
    private boolean working;
    private Job J;
    
    public Machine1() {
        operational = true;
        working = false;
        J = null;
    }

    public void run () {
        double ActiveStart, ActiveEnd;

        while (!terminated())
        {
        	Queue prepQ = MachineShop.getPreparationQueue();
        	ProcessQueue idleQ = MachineShop.getIdleQ1();
        	working = true;

            while (!prepQ.isEmpty())
            {
                ActiveStart = currentTime();
                MachineShop.CheckFreq++;

                MachineShop.JobsInQueue += prepQ.queueSize();
                J = prepQ.dequeue();

                try {
                    hold(J.preparationTime());
                }
                catch (SimulationException e) {
                }
                catch (RestartException e) {
                }

                ActiveEnd = currentTime();
                MachineShop.MachineActiveTime += ActiveEnd - ActiveStart;
                
                //Add client to operation queue
                Queue opQ = MachineShop.getOperationQueue();
                opQ.enqueue(J);
                
                //Activate operation machine if it isn't active
                Machine2 m = MachineShop.getOperationTheatre();
                if (!m.processing()) {
                	try {
						m.activate();
					} catch (SimulationException e) {
						e.printStackTrace();
					} catch (RestartException e) {
						e.printStackTrace();
					}
                }
            }

            idleQ.Enqueue(this);
            working = false;

            try {
                cancel();
            }
            catch (RestartException e) {
            }
        }
    }

    public void broken () {
        operational = false;
    }

    public void fixed () {
        operational = true;
    }

    public boolean isOperational () {
        return operational;
    }

    public boolean processing () {
        return working;
    }
}
