package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21_1 {

    private static final String FILENAME = "2017/day21_input.txt";

    private static final char[][] STARTING_GRID = new char[][]{
            {'.', '#', '.'},
            {'.', '.', '#'},
            {'#', '#', '#'}
    };

    private static final int ITERATIONS = 5;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        Map<Grid, char[][]> mapping = preprocess(lines);
        int pixels = computePixels(mapping);
        System.out.println(pixels);
    }

    private static Map<Grid, char[][]> preprocess(List<String> lines) {
        Map<Grid, char[][]> mapping = new HashMap<>(lines.size() * 8);
        for (String line : lines) {
            String[] parts = line.split("=>");
            char[][] input = parse(parts[0].trim());
            char[][] output = parse(parts[1].trim());

            char[][] rotate1 = rotate1(input);
            char[][] rotate2 = rotate2(input);
            char[][] rotate3 = rotate3(input);
            char[][] flipped0 = flip(input);
            char[][] flipped1 = flip(rotate1);
            char[][] flipped2 = flip(rotate2);
            char[][] flipped3 = flip(rotate3);

            mapping.put(new Grid(input), output);
            mapping.put(new Grid(rotate1), output);
            mapping.put(new Grid(rotate2), output);
            mapping.put(new Grid(rotate3), output);
            mapping.put(new Grid(flipped0), output);
            mapping.put(new Grid(flipped1), output);
            mapping.put(new Grid(flipped2), output);
            mapping.put(new Grid(flipped3), output);
        }
        return mapping;
    }

    private static char[][] rotate1(char[][] input) {
        char[][] rotate1 = new char[input.length][input.length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                rotate1[i][j] = input[input.length - j - 1][i];
            }
        }
        return rotate1;
    }

    private static char[][] rotate2(char[][] input) {
        char[][] rotate2 = new char[input.length][input.length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                rotate2[i][j] = input[input.length - i - 1][input.length - j - 1];
            }
        }
        return rotate2;
    }

    private static char[][] rotate3(char[][] input) {
        char[][] rotate3 = new char[input.length][input.length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                rotate3[i][j] = input[j][input.length - i - 1];
            }
        }
        return rotate3;
    }

    private static char[][] flip(char[][] input) {
        char[][] flipped = new char[input.length][input.length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                flipped[i][j] = input[i][input.length - j - 1];
            }
        }
        return flipped;
    }

    private static char[][] parse(String code) {
        String[] parts = code.split("/");
        char[][] parsed = new char[parts.length][];
        for (int i = 0; i < parts.length; i++) {
            parsed[i] = parts[i].toCharArray();
        }
        return parsed;
    }

    private static int computePixels(Map<Grid, char[][]> mapping) {
        char[][] grid = STARTING_GRID;
        for (int i = 0; i < ITERATIONS; i++) {
            grid = iterate(grid, mapping);
        }
        return countOn(grid);
    }

    private static char[][] iterate(char[][] grid, Map<Grid, char[][]> mapping) {
        int groupSize;
        int newSize;
        if (grid.length % 2 == 0) {
            groupSize = 2;
            newSize = (grid.length / 2) * 3;
        } else {
            groupSize = 3;
            newSize = (grid.length / 3) * 4;
        }
        char[][] newGrid = new char[newSize][newSize];

        for (int i = 0; i < grid.length / groupSize; i++) {
            for (int j = 0; j < grid.length / groupSize; j++) {
                char[][] section = getSection(grid, i, j, groupSize);
                char[][] output = mapping.get(new Grid(section));
                fillNewGrid(newGrid, output, i, j);
            }
        }
        return newGrid;
    }

    private static char[][] getSection(char[][] grid, int x, int y, int groupSize) {
        char[][] section = new char[groupSize][groupSize];
        for (int i = 0; i < groupSize; i++) {
            System.arraycopy(grid[(x * groupSize) + i], (y * groupSize), section[i], 0, groupSize);
        }
        return section;
    }

    private static void fillNewGrid(char[][] newGrid, char[][] section, int x, int y) {
        int sectionSize = section.length;
        int startI = x * sectionSize;
        int startJ = y * sectionSize;
        for (int i = 0; i < sectionSize; i++) {
            System.arraycopy(section[i], 0, newGrid[i + startI], startJ, sectionSize);
        }
    }

    private static int countOn(char[][] grid) {
        int count = 0;
        for (char[] row : grid) {
            for (char c : row) {
                if (c == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    private static class Grid {
        final char[][] grid;

        private Grid(char[][] grid) {
            this.grid = grid;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Grid grid1 = (Grid) o;
            return Arrays.deepEquals(grid, grid1.grid);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(grid);
        }
    }
}
