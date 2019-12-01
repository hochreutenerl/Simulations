import org.javasim.*;
import org.javasim.streams.*;

import java.util.*;


public class Clinic extends SimulationProcess {
    public static Arrivals A = null;
    public static OperationRoom M = null;
    public static PreparationRoom[] PRooms = new PreparationRoom[10];
    public static RecoveryRoom[] RRooms = new RecoveryRoom[10];
    public static Reporter monitor = new Reporter(10);
    public static LinkedList<Patient> JobQ = new LinkedList<Patient>();
    public static LinkedList<Patient> RecQ = new LinkedList<Patient>();
    public static LinkedList<Patient> EntQ = new LinkedList<Patient>();
    public static LinkedList<SimulationProcess> WaitQ = new LinkedList<SimulationProcess>(); //preparations waiting operation
    public static LinkedList<SimulationProcess> IWQ = new LinkedList<SimulationProcess>(); // Idle preparations
    public static LinkedList<SimulationProcess> IRQ = new LinkedList<SimulationProcess>(); //Idle Recoveries

    public static double TotalResponseTime = 0.0;
    public static long TotalJobs = 0;
    public static long ProcessedJobs = 0;
    public static long JobsInQueue = 0;
    public static long CheckFreq = 0;
    public static double MachineActiveTime = 0.0;

    public static int PREPARATION_ROOMS = 4;
    public static int RECOVERY_ROOMS = 4;

    public Clinic(RandomStream arrival, RandomStream preparation, RandomStream recovery, int preparationUnits, int recoveryUnits) {
        RandomStream inter = arrival;

        RandomStream pre = preparation;

        RandomStream rec = recovery;


        RandomStream op = new ExponentialStream(20, 20);
        
        A = new Arrivals(inter, pre, op, rec);
        
        Clinic.M = new OperationRoom();
        
        PREPARATION_ROOMS = preparationUnits;
        RECOVERY_ROOMS = recoveryUnits;
        
        PRooms = new PreparationRoom[preparationUnits];
        RRooms = new RecoveryRoom[recoveryUnits];
        
        for (int i = 0; i < PREPARATION_ROOMS; i++) {
            PRooms[i] = new PreparationRoom();
            IWQ.add(PRooms[i]);
        }

        for (int i = 0; i < RECOVERY_ROOMS; i++) {
            RRooms[i] = new RecoveryRoom();
            IRQ.add(RRooms[i]);
        }

    }

    public void run() {
        try {

            monitor.activate();
            A.activate();

            Simulation.start();
            hold(1000);
            monitor.reset();
            for (int i = 0; i < 20; i++) {
                hold(1000);
                monitor.report();
                hold(1000);
                monitor.reset();
            }
            System.out.println("Total number of jobs present " + TotalJobs);
            System.out.println("Total number of jobs processed " + ProcessedJobs);
            System.out.println("Total response time of " + TotalResponseTime);
            System.out.println("Average response time = " + (TotalResponseTime / ProcessedJobs));
            System.out.println("Probability that machine is working = " + (MachineActiveTime / currentTime()));
            System.out.println("Average number of jobs present = " + (JobsInQueue / CheckFreq));


            Simulation.stop();

            A.terminate();
            Clinic.M.terminate();
            monitor.terminate();
            
            for (PreparationRoom prep : PRooms) {
            	prep.terminate();
            }
            for (RecoveryRoom rec : RRooms) {
            	rec.terminate();
            }

            SimulationProcess.mainResume();
        } catch (SimulationException | RestartException e) {
        }
    }

    public void Await() {
        this.resumeProcess();
        SimulationProcess.mainSuspend();
    }

}
