package advent2016;

import util.Coordinate;
import util.Hashing;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.Locale;
import java.util.Objects;
import java.util.Queue;

public class Day17_2 {
    private static final String PASSCODE = "lpvhkcbi";
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(solve());
    }

    private static int solve() throws NoSuchAlgorithmException {
        Queue<PathElement> queue = new ArrayDeque<>();
        PathElement start = new PathElement("", 0, new Coordinate(0, 0));
        queue.add(start);
        PathElement max = start;
        while (!queue.isEmpty()) {
            PathElement pathElement = queue.remove();
            String path = pathElement.getPath();
            int distance = pathElement.getDistance();
            Coordinate coord = pathElement.getLocation();

            if (isVault(coord)) {
                if (distance > max.getDistance()) {
                    max = pathElement;
                }
                continue;
            }

            String md5 = Hashing.md5(PASSCODE + path).toLowerCase(Locale.ROOT);
            String truncate = md5.substring(0, 4);

            Coordinate up = coord.coordinateUp();
            if (isOpen(up, truncate.charAt(0))) {
                queue.add(new PathElement(path + "U", distance + 1, up));
            }

            Coordinate down = coord.coordinateDown();
            if (isOpen(down, truncate.charAt(1))) {
                queue.add(new PathElement(path + "D", distance + 1, down));
            }

            Coordinate left = coord.coordinateLeft();
            if (isOpen(left, truncate.charAt(2))) {
                queue.add(new PathElement(path + "L", distance + 1, left));
            }

            Coordinate right = coord.coordinateRight();
            if (isOpen(right, truncate.charAt(3))) {
                queue.add(new PathElement(path + "R", distance + 1, right));
            }
        }
        return max.getDistance();
    }

    private static boolean isVault(Coordinate coord) {
        return coord.getX() == 3 && coord.getY() == 3;
    }

    private static boolean isOpen(Coordinate coord, char c) {
        switch (c) {
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
                return coord.isInBounds(4, 4);
            default:
                return false;
        }
    }

    private static class PathElement {
        String path;
        int distance;
        Coordinate location;

        public PathElement(String path, int distance, Coordinate location) {
            this.path = path;
            this.distance = distance;
            this.location = location;
        }

        public String getPath() {
            return path;
        }

        public int getDistance() {
            return distance;
        }

        public Coordinate getLocation() {
            return location;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PathElement that = (PathElement) o;
            return distance == that.distance && Objects.equals(path, that.path) && Objects.equals(location, that.location);
        }

        @Override
        public int hashCode() {
            return Objects.hash(path, distance, location);
        }

        @Override
        public String toString() {
            return "PathElement{" +
                    "path='" + path + '\'' +
                    ", distance=" + distance +
                    ", location=" + location +
                    '}';
        }
    }
}
