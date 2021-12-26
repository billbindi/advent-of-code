package advent2016;

import util.Interval;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day20 {
    private static final long IP_LOW = 0;
    private static final long IP_HIGH = 4294967295L;

    private static final String FILENAME = "2016/day20_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static long solve(List<String> content) {
        List<Interval> valids = List.of(new Interval(IP_LOW, IP_HIGH));
        for (String range : content) {
            Interval invalid = parseLine(range);
            List<Interval> newValids = new ArrayList<>(valids.size());
            for (Interval valid : valids) {
                newValids.addAll(valid.split(invalid));
            }
            valids = newValids;
        }
        System.out.println(totalSize(valids));
        return valids.get(0).getLow();
    }

    private static Interval parseLine(String range) {
        String[] parts = range.split("-");
        long low = Long.parseLong(parts[0]);
        long high = Long.parseLong(parts[1]);
        return new Interval(low, high);
    }

    private static long totalSize(List<Interval> valids) {
        long total = 0;
        for (Interval interval : valids) {
            total += interval.size();
        }
        return total;
    }
}
