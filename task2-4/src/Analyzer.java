import java.util.*;

public class Analyzer {
    private Analyzable subject;
    private Set<Integer> numbers = new HashSet<>();

    public Analyzer(Analyzable subject) {
        this.subject = subject;
    }

    public void run(int cycles) {
        for (int i = 0; i < cycles; i++) {
            Integer next = subject.nextValue();
            numbers.add(next);
        }
    }

    public int uniqueNumbers() {
        return numbers.size();
    }

    public Integer highestNumber() {
        return Collections.max(numbers);
    }

    public Integer lowesetNumber() {
        return Collections.min(numbers);
    }

    public double averageNumber() {
        int total = 0;
        for (Integer i : numbers) {
            total += i;
        }
        return (double) total / numbers.size();
    }

    public double variance() {
        double mean = averageNumber();
        double s = 0;
        for (Integer i : numbers) {
            s += Math.pow(i - mean, 2);
        }
        return s / numbers.size();
    }
}
