package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Day4_1 {

    private static final String FILENAME = "2017/day4_input.txt";

    public static void main(String[] args) throws IOException {
        long total = Files.readAllLines(Paths.get(FILENAME)).stream()
                .filter(Day4_1::isValid)
                .count();
        System.out.println(total);
    }

    private static boolean isValid(String line) {
        Set<String> uniques = new HashSet<>();
        for (String word : line.split("\\s+")) {
            if (uniques.contains(word)) {
                return false;
            } else {
                uniques.add(word);
            }
        }
        return true;
    }
}
