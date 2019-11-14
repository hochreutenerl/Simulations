import java.util.HashSet;
import java.util.Set;

public class Analyzer {
    private Analyzable subject;
    private Set<Integer> numbers = new HashSet<>();

    public Analyzer(Analyzable subject) {
        this.subject = subject;
    }

    public void run(int cycles) throws CycleCompletedException {
        for (int i = 0; i < cycles; i++) {
            int next = subject.nextValue();
            // System.out.println("On cycle " + i + " the number is " + next);
            if(numbers.contains(next)) {
                throw new CycleCompletedException("Cycle completed at cycle: " + i);
            } else {
                numbers.add(next);
            }
        }
        System.out.println("Run through " + cycles + " cycles");
    }
}
