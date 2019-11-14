public class LehmerGenerator implements Analyzable {

    private int m = 100000001;
    private int a = 23;
    private int c = 0;
    private int x;

    public LehmerGenerator(int seed) {
        x = seed;
    }

    public int nextValue() {
        x = (a * x + c) % m;
        return x;
    }
}
