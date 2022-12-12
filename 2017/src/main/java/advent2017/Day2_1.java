package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day2_1 {

    private static final String FILENAME = "2017/day2_input.txt";

    public static void main(String[] args) throws IOException {
        int total = Files.readAllLines(Paths.get(FILENAME)).stream()
                .mapToInt(Day2_1::biggestDifference)
                .sum();
        System.out.println(total);
    }

    private static int biggestDifference(String line) {
        // who cares about efficiency here, right?
        int max = Arrays.stream(line.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElseThrow();
        int min = Arrays.stream(line.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .min()
                .orElseThrow();
        return max - min;
    }
}
