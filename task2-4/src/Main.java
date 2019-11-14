public class Main {
    public static void main(String[] args) throws CycleCompletedException {
        // Select any number as a seed between 0 and 100000000
        // The programme will very likely throw an exception
        LehmerGenerator l = new LehmerGenerator(1);
        Analyzer a = new Analyzer(l);
        a.run(100000000);
    }
}
