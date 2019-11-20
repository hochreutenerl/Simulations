import org.javasim.*;

public class RecoveryRoom extends SimulationProcess
{
    
public RecoveryRoom ()
    {
	working = false;
	J = null;
    }

public void run ()
    {

	for (;;)
	{
	    working = true;
	    while (!Clinic.RecQ.isEmpty()){
	    	J = Clinic.RecQ.remove();		
	    	try {
	    		hold(J.RecoveryTime());
	    	}
	    	catch (SimulationException e){}
	    	catch (RestartException e){}		
	    	J.finished(); // last process to touch J. So time to read J:s internal memory
	    }
	    working = false;
	    Clinic.IRQ.add(this);
	    try {
	    	if (!Clinic.M.IsOperational()) {
	    		Clinic.M.Release();
	    		Clinic.M.activate();
	    	}
	    	passivate();
	    }
		catch (SimulationException e){}
	    catch (RestartException e){}
	}
    }

public void Block ()
    {
	operational = false;
    }
    
public void Release ()
    {
	operational = true;
    }
    
public boolean IsOperational ()
    {
	return operational;
    }
    
public boolean Processing ()
    {
	return working;
    }
    

private boolean operational;
private boolean working;
private Patient J;

};
