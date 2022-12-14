package advent2017;

import com.google.common.collect.Iterables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Day11_2 {

    private static final String FILENAME = "2017/day11_input.txt";

    public static void main(String[] args) throws IOException {
        String line = Iterables.getOnlyElement(Files.readAllLines(Paths.get(FILENAME)));
        int distance = maxDistance(Objects.requireNonNull(line));
        System.out.println(distance);
    }

    private static int maxDistance(String line) {
        // count totals
        int n = 0;
        int ne = 0;
        int nw = 0;
        int s = 0;
        int se = 0;
        int sw = 0;
        int maxSteps = 0;
        for (String dir : line.split(",")) {
            switch (dir) {
                case "n":
                    n++;
                    break;
                case "ne":
                    ne++;
                    break;
                case "nw":
                    nw++;
                    break;
                case "s":
                    s++;
                    break;
                case "se":
                    se++;
                    break;
                case "sw":
                    sw++;
                    break;
            }
            int distance = countDistance(n, ne, nw, s, se, sw);
            if (distance > maxSteps) {
                maxSteps = distance;
            }
        }

        return maxSteps;
    }

    private static int countDistance(int n, int ne, int nw, int s, int se, int sw) {
        // continue combining until no changes made
        boolean changesMade = true;
        while (changesMade) {
            changesMade = false;
            // simplifications
            if (n > 0 && se > 0) {
                int min = Math.min(n, se);
                n -= min;
                se -= min;
                ne += min;
                changesMade = true;
            }
            if (ne > 0 && s > 0) {
                int min = Math.min(ne, s);
                ne -= min;
                s -= min;
                se += min;
                changesMade = true;
            }
            if (se > 0 && sw > 0) {
                int min = Math.min(se, sw);
                se -= min;
                sw -= min;
                s += min;
                changesMade = true;
            }
            if (s > 0 && nw > 0) {
                int min = Math.min(s, nw);
                s -= min;
                nw -= min;
                sw += min;
                changesMade = true;
            }
            if (sw > 0 && n > 0) {
                int min = Math.min(sw, n);
                sw -= min;
                n -= min;
                nw += min;
                changesMade = true;
            }
            if (nw > 0 && ne > 0) {
                int min = Math.min(nw, ne);
                nw -= min;
                ne -= min;
                n += min;
                changesMade = true;
            }
        }
        // negations
        if (n > 0 && s > 0) {
            int min = Math.min(n, s);
            n -= min;
            s -= min;
        }
        if (ne > 0 && sw > 0) {
            int min = Math.min(ne, sw);
            ne -= min;
            sw -= min;
        }
        if (nw > 0 && se > 0) {
            int min = Math.min(nw, se);
            nw -= min;
            se -= min;
        }

        return n + ne + nw + s + se + sw;
    }
}
