public class LehmerGenerator implements Analyzable {

    protected int m = 100000001;
    protected int a = 23;
    protected int c = 0;
    protected int x;

    public LehmerGenerator(int seed) {
        x = seed;
    }

    public LehmerGenerator(int m, int a, int c, int seed) {
        this.m = m;
        this.a = a;
        this.c = c;
        x = seed;
    }

    private void next() {
        x = Math.floorMod(a * x + c, m);
    }

    @Override
    public int nextValue() {
        next();
        return x;
    }

    @Override
    public double nextDouble() {
        next();
        return new Double(x) / m;
    }

    public void setX(int x) {
        this.x = x;
    }

}
