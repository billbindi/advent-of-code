package advent2017;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day17_2 {

    private static final int INPUT = 394;
    private static final int LAST_VALUE = 50_000_000;

    public static void main(String[] args) {
        int shortCircuit = spin();
        System.out.println(shortCircuit);
    }

    // N.B. Nothing can be written to the left of 0, so 0 is always the first element
    // of the buffer. Therefore, we are only ever looking for element 1 at the end.
    private static int spin() {
        int index = 0;
        int stop = 1;
        for (int count = 1; count <= LAST_VALUE; count++) {
             index = ((index + INPUT) % count) + 1;
             if (index == 1) {
                 stop = count;
             }
        }
        return stop;
    }
}
