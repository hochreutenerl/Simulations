import java.util.*;

public class Analyzer {
    private Analyzable subject;
    private Set<Double> numbers = new HashSet<>();

    public Analyzer(Analyzable subject) {
        this.subject = subject;
    }

    public void run(int cycles) {
        run(cycles, 1);
    }

    public void run(int cycles, double upper_limit) {
        for (int i = 0; i < cycles; i++) {
        	double xi = subject.getX();
            Double next = subject.nextDouble();
            if(xi < upper_limit) {
                numbers.add(next);
            }
        }
    }

    public int uniqueNumbers() {
        return numbers.size();
    }

    public Double highestNumber() {
        Double max = 0.0;
        for (Double i : numbers) {
            max = Double.max(i, max);
        }
        return max;
    }

    public Double lowesetNumber() {
        Double min = Double.MAX_VALUE;
        for (Double i : numbers) {
            min = Double.min(i, min);
        }
        return min;
    }

    public double averageNumber() {
        int total = 0;
        for (Double i : numbers) {
            total += i;
        }
        return (double) total / numbers.size();
    }

    public double variance() {
        double mean = averageNumber();
        double s = 0;
        for (Double i : numbers) {
            s += Math.pow(i - mean, 2);
        }
        return s / numbers.size();
    }

    public void printAnalysis(String name) {
        System.out.println(name + " unique numbers: " +  uniqueNumbers());
        System.out.println(name + " highest number: " + highestNumber());
        System.out.println(name + " lowest number: " +  lowesetNumber());
        System.out.println(name + " average number: " +  averageNumber());
        System.out.println(name + " variance: " +  variance());
    }
}
