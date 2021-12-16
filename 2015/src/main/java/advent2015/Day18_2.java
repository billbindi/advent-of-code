package advent2015;

import com.google.common.primitives.Booleans;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day18_2 {

    private static final String FILENAME = "day18_2_input.txt";

    private static final int SIZE = 100;
    private static final int NUM_ITERATIONS = 100;

    public static void main(String[] args) throws IOException {
        boolean[][] lights = parseFile();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            lights = iterate(lights);
        }
        System.out.println(countOn(lights));
    }

    private static boolean[][] iterate(boolean[][] lights) {
        boolean[][] next = new boolean[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCorner(i, j)) {
                    next[i][j] = true;
                } else {
                    next[i][j] = computeNext(lights, i, j);
                }
            }
        }
        return next;
    }

    private static boolean computeNext(boolean[][] lights, int i, int j) {
        int numNeighborsOn = 0;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if (inBounds(x, y) && !(x == i && y == j) && lights[x][y]) {
                    numNeighborsOn++;
                }
            }
        }
        if (lights[i][j]) {
            return numNeighborsOn == 2 || numNeighborsOn == 3;
        } else {
            return numNeighborsOn == 3;
        }
    }

    private static boolean isCorner(int i, int j) {
        return (i == 0 || i == SIZE - 1) && (j == 0 || j == SIZE - 1);
    }

    private static boolean inBounds(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    private static int countOn(boolean[][] lights) {
        int count = 0;
        for (boolean[] lightRow : lights) {
            count += Booleans.countTrue(lightRow);
        }
        return count;
    }

    private static boolean[][] parseFile() throws IOException {
        boolean[][] lights = new boolean[SIZE][SIZE];
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        for (int i = 0; i < SIZE; i++) {
            String line = lines.get(i);
            for (int j = 0; j < SIZE; j++) {
                if (line.charAt(j) == '#') {
                    lights[i][j] = true;
                }
            }
        }
        return lights;
    }
}
