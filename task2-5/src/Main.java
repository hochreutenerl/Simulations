public class Main {
    public static void main(String[] args) {
        // Select any number as a seed between 0 and 100000000
        // The programme will very likely throw an exception

        int seed = 4563685;
        int seed2 = 32765422;
        int maxCycles = 100000000;

        LehmerGenerator l = new LehmerGenerator(seed);
        Analyzer al = new Analyzer(l);
        al.run(maxCycles);
        al.printAnalysis("Lehmer");

        System.out.println("***");

        LehmerGenerator lr = new LehmerGenerator(seed);
        Analyzer alr = new Analyzer(lr);
        alr.run(maxCycles, 0.01);
        alr.printAnalysis("Lehmer reduced");

        System.out.println("***");

        ShuffledGenerator s = new ShuffledGenerator(seed, seed2, 1000);
        Analyzer as = new Analyzer(s);
        as.run(maxCycles);
        as.printAnalysis("Shuffled");

        System.out.println("***");

        ShuffledGenerator sr = new ShuffledGenerator(seed, seed2, 1000);
        Analyzer asr = new Analyzer(sr);
        asr.run(maxCycles, 0.01);
        asr.printAnalysis("Shuffled reduced");
    }
}
