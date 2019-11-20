public class ShuffledGenerator extends LehmerGenerator implements Analyzable {
    LehmerGenerator a;
    LehmerGenerator b;
    int[] randomTable;
    int size;
    double previous;

    public ShuffledGenerator(int seed, int seed2, int size) {
        super(seed);
        // a = new LehmerGenerator(Integer.MAX_VALUE, 49, 0, seed);
        a = new LehmerGenerator(seed);
        b = new LehmerGenerator(seed2);
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
        previous = new Double(next) / m;
        randomTable[index] = a.nextValue();
        return next;
    }

    @Override
    public double nextDouble() {
        return new Double(nextValue()) / m;
    }
    
    @Override
    public double getX() {
    	return previous;
    }
}
