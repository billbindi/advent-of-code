package advent2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day5 {

    private static final String FILENAME = "day5_input.txt";

    private static final Set<Character> VOWELS = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    private static final Set<String> BAD = new HashSet<>(Arrays.asList("ab", "cd", "pq", "xy"));

    public static void main(String[] args) throws IOException {
        Set<String> niceStrings = Files.readAllLines(Paths.get(FILENAME)).stream()
                .filter(Day5::isNice)
                .collect(Collectors.toSet());
        System.out.println(niceStrings.size());
    }

    private static boolean isNice(String input) {
        return has3Vowels(input) && hasDouble(input) && noBad(input);
    }

    private static boolean has3Vowels(String input) {
        int count = 0;
        for (char c : input.toCharArray()) {
            if (VOWELS.contains(c)) {
                count++;
            }
            if (count >= 3) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasDouble(String input) {
        for (int i = 0; i < input.length() - 1; i++) {
            if (input.charAt(i) == input.charAt(i+1)) {
                return true;
            }
        }
        return false;
    }

    private static boolean noBad(String input) {
        for (int i = 0; i < input.length() - 1; i++) {
            String test = input.charAt(i) + "" + input.charAt(i+1);
            if (BAD.contains(test)) {
                return false;
            }
        }
        return true;
    }
}
