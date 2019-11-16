public class Main {
    public static void main(String[] args) {
        // Select any number as a seed between 0 and 100000000
        // The programme will very likely throw an exception

        int seed = 4563685;
        int maxCycles = 100000000;

        LehmerGenerator l = new LehmerGenerator(seed);
        Analyzer al = new Analyzer(l);
        al.run(maxCycles);
        System.out.println("Lehmer unique numbers:" +  al.uniqueNumbers());
        System.out.println("Lehmer highest number:" +  al.highestNumber());
        System.out.println("Lehmer lowest number:" +  al.lowesetNumber());
        System.out.println("Lehmer average number:" +  al.averageNumber());
        System.out.println("Lehmer variance:" +  al.variance());

        System.out.println("***");

        ShuffledGenerator s = new ShuffledGenerator(seed, 1000);
        Analyzer as = new Analyzer(s);
        as.run(maxCycles);
        System.out.println("Shuffled unique numbers:" +  as.uniqueNumbers());
        System.out.println("Shuffled highest number:" +  as.highestNumber());
        System.out.println("Shuffled lowest number:" +  as.lowesetNumber());
        System.out.println("Shuffled average number:" +  as.averageNumber());
        System.out.println("Shuffled variance:" +  as.variance());
    }
}
