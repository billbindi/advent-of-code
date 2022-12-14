package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class Day13_1 {

    private static final String FILENAME = "2017/day13_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        int severity = severity(lines);
        System.out.println(severity);
    }

    private static int severity(List<String> lines) {
        int severity = 0;
        for (String line : lines) {
            String[] parts = line.split(": ");
            int depth = Integer.parseInt(parts[0]);
            int range = Integer.parseInt(parts[1]);
            if (isCaught(depth, range)) {
                severity += (depth * range);
            }
        }
        return severity;
    }

    private static boolean isCaught(int depth, int range) {
        return depth % (2 * (range - 1)) == 0;
    }
}
