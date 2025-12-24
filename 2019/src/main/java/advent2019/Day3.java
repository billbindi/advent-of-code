package advent2019;

import com.google.common.base.Splitter;
import util.Coordinate;
import util.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class Day3 {
    private static final String INPUT_FILENAME = "2019/day3_input.txt";
    private static final Path INPUT_PATH = Path.of(INPUT_FILENAME);

    public static void main(String[] args) throws IOException {
        System.out.println(part1(Files.lines(INPUT_PATH)));
        System.out.println(part2(Files.lines(INPUT_PATH)));
    }

    private static int part1(Stream<String> lines) {
        // build list of wires
        List<Wire> wires = lines.map(Day3::buildWire).toList();

        // find intersection point. arbitrary stop point in search, could
        // be replaced by a tracker of seeing at least one part of every
        // wire at this layer (no intersection possible past that point),
        // but that extra overhead is not wort the effort.
        for (int manhattanDistance = 1; manhattanDistance < 10000; manhattanDistance++) {
            // make a diamond centered on the origin. start at the far right and sweep around CCW.
            // will double test all endpoints, that is fine.

            // (m, 0) -> (0, m)
            for (int x = manhattanDistance, y = 0; x >= 0; x--, y++) {
                Coordinate test = new Coordinate(x, y);
                if (wires.stream().allMatch(wire -> wire.contains(test))) {
                    return manhattanDistance;
                }
            }

            // (0, m) -> (-m, 0)
            for (int x = 0, y = manhattanDistance; y >= 0; x--, y--) {
                Coordinate test = new Coordinate(x, y);
                if (wires.stream().allMatch(wire -> wire.contains(test))) {
                    return manhattanDistance;
                }
            }

            // (-m, 0) -> (0, -m)
            for (int x = -manhattanDistance, y = 0; x <= 0; x++, y--) {
                Coordinate test = new Coordinate(x, y);
                if (wires.stream().allMatch(wire -> wire.contains(test))) {
                    return manhattanDistance;
                }
            }

            // (0, -m) -> (m, 0)
            for (int x = 0, y = -manhattanDistance; y <= 0; x++, y++) {
                Coordinate test = new Coordinate(x, y);
                if (wires.stream().allMatch(wire -> wire.contains(test))) {
                    return manhattanDistance;
                }
            }
        }
        return -1;
    }

    private static Wire buildWire(String line) {
        Wire wire = new Wire();
        Coordinate tracker = new Coordinate(0, 0);
        int steps = 0;
        Iterable<String> paths = Splitter.on(',').split(line);
        for(String path : paths) {
            int length =  Integer.parseInt(path.substring(1));
            tracker = switch (path.charAt(0)) {
                case 'L' -> {
                    wire.addAll(tracker, Direction.LEFT, length, steps);
                    yield new Coordinate(tracker.getX() - length, tracker.getY());
                }
                case 'R' -> {
                    wire.addAll(tracker, Direction.RIGHT, length, steps);
                    yield new Coordinate(tracker.getX() + length, tracker.getY());
                }
                case 'U' -> {
                    wire.addAll(tracker, Direction.UP, length, steps);
                    yield new Coordinate(tracker.getX(), tracker.getY() + length);
                }
                case 'D' -> {
                    wire.addAll(tracker, Direction.DOWN, length, steps);
                    yield new Coordinate(tracker.getX(), tracker.getY() - length);
                }
                default -> throw new IllegalArgumentException("Invalid input: " + path);
            };
            steps += length;
        }
        return wire;
    }

    private static int part2(Stream<String> lines) {
        return 0;
    }

    private static final class Wire {
        private final Map<Coordinate, Integer> coordinates = new HashMap<>();

        public void addAll(Coordinate start, Direction direction, int length, int startingSteps) {
            int addX = switch (direction) {
                case LEFT -> -1;
                case RIGHT -> 1;
                case UP, DOWN -> 0;
            };
            int addY = switch (direction) {
                case LEFT, RIGHT -> 0;
                case UP -> 1;
                case DOWN -> -1;
            };

            // include start, though not strictly speaking necessary
            for (int count = 0; count <= length; count++) {
                int x = start.getX() + (count * addX);
                int y = start.getY() + (count * addY);
                coordinates.putIfAbsent(new Coordinate(x, y), startingSteps + count);
            }
        }

        public boolean contains(Coordinate coordinate) {
            return coordinates.containsKey(coordinate);
        }

        public int steps(Coordinate coordinate) {
            return coordinates.getOrDefault(coordinate, -1);
        }
    }
}
