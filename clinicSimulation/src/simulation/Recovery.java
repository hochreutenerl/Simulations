package simulation;

import org.javasim.RestartException;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class Recovery extends SimulationProcess {
    private boolean working;
    private Client J;
    
    public Recovery() {
        working = false;
        J = null;
    }

    public void run () {

        while (!terminated()) {
        	ProcessQueue<Recovery> idleQ = Clinic.getRecoveryIdleQ();
        	Operation ope = Clinic.getOperationTheatre();

            while (!ope.processing() && ope.hasClient()) {
            	working = true;
            	
                Clinic.CheckFreq++;

                J = ope.getClient();
                ope.unblock();
                try {
                    hold(J.recoveryTime());
                }
                catch (SimulationException e) {
                	
                }
                catch (RestartException e) {
                	
                }
                
                Clinic.ProcessedClients++;

                /*
                 * Introduce this new method because we usually rely upon the
                 * destructor of the object to do the work in C++.
                 */

                J.finished();
                
                working = false;
                
                try {
					ope.activate();
				} catch (SimulationException e) {
					e.printStackTrace();
				} catch (RestartException e) {
					e.printStackTrace();
				}
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