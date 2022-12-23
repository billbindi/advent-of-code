package advent2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1_2 {

    private final static String FILENAME = "2018/day1_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        Set<Integer> frequencies = new HashSet<>();
        int frequency = 0;
        int index = 0;
        while (true) {
            if (frequencies.contains(frequency)) {
                return Integer.toString(frequency);
            }
            frequencies.add(frequency);
            frequency += Integer.parseInt(lines.get(index));
            index = (index + 1) % lines.size();
        }
    }
}
