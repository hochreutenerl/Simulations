
import org.javasim.*;
import org.javasim.streams.*;

import java.io.IOException;

public class Arrivals extends SimulationProcess {
    boolean empty = false;
    private RandomStream InterArrivalTime;
    private RandomStream PreparationTime;
    private RandomStream OperationTime;
    private RandomStream RecoveryTime;

    public Arrivals(RandomStream inter, RandomStream pre, RandomStream op, RandomStream rec) {
        InterArrivalTime = inter;
        PreparationTime = pre;
        OperationTime = op;
        RecoveryTime = rec;
    }

    public void run() {
        for (; ; ) {
            try {
                Patient work = new Patient(PreparationTime.getNumber(), OperationTime.getNumber(), RecoveryTime.getNumber());
                empty = Clinic.EntQ.isEmpty();
                Clinic.EntQ.add(work);
                Clinic.TotalJobs++;

                if (empty && !Clinic.IWQ.isEmpty()) {
                    try {
                        PreparationRoom waitroom = (PreparationRoom) Clinic.IWQ.remove();
                        waitroom.activate();
                    } catch (SimulationException | RestartException e) {
                    }
                }
                hold(InterArrivalTime.getNumber());
            } catch (SimulationException | RestartException | IOException e) {
            }
        }
    }
}
