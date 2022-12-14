package advent2017;

import com.google.common.collect.Iterables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class Day10_1 {

    private static final String FILENAME = "2017/day10_input.txt";

    private static final int SIZE = 256;

    public static void main(String[] args) throws IOException {
        String line = Iterables.getOnlyElement(Files.readAllLines(Paths.get(FILENAME)));
        int[] lengths = parseLengths(Objects.requireNonNull(line));
        int[] numbers = makeDefault();
        rotate(numbers, lengths);
        System.out.println(numbers[0] * numbers[1]);
    }

    private static int[] parseLengths(String line) {
        return Arrays.stream(line.split(","))
                .map(String::strip)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static int[] makeDefault() {
        int[] numbers = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = i;
        }
        return numbers;
    }

    private static void rotate(int[] numbers, int[] lengths) {
        int index = 0;
        int skip = 0;
        for (int length : lengths) {
            rotate(numbers, index, index + length - 1);
            index += length + skip;
            skip++;
        }
    }

    // end may be larger than the array, that's fine, everything will be modded
    private static void rotate(int[] numbers, int start, int end) {
        int numFlips = (int) (Math.ceil((end - start) / 2.0));
        for (int i = 0; i < numFlips; i++) {
            int firstIndex = (start + i) % numbers.length;
            int secondIndex = (end - i) % numbers.length;
            int temp = numbers[firstIndex];
            numbers[firstIndex] = numbers[secondIndex];
            numbers[secondIndex] = temp;
        }
    }

}
