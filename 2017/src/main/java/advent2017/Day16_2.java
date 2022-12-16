package advent2017;

import com.google.common.collect.Iterables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Day16_2 {

    private static final String FILENAME = "2017/day16_input.txt";

    private static final int ITERATIONS = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        String line = Iterables.getOnlyElement(Files.readAllLines(Paths.get(FILENAME)));
        String result = danceBillion(Objects.requireNonNull(line));
        System.out.println(result);
    }

    // N.B. The mapping below is obtained from running the full set of
    // dance instructions WITHOUT "partner" (pA/B) lines. The lemma used
    // here is that the order in which that instruction appears in the
    // dance does not matter, so we can put them all at the end if we
    // want, and since we are doing the dance an even number of times,
    // they then all immediately cancel themselves out. So, the dance is
    // run without that step included at all, then the final string from
    // that single iteration gives a result ("dgnaimjkebfhlocp") from
    // which we take the ending index of each character in order and use
    // that for our mapping, since each full run through of the steps
    // (without the partner steps) will always move around the indices
    // in exactly the same way.
    //
    // NOTE: A more generalized version of this could be run where you do
    // the dance ONCE, then find a mapping (simply just using "charAt",
    // for example) for each char.
    private static String danceBillion(String line) {
        // harcoded mapping
        int[] nextIndex = new int[]{3, 9, 14, 0, 8, 10, 1, 11, 4, 6, 7, 12, 5, 2, 13, 15};
        char[] dancers = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'};
        for (int i = 0; i < ITERATIONS; i++) {
            dancers = oneFullDance(dancers, nextIndex);
        }
        return new String(dancers);
    }

    private static char[] oneFullDance(char[] dancers, int[] mapping) {
        char[] next = new char[dancers.length];
        for (int i = 0; i < mapping.length; i++) {
            next[mapping[i]] = dancers[i];
        }
        return next;
    }
}
