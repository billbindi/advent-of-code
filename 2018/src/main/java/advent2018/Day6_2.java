package advent2018;

import util.Coordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day6_2 {

    private static final String FILENAME = "2018/day6_input.txt";

    // find a fudge factor such that area(FUDGE) == area(FUDGE + 1), and that is the limit
    private static final int BOUNDS_FUDGE_ROOM = 1;
    private static final int TOLERANCE = 10000;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        List<Coordinate> coordinates = parseCoordinates(lines);
        Bounds bounds = findBounds(coordinates);
        return Integer.toString(safeArea(bounds, coordinates));
    }

    private static List<Coordinate> parseCoordinates(List<String> lines) {
        List<Coordinate> coordinates = new ArrayList<>(lines.size());
        for (String line : lines) {
            String[] parts = line.split(",\\s*");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            coordinates.add(new Coordinate(x, y));
        }
        return coordinates;
    }

    private static Bounds findBounds(List<Coordinate> coordinates) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Coordinate coord : coordinates) {
            int x = coord.getX();
            int y = coord.getY();

            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }
        }

        return new Bounds(minX - BOUNDS_FUDGE_ROOM, maxX + BOUNDS_FUDGE_ROOM, minY - BOUNDS_FUDGE_ROOM, maxY + BOUNDS_FUDGE_ROOM);
    }

    private static int safeArea(Bounds bounds, List<Coordinate> coordinates) {
        int count = 0;
        for (int x = bounds.minX; x <= bounds.maxX; x++) {
            for (int y = bounds.minY; y <= bounds.maxY; y++) {
                Coordinate coord = new Coordinate(x, y);
                if (isSafe(coordinates, coord)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isSafe(List<Coordinate> coordinates, Coordinate target) {
        int totalDistance = coordinates.stream()
                .mapToInt(coord -> coord.manhattenDistanceToCoordinate(target))
                .sum();
        return totalDistance < TOLERANCE;
    }

    private static class Bounds {
        final int minX;
        final int maxX;
        final int minY;
        final int maxY;

        private Bounds(int minX, int maxX, int minY, int maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }
    }
}
