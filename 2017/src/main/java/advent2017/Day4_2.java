package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day4_2 {

    private static final String FILENAME = "2017/day4_input.txt";

    public static void main(String[] args) throws IOException {
        long total = Files.readAllLines(Paths.get(FILENAME)).stream()
                .filter(Day4_2::isValid)
                .count();
        System.out.println(total);
    }

    private static boolean isValid(String line) {
        Set<String> uniques = new HashSet<>();
        for (String word : line.split("\\s+")) {
            //sort first to handle anagrams
            String sorted = sort(word);
            if (uniques.contains(sorted)) {
                return false;
            } else {
                uniques.add(sorted);
            }
        }
        return true;
    }

    private static String sort(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
