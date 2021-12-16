package advent2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

public class Day5_2 {

    private static final String FILENAME = "day5_input.txt";

    public static void main(String[] args) throws IOException {
        Set<String> niceStrings = Files.readAllLines(Paths.get(FILENAME)).stream()
                .filter(Day5_2::isNice)
                .collect(Collectors.toSet());
        System.out.println(niceStrings.size());
    }

    private static boolean isNice(String input) {
        return hasDoublePair(input) && hasSplit(input);
    }

    private static boolean hasDoublePair(String input) {
        for (int i = 0; i < input.length() - 3; i++) {
            String curr = input.charAt(i) + "" + input.charAt(i+1);
            for (int j = i+2; j < input.length() - 1; j++) {
                String test = input.charAt(j) + "" + input.charAt(j+1);
                if (curr.equals(test)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasSplit(String input) {
        for (int i = 0; i < input.length() - 2; i++) {
            if (input.charAt(i) == input.charAt(i+2)) {
                return true;
            }
        }
        return false;
    }
}
