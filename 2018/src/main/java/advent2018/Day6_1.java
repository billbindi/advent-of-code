package advent2018;

import util.Coordinate;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day6_1 {

    private static final String FILENAME = "2018/day6_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        List<Coordinate> coordinates = parseCoordinates(lines);
        Bounds bounds = findBounds(coordinates);
        return Integer.toString(maxFiniteArea(bounds, coordinates));
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

        // N.B. The key insight here is that if you look at the bounding square
        // that is one bigger in all dimensions than the points, a point will
        // be closest iff it that point has an infinite area of points.
        return new Bounds(minX - 1, maxX + 1, minY - 1, maxY + 1);
    }

    private static int maxFiniteArea(Bounds bounds, List<Coordinate> coordinates) {
        Map<Coordinate, Coordinate> closests = findClosests(bounds, coordinates);
        Map<Coordinate, Integer> closestSizes = computeClosestSizes(closests);

        Set<Coordinate> infinites = findInfinites(bounds, closests);
        for (Coordinate coord : infinites) {
            closestSizes.remove(coord);
        }

        return closestSizes.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();
    }

    private static Map<Coordinate, Coordinate> findClosests(Bounds bounds, List<Coordinate> coordinates) {
        Map<Coordinate, Coordinate> closests = new HashMap<>();
        for (int x = bounds.minX; x <= bounds.maxX; x++) {
            for (int y = bounds.minY; y <= bounds.maxY; y++) {
                Coordinate coord = new Coordinate(x, y);
                Coordinate closest = findClosest(coordinates, coord);
                if (closest != null) {
                    closests.put(coord, closest);
                }
            }
        }
        return closests;
    }

    // could be done in a more cool way with a 2D kd-tree, but there aren't enough points
    // to make it worth the effort.
    @Nullable
    private static Coordinate findClosest(List<Coordinate> coordinates, Coordinate target) {
        coordinates.sort(Comparator.comparingInt(coordinate -> coordinate.manhattanDistanceToCoordinate(target)));

        // check for ties
        Coordinate first = coordinates.get(0);
        Coordinate second = coordinates.get(1);
        if (first.manhattanDistanceToCoordinate(target) == second.manhattanDistanceToCoordinate(target)) {
            return null;
        } else {
            return first;
        }
    }

    private static Map<Coordinate, Integer> computeClosestSizes(Map<Coordinate, Coordinate> closests) {
        Map<Coordinate, Integer> sizes = new HashMap<>();
        for (Coordinate closest : closests.values()) {
            if (!sizes.containsKey(closest)) {
                sizes.put(closest, 0);
            }
            sizes.put(closest, sizes.get(closest) + 1);
        }
        return sizes;
    }

    private static Set<Coordinate> findInfinites(Bounds bounds, Map<Coordinate, Coordinate> closest) {
        Set<Coordinate> infinites = new HashSet<>();

        // top and bottom of bounds
        for (int x = bounds.minX; x <= bounds.maxX; x++) {
            Coordinate topEdge = new Coordinate(x, bounds.minY);
            Coordinate bottomEdge = new Coordinate(x, bounds.maxY);
            infinites.add(closest.get(topEdge));
            infinites.add(closest.get(bottomEdge));
        }

        // left and right of bounds
        for (int y = bounds.minY; y <= bounds.maxY; y++) {
            Coordinate leftEdge = new Coordinate(bounds.minX, y);
            Coordinate rightEdge = new Coordinate(bounds.maxX, y);
            infinites.add(closest.get(leftEdge));
            infinites.add(closest.get(rightEdge));
        }

        return infinites;
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
