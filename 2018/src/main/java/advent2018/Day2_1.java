package advent2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2_1 {

    private static final String FILENAME = "2018/day2_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        int twoCount = 0;
        int threeCount = 0;
        for (String line : lines) {
            Map<Character, Integer> letterCounts = new HashMap<>();
            for (char character : line.toCharArray()) {
                if (!letterCounts.containsKey(character)) {
                    letterCounts.put(character, 0);
                }
                int newCount = letterCounts.get(character) + 1;
                letterCounts.put(character, newCount);
            }
            if (hasCount(letterCounts, 2)) {
                twoCount++;
            }
            if (hasCount(letterCounts, 3)) {
                threeCount++;
            }
        }
        return Integer.toString(twoCount * threeCount);
    }

    private static boolean hasCount(Map<Character, Integer> letterCounts, int expected) {
        for (Integer count : letterCounts.values()) {
            if (count == expected) {
                return true;
            }
        }
        return false;
    }
}
