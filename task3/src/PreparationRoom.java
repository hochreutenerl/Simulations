import org.javasim.*;

public class PreparationRoom extends SimulationProcess {

    private boolean operational;
    private boolean working;
    private Patient J;

    public PreparationRoom() {
        working = false;
        J = null;
    }

    public void run() {
        for (; ; ) {
            if (!Clinic.EntQ.isEmpty()) {
                working = true;
                J = Clinic.EntQ.remove();
                try {
                    hold(J.PreparationTime());
//	    		Clinic.JobQ.add(J);
                    if (!Clinic.M.Processing()) {
                        Clinic.M.activate();
                    }
                    working = false;
                    Clinic.WaitQ.add(this);
                } catch (SimulationException | RestartException e) {
                }

            }
            try {
                passivate();
            } catch (RestartException e) {
            }
        }
    }

    public void Block() {
        operational = false;
    }

    public void Release() {
        operational = true;
    }

    public boolean IsOperational() {
        return operational;
    }

    public boolean Processing() {
        return working;
    }

    public Patient MyPatient() {
        Patient temp = J;
        J = null;
        return temp;
    }
}
