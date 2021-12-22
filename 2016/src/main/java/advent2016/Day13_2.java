package advent2016;

import util.Coordinate;

import java.util.*;

public class Day13_2 {
    private final static int KEY = 1352;
    private final static int STEPS = 50;
    private final static Set<Coordinate> VISITED = new HashSet<>();

    public static void main(String[] args) {
        System.out.println(solve());
    }

    private static int solve() {
        Queue<PathElement> queue = new ArrayDeque<>();
        queue.add(new PathElement(new Coordinate(1, 1), 0));
        while (!queue.isEmpty()) {
            PathElement pathElement = queue.remove();
            Coordinate coord = pathElement.getCoord();
            int distance = pathElement.getDistance();
            if (distance > STEPS) {
                continue;
            }

            VISITED.add(coord);
            Coordinate left = coord.coordinateLeft();
            if (isValid(left)) {
                queue.add(new PathElement(left, distance + 1));
            }

            Coordinate right = coord.coordinateRight();
            if (isValid(right)) {
                queue.add(new PathElement(right, distance + 1));
            }

            Coordinate up = coord.coordinateUp();
            if (isValid(up)) {
                queue.add(new PathElement(up, distance + 1));
            }

            Coordinate down = coord.coordinateDown();
            if (isValid(down)) {
                queue.add(new PathElement(down, distance + 1));
            }
        }
        return VISITED.size();
    }

    private static boolean isValid(Coordinate coord) {
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
        Coordinate coord;
        int distance;

        public PathElement(Coordinate coord, int distance) {
            this.coord = coord;
            this.distance = distance;
        }

        public Coordinate getCoord() {
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
