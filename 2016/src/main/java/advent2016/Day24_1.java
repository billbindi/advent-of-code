package advent2016;

import com.google.common.collect.Collections2;
import util.PixelCoordinate;
import util.PathElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day24_1 {

    private static final String FILENAME = "2016/day24_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        Grid grid = parse(content);
        return shortestPath(grid);
    }

    private static int shortestPath(Grid grid) {
        int[][] shortest = shortestInternalPaths(grid);
        List<Integer> points = new ArrayList<>(shortest.length - 1);
        for (int i = 1; i < shortest.length; i++) {
            points.add(i);
        }

        @SuppressWarnings("all")
        Collection<List<Integer>> permutations = Collections2.permutations(points);
        int minDist = Integer.MAX_VALUE;

        for (List<Integer> potential : permutations) {
            int dist = shortest[0][potential.get(0)];
            for (int i = 0; i < potential.size() - 1; i++) {
                dist += shortest[potential.get(i)][potential.get(i + 1)];
            }
            if (dist < minDist) {
                minDist = dist;
            }
        }
        return minDist;
    }

    private static int[][] shortestInternalPaths(Grid grid) {
        int points = grid.getNumbers().size();
        int[][] shortest = new int[points][];
        for (int i = 0; i < shortest.length; i++) {
            shortest[i] = shortestPathsForNum(grid, i, points);
        }
        return shortest;
    }

    private static int[] shortestPathsForNum(Grid grid, int num, int total) {
        Set<PixelCoordinate> visited = new HashSet<>();
        Queue<PathElement> queue = new ArrayDeque<>();
        queue.add(PathElement.start(grid.getNumberCoordinate(num)));
        int[] shortest = new int[total];
        int numFound = 0;
        while (numFound < total && !queue.isEmpty()) {
            PathElement pathElement = queue.remove();
            PixelCoordinate coord = pathElement.coord();
            int distance = pathElement.distance();
            if (visited.contains(coord)) {
                continue;
            }
            visited.add(coord);

            Cell cell = grid.cellAt(coord);
            if (cell.getNum() >= 0) {
                shortest[cell.getNum()] = distance;
                numFound++;
            }

            PixelCoordinate left = coord.coordinateLeft();
            if (grid.inBounds(left) && !grid.isWall(left)) {
                queue.add(new PathElement(left, distance + 1));
            }

            PixelCoordinate right = coord.coordinateRight();
            if (grid.inBounds(right) && !grid.isWall(right)) {
                queue.add(new PathElement(right, distance + 1));
            }

            PixelCoordinate up = coord.coordinateUp();
            if (grid.inBounds(up) && !grid.isWall(up)) {
                queue.add(new PathElement(up, distance + 1));
            }

            PixelCoordinate down = coord.coordinateDown();
            if (grid.inBounds(down) && !grid.isWall(down)) {
                queue.add(new PathElement(down, distance + 1));
            }
        }
        return shortest;
    }

    private static Grid parse(List<String> content) {
        int height = content.size();
        int width = content.get(0).length();
        Grid grid = new Grid(width, height);
        for (int y = 0; y < height; y++) {
            String row = content.get(y);
            for (int x = 0; x < width; x++) {
                char cell = row.charAt(x);
                if (cell == '#') {
                    grid.setCell(x, y, new Cell(true));
                } else if (cell == '.') {
                    grid.setCell(x, y, new Cell(false));
                } else {
                    int num = cell - '0';
                    grid.setCell(x, y, new Cell(false, num));
                    grid.setNumber(num, new PixelCoordinate(x, y));
                }
            }
        }
        return grid;
    }

    private static class Grid {
        Cell[][] grid;
        Map<Integer, PixelCoordinate> numbers = new HashMap<>();

        Grid(int width, int height) {
            grid = new Cell[height][width];
        }

        void setCell(int x, int y, Cell cell) {
            grid[y][x] = cell;
        }

        void setNumber(int num, PixelCoordinate coord) {
            numbers.put(num, coord);
        }

        Cell cellAt(PixelCoordinate coord) {
            return grid[coord.getY()][coord.getX()];
        }

        PixelCoordinate getNumberCoordinate(int num) {
            return numbers.get(num);
        }

        boolean isWall(PixelCoordinate coord) {
            return grid[coord.getY()][coord.getX()].isWall();
        }

        boolean inBounds(PixelCoordinate coord) {
            int x = coord.getX();
            int y = coord.getY();
            return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
        }

        public Map<Integer, PixelCoordinate> getNumbers() {
            return numbers;
        }

        @Override
        public String toString() {
            return "Grid{" +
                    "grid=" + Arrays.toString(grid) +
                    ", numbers=" + numbers +
                    '}';
        }
    }

    private static class Cell {
        boolean isWall;
        int num;

        public Cell(boolean isWall) {
            this(isWall, -1);
        }

        public Cell(boolean isWall, int num) {
            this.isWall = isWall;
            this.num = num;
        }

        public boolean isWall() {
            return isWall;
        }

        public int getNum() {
            return num;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return isWall == cell.isWall && num == cell.num;
        }

        @Override
        public int hashCode() {
            return Objects.hash(isWall, num);
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "isWall=" + isWall +
                    ", num=" + num +
                    '}';
        }
    }
}
