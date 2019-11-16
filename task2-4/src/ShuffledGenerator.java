public class ShuffledGenerator extends LehmerGenerator implements Analyzable {
    LehmerGenerator a;
    LehmerGenerator b;
    int[] randomTable;
    int size;

    public ShuffledGenerator(int seed, int size) {
        super(seed);
        // a = new LehmerGenerator(Integer.MAX_VALUE, 49, 0, seed);
        a = new LehmerGenerator(seed);
        b = new LehmerGenerator(seed);
        randomTable = generateRandomTable(size);
        this.size = size;
    }

    private int[] generateRandomTable(int size){
        int[] randomList = new int[size];

        for(int i=1; i < size; i++){
            randomList[i] = a.nextValue();
        }

        return randomList;
    }

    @Override
    public int nextValue() {
        int index = Math.floorMod(b.nextValue(), size);
        int next = randomTable[index];
        randomTable[index] = a.nextValue();
        return next;
    }

    @Override
    public double nextDouble() {
        return Math.abs((double) nextValue() / m);
    }
}
