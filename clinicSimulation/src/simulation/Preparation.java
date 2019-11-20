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
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class Preparation extends SimulationProcess {
    private boolean working;
    private Client J;
    
    public Preparation() {
        working = false;
        J = null;
    }

    public void run () {

        while (!terminated())
        {
        	Queue prepQ = Clinic.getPreparationQueue();
        	ProcessQueue<Preparation> idleQ = Clinic.getPreaparationIdleQ();

            while (!prepQ.isEmpty()) {
            	working = true;
            	
                Clinic.CheckFreq++;

                Clinic.CLientsInQueue += prepQ.queueSize();
                J = prepQ.dequeue();

                try {
                    hold(J.preparationTime());
                }
                catch (SimulationException e) {
                }
                catch (RestartException e) {
                }
                
                //Add client to operation queue
                Queue opQ = Clinic.getOperationQueue();
                opQ.enqueue(J);
                
                //Activate operation if it isn't active
                Operation m = Clinic.getOperationTheatre();
                if (!m.processing()) {
                	try {
						m.activate();
					} catch (SimulationException e) {
						e.printStackTrace();
					} catch (RestartException e) {
						e.printStackTrace();
					}
                }
                
                working = false;
            }

            idleQ.enqueue(this);
            
            try {
                cancel();
            }
            catch (RestartException e) {
            }
        }
    }

    public boolean processing () {
        return working;
    }
}
