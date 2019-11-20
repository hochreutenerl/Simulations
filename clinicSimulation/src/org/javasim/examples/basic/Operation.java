package org.javasim.examples.basic;

import org.javasim.RestartException;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class Operation extends SimulationProcess {
	private boolean blocked;
    private boolean working;
    private Client J;
    
    public Operation() {
    	blocked = false;
        working = false;
        J = null;
    }

    public void run () {

        while (!terminated()) {
        	Queue opQ = Clinic.getOperationQueue();
        	System.out.println(Clinic.getRecoveryIdleQ().queueSize());
        	//ProcessQueue idleQ = MachineShop.getIdleQ1();

            while (!opQ.isEmpty() && !blocked) {
            	working = true;
            	
                Clinic.CheckFreq++;

                Clinic.CLientsInQueue += opQ.queueSize();
                J = opQ.dequeue();

                try {
                    hold(J.operationTime());
                }
                catch (SimulationException e) {
                }
                catch (RestartException e) {
                }
                
                ProcessQueueAlt<Recovery> idleQ = Clinic.getRecoveryIdleQ();
                
                //Start recovery if it's available
                if (!idleQ.isEmpty()) {
                	Recovery next = idleQ.getFirst();
                	
                	try {
                		next = idleQ.dequeue();
        				next.activate();
        			} catch (SimulationException e) {
        				e.printStackTrace();
        			} catch (RestartException e) {
        				e.printStackTrace();
        			}
                } else {
                	blocked = true;
                	working = false;
                	try {
						cancel();
					} catch (RestartException e) {
						e.printStackTrace();
					}
                }
                System.out.println("here?"+blocked);
                working = false;
            }

            try {
                cancel();
            }
            catch (RestartException e) {
            }
        }
    }

    public boolean processing ()
    {
        return working;
    }

	public void unblock() {
		blocked = false;
		
	}

	public Client getClient() {
		Client client = J;
		J = null;
		System.out.println(client.toString());
		return client;
	}

	public boolean hasClient() {
		if (J != null) {
			return true;
		}
		return false;
	}

	public boolean isBlocked() {
		return blocked;
	}
}