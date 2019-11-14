public class LehmerGenerator implements Analyzable {

    protected int m = 100000001;
    protected int a = 23;
    protected int c = 0;
    protected int x;

    public LehmerGenerator(int seed) {
        x = seed;
    }

    public int nextValue() {
        x = (a * x + c) % m;
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
