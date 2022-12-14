package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day13_2 {

    private static final String FILENAME = "2017/day13_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        int delay = minDelay(lines);
        System.out.println(delay);
    }

    private static int minDelay(List<String> lines) {
        int[] depths = new int[lines.size()];
        int[] ranges = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(": ");
            depths[i] = Integer.parseInt(parts[0]);
            ranges[i] = Integer.parseInt(parts[1]);
        }

        int delay = 1;
        while (!isValid(depths, ranges, delay)) {
            delay++;
        }
        return delay;
    }

    private static boolean isValid(int[] depths, int[] ranges, int delay) {
        for (int i = 0; i < depths.length; i++) {
            if (isCaught(depths[i], ranges[i], delay)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCaught(int depth, int range, int delay) {
        return (depth + delay) % (2 * (range - 1)) == 0;
    }
}
