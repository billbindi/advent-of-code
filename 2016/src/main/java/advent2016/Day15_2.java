package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15_2 {
    private static final String DISC_REGEX = "Disc #(?<disc>\\d+) has (?<pos>\\d+) positions; at time=0, it is at position (?<start>\\d+).";
    private final static Pattern DISC_PATTERN = Pattern.compile(DISC_REGEX);

    private static final String FILENAME = "2016/day15_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        List<Disc> discs = parseDiscs(content);
        for (int t = 0; t < Integer.MAX_VALUE; t++) {
            boolean solution = true;
            for (Disc disc : discs) {
                if (!disc.isHole(t)) {
                    solution = false;
                    break;
                }
            }
            if (solution) {
                return t;
            }
        }
        return -1;
    }

    private static List<Disc> parseDiscs(List<String> content) {
        List<Disc> discs = new ArrayList<>(content.size());
        for (String line : content) {
            Matcher m = DISC_PATTERN.matcher(line);
            if (m.find()) {
                int discNum = Integer.parseInt(m.group("disc"));
                int numPositions = Integer.parseInt(m.group("pos"));
                int startPosition = Integer.parseInt(m.group("start"));
                Disc disc  = new Disc(discNum, numPositions, startPosition);
                discs.add(disc);
            } else {
                throw new IllegalArgumentException("Could not parse disc from line: " + line);
            }
        }

        // add new bottom disc with 11 positions starting at position 0
        discs.add(new Disc(content.size() + 1, 11, 0));
        return discs;
    }

    private static class Disc {
        int disc;
        int numPositions;
        int startPosition;

        public Disc(int disc, int numPositions, int startPosition) {
            this.disc = disc;
            this.numPositions = numPositions;
            this.startPosition = startPosition;
        }

        public boolean isHole(int t) {
            return (t + disc + startPosition) % numPositions == 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Disc disc = (Disc) o;
            return this.disc == disc.disc && numPositions == disc.numPositions && startPosition == disc.startPosition;
        }

        @Override
        public int hashCode() {
            return Objects.hash(disc, numPositions, startPosition);
        }

        @Override
        public String toString() {
            return "Disc{" +
                    "disk=" + disc +
                    ", numPositions=" + numPositions +
                    ", startPosition=" + startPosition +
                    '}';
        }
    }
}
