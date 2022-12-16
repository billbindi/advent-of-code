package advent2017;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day17_1 {

    private static final int INPUT = 394;
    private static final int LAST_VALUE = 2017;

    public static void main(String[] args) {
        int shortCircuit = spin();
        System.out.println(shortCircuit);
    }

    private static int spin() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(0);
        int count = 1;
        int index = 0;
        while (count <= LAST_VALUE) {
             index = (index + INPUT) % count;
             numbers.add(index + 1, count);
             index++;
             count++;
        }
        return numbers.get((numbers.indexOf(LAST_VALUE) + 1) % numbers.size());
    }
}
