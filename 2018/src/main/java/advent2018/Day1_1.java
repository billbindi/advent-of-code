package advent2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day1_1 {

    private final static String FILENAME = "2018/day1_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        int sum = lines.stream()
                .mapToInt(Integer::parseInt)
                .sum();
        return Integer.toString(sum);
    }
}
