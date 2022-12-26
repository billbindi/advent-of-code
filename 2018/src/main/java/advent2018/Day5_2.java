package advent2018;

import com.google.common.collect.Iterables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Day5_2 {

    private static final String FILENAME = "2018/day5_input.txt";

    private static final int CASE_DIFFERENCE = Math.abs('a' - 'A');

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        String line = Objects.requireNonNull(Iterables.getOnlyElement(lines));
        int minSize = Integer.MAX_VALUE;
        for (char removal = 'a'; removal <= 'z'; removal++) {
            char uppercase = Character.toUpperCase(removal);
            if (line.indexOf(removal) != 0 || line.indexOf(uppercase) != 0) {
                String attempt = line.replaceAll(Character.toString(removal), "")
                        .replaceAll(Character.toString(uppercase), "");
                int size = polymerLength(attempt);
                if (size < minSize) {
                    minSize = size;
                }
            }
        }
        return Integer.toString(minSize);
    }

    private static int polymerLength(String line) {
        int count = line.length();
        while (true) {
            line = iterate(line);
            if (count == line.length()) {
                return count;
            }
            count = line.length();
        }
    }

    private static String iterate(String line) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < line.length() - 1; i++) {
            char first = line.charAt(i);
            char second = line.charAt(i + 1);
            if (Math.abs(first - second) == CASE_DIFFERENCE) {
                // skip an extra character
                i++;
            } else {
                result.append(first);
            }
        }

        // check last index separate
        if (Math.abs(line.charAt(line.length() - 2) - line.charAt(line.length() - 1)) != CASE_DIFFERENCE) {
            result.append(line.charAt(line.length() - 1));
        }
        return result.toString();
    }
}
