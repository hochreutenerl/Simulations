package org.javasim.examples.basic;

import org.javasim.RestartException;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class Machine3 extends SimulationProcess {
	private boolean operational;
    private boolean working;
    private Job J;
    
    public Machine3() {
        operational = true;
        working = false;
        J = null;
    }

    public void run () {
        double ActiveStart, ActiveEnd;

        while (!terminated())
        {
        	ProcessQueueAlt<Machine3> idleQ = MachineShop.getIdleQ2();
        	Queue recQ = MachineShop.getRecoveryQueue();
        	working = true;

            while (!recQ.isEmpty())
            {
                ActiveStart = currentTime();
                MachineShop.CheckFreq++;

                MachineShop.JobsInQueue += recQ.queueSize();
                J = recQ.dequeue();

                try
                {
                    hold(J.recoveryTime());
                }
                catch (SimulationException e)
                {
                }
                catch (RestartException e)
                {
                }

                ActiveEnd = currentTime();
                MachineShop.MachineActiveTime += ActiveEnd - ActiveStart;
                MachineShop.ProcessedJobs++;

                /*
                 * Introduce this new method because we usually rely upon the
                 * destructor of the object to do the work in C++.
                 */

                J.finished();
            }

            idleQ.enqueue(this);
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