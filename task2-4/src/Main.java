public class Main {
    public static void main(String[] args) {
        // Select any number as a seed between 0 and 100000000
        // The programme will very likely throw an exception

        int seed = 4563685;
        int maxCycles = 100000000;

        LehmerGenerator l = new LehmerGenerator(seed);
        Analyzer al = new Analyzer(l);
        System.out.println("Lehmer cycle length:" +  al.getCycleLength(maxCycles));

        ShuffledGenerator s = new ShuffledGenerator(seed, 1000);
        Analyzer as = new Analyzer(s);
        System.out.println("Shuffled cycle length:" +  as.getCycleLength(maxCycles));
    }
}
