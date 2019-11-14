package org.javasim.examples.basic;

import org.javasim.RestartException;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class Machine2 extends SimulationProcess {
	private boolean operational;
    private boolean working;
    private Job J;
    
    public Machine2() {
        operational = true;
        working = false;
        J = null;
    }

    public void run () {
        double ActiveStart, ActiveEnd;

        while (!terminated()) {
        	Queue opQ = MachineShop.getOperationQueue();
        	//ProcessQueue idleQ = MachineShop.getIdleQ1();
        	working = true;

            while (!opQ.isEmpty()) {
                ActiveStart = currentTime();
                MachineShop.CheckFreq++;

                MachineShop.JobsInQueue += opQ.queueSize();
                J = opQ.dequeue();

                try {
                    hold(J.operationTime());
                }
                catch (SimulationException e) {
                }
                catch (RestartException e) {
                }

                ActiveEnd = currentTime();
                MachineShop.MachineActiveTime += ActiveEnd - ActiveStart;

                Queue recQ = MachineShop.getRecoveryQueue();
                boolean empty = recQ.isEmpty();
                recQ.enqueue(J);
                
                ProcessQueueAlt<Machine3> idleQ = MachineShop.getIdleQ2();
                
                //Start recovery machine if one is available and there are no clients in the recovery queue ahead of this
                if (!idleQ.isEmpty() && empty) {
                	Machine3 next = idleQ.getFirst();
                	
                	if (!next.processing() && next.isOperational()) {
                		try {
                    		next = idleQ.dequeue();
            				next.activate();
            			} catch (SimulationException e) {
            				e.printStackTrace();
            			} catch (RestartException e) {
            				e.printStackTrace();
            			}
                	}
                }
            }

            //idleQ.Enqueue(this);
            working = false;

            try
            {
                cancel();
            }
            catch (RestartException e)
            {
            }
        }
    }

    public void broken ()
    {
        operational = false;
    }

    public void fixed ()
    {
        operational = true;
    }

    public boolean isOperational ()
    {
        return operational;
    }

    public boolean processing ()
    {
        return working;
    }
}