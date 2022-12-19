package advent2016;

import util.PixelCoordinate;

import java.util.*;

public class Day13_1 {
    private final static int KEY = 1352;
    private final static PixelCoordinate DESTINATION = new PixelCoordinate(31, 39);
    private final static Set<PixelCoordinate> VISITED = new HashSet<>();

    public static void main(String[] args) {
        System.out.println(solve());
    }

    private static int solve() {
        Queue<PathElement> queue = new ArrayDeque<>();
        queue.add(new PathElement(new PixelCoordinate(1, 1), 0));
        while (!queue.isEmpty()) {
            PathElement pathElement = queue.remove();
            PixelCoordinate coord = pathElement.getCoord();
            int distance = pathElement.getDistance();
            if (coord.equals(DESTINATION)) {
                return distance;
            }

            VISITED.add(coord);
            PixelCoordinate left = coord.coordinateLeft();
            if (isValid(left)) {
                queue.add(new PathElement(left, distance + 1));
            }

            PixelCoordinate right = coord.coordinateRight();
            if (isValid(right)) {
                queue.add(new PathElement(right, distance + 1));
            }

            PixelCoordinate up = coord.coordinateUp();
            if (isValid(up)) {
                queue.add(new PathElement(up, distance + 1));
            }

            PixelCoordinate down = coord.coordinateDown();
            if (isValid(down)) {
                queue.add(new PathElement(down, distance + 1));
            }
        }
        return -1;
    }

    private static boolean isValid(PixelCoordinate coord) {
        int x = coord.getX();
        int y = coord.getY();
        return x >= 0 && y >= 0 &&
                !isWall(x ,y) &&
                !VISITED.contains(coord);
    }

    private static boolean isWall(int x, int y) {
        int sum = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + KEY;
        return Integer.bitCount(sum) % 2 == 1;
    }

    private static void printMaze(int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isWall(x, y)) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    private static class PathElement {
        PixelCoordinate coord;
        int distance;

        public PathElement(PixelCoordinate coord, int distance) {
            this.coord = coord;
            this.distance = distance;
        }

        public PixelCoordinate getCoord() {
            return coord;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PathElement that = (PathElement) o;
            return distance == that.distance && coord.equals(that.coord);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coord, distance);
        }
    }
}
