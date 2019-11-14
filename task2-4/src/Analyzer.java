import java.util.HashSet;
import java.util.Set;

public class Analyzer {
    private Analyzable subject;
    private Set<Integer> numbers = new HashSet<>();

    public Analyzer(Analyzable subject) {
        this.subject = subject;
    }

    public int getCycleLength(int cycles) {
        for (int i = 0; i < cycles; i++) {
            int next = subject.nextValue();
            if(numbers.contains(next)) {
                return i;
            } else {
                numbers.add(next);
            }
        }
        return cycles;
    }
}
