package advent2019;

import com.google.common.math.IntMath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class Day10 {
    private static final String INPUT_FILENAME = "2019/day10_input.txt";
    private static final Path INPUT_PATH = Path.of(INPUT_FILENAME);

    public static void main(String[] args) throws IOException {
        System.out.println(part1(Files.lines(INPUT_PATH)));
        System.out.println(part2(Files.lines(INPUT_PATH)));
    }

    private static int part1(Stream<String> lines) {
        boolean[][] asteroidField = parseAsteroids(lines);
        return maxAsteroidsVisible(asteroidField);
    }

    private static int part2(Stream<String> lines) {
        return 0;
    }

    private static boolean[][] parseAsteroids(Stream<String> lines) {
        return lines.map(line -> {
            boolean[] asteroids = new boolean[line.length()];
            for (int i = 0; i < line.length(); i++) {
                asteroids[i] = line.charAt(i) == '#';
            }
            return asteroids;
        }).toArray(boolean[][]::new);
    }

    private static int maxAsteroidsVisible(boolean[][] asteroidField) {
        int maxVisible = Integer.MIN_VALUE;
        for (int y = 0; y < asteroidField.length; y++) {
            for (int x = 0; x < asteroidField[y].length; x++) {
                if (asteroidField[y][x]) {
                    int testVisible = countVisible(asteroidField, x, y);
                    if (testVisible > maxVisible) {
                        maxVisible = testVisible;
                    }
                }
            }
        }
        return maxVisible;
    }

    private static int countVisible(boolean[][] asteroidField, int a, int b) {
        int count = 0;
        for (int y = 0; y < asteroidField.length; y++) {
            for (int x = 0; x < asteroidField[y].length; x++) {
                if (asteroidField[y][x] && isVisible(asteroidField, a, b, x, y)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * For three asteroids {(a, b), (c, d), (e, f)}, (e, f) blocks line of sight from (a, b) to (c, d)
     * iff there exists some integer n such that (a, b) + n(x, y) has an asteroid, where the pair (x, y)
     * is (a + [(c - a)/gcd(c - a, d - b)], b + [(d - b)/gcd(c - a, d - b)]), and n(x, y) is in between
     * (a, b) and (c, d). Note that y coordinates in this problem count _down_, not up.
     */
    private static boolean isVisible(boolean[][] asteroidField, int a, int b, int c, int d) {
        int xDiff = c - a;
        int yDiff = d - b;
        int gcd = IntMath.gcd(Math.abs(xDiff), Math.abs(yDiff));
        if (gcd == 0) {
            // same coordinate, skip (asteroid is not visible to itself)
            return false;
        } else if (gcd > 1) {
            // don't need to check if coprime, always visible
            int xBase = (c - a) / gcd;
            int yBase = (d - b) / gcd;
            int x = a + xBase;
            int y = b + yBase;
            while (inBetween(x, a, c) || inBetween(y, b, d)) {
                if (asteroidField[y][x]) {
                    return false;
                }
                x += xBase;
                y += yBase;
            }
        }
        return true;
    }

    /** Check if {@code a} is in the interval (b, c) */
    private static boolean inBetween(int a, int b, int c) {
        return (a > b && a < c) || (a < b && a > c);
    }
}
