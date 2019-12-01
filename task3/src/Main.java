import org.javasim.streams.ExponentialStream;
import org.javasim.streams.RandomStream;

public class Main {

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-help")) {
                System.out.println("Usage: Main [-breaks] [-help]");
                System.exit(0);
            }
        }
        
        RandomStream inter = new ExponentialStream(25);
        // RandomStream inter = new ExponentialStream(22.5);
        // RandomStream inter = new UniformStream(20, 30);
        // RandomStream inter = new UniformStream(20, 25);


        RandomStream pre = new ExponentialStream(40, 10);
        // RandomStream pre = new UniformStream(30, 50, 10);

        RandomStream rec = new ExponentialStream(40, 30);
        // RandomStream rec = new UniformStream(30, 50, 30);

        Clinic m = new Clinic(inter, pre, rec, 4, 4);

        m.Await();

        System.exit(0);
    }

}
