public class Main {
    public static void main(String[] args) throws CycleCompletedException {
        Lehmer l = new Lehmer(1);
        Analyzer a = new Analyzer(l);
        a.run(100000001);
    }
}
