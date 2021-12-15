import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Day1_2 {
    private static final String FILENAME = "2016/day1_1_input.txt";
    private static final Set<Coordinate> COORDINATE_CACHE = new HashSet<>();

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        String content = Files.readString(input);

        String[] directions = content.split(",\\s*");
        System.out.println(solve(directions));
    }

    private static int solve(String[] directions) {
        Position pos = new Position(Direction.NORTH, 0, 0);
        for (String direction : directions) {
            char turn = direction.charAt(0);
            int amount = Integer.parseInt(direction.substring(1));
            int dist = pos.apply(turn, amount);
            if (dist >= 0) {
                return dist;
            }
        }

        // same answer as 1_1
        return 231;
    }

    private enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    private static class Coordinate {
        int x;
        int y;

        Coordinate() {
            this(0, 0);
        }

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Position {
        Direction facing;
        int xPos;
        int yPos;

        Position(Direction facing, int x, int y) {
            this.facing = facing;
            this.xPos = x;
            this.yPos = y;
        }

        int apply(char turn, int amount) {
            switch (facing) {
                case NORTH:
                    switch (turn) {
                        case 'R':
                            for (int i = 0; i < amount; i++) {
                                Coordinate coordinate = new Coordinate(xPos, yPos);
                                if (!COORDINATE_CACHE.add(coordinate)) {
                                    return distance();
                                } else {
                                    COORDINATE_CACHE.add(coordinate);
                                    xPos++;
                                }
                            }
                            facing = Direction.EAST;
                            return -1;
                        case 'L':
                            for (int i = 0; i < amount; i++) {
                                Coordinate coordinate = new Coordinate(xPos, yPos);
                                if (!COORDINATE_CACHE.add(coordinate)) {
                                    return distance();
                                } else {
                                    COORDINATE_CACHE.add(coordinate);
                                    xPos--;
                                }
                            }
                            facing = Direction.WEST;
                            return -1;
                        default:
                            throw new IllegalArgumentException("Unknown direction: " + turn);
                    }
                case EAST:
                    switch (turn) {
                        case 'R':
                            for (int i = 0; i < amount; i++) {
                                Coordinate coordinate = new Coordinate(xPos, yPos);
                                if (!COORDINATE_CACHE.add(coordinate)) {
                                    return distance();
                                } else {
                                    COORDINATE_CACHE.add(coordinate);
                                    yPos--;
                                }
                            }
                            facing = Direction.SOUTH;
                            return -1;
                        case 'L':
                            for (int i = 0; i < amount; i++) {
                                Coordinate coordinate = new Coordinate(xPos, yPos);
                                if (!COORDINATE_CACHE.add(coordinate)) {
                                    return distance();
                                } else {
                                    COORDINATE_CACHE.add(coordinate);
                                    yPos++;
                                }
                            }
                            facing = Direction.NORTH;
                            return -1;
                        default:
                            throw new IllegalArgumentException("Unknown direction: " + turn);
                    }
                case SOUTH:
                    switch (turn) {
                        case 'R':
                            for (int i = 0; i < amount; i++) {
                                Coordinate coordinate = new Coordinate(xPos, yPos);
                                if (!COORDINATE_CACHE.add(coordinate)) {
                                    return distance();
                                } else {
                                    COORDINATE_CACHE.add(coordinate);
                                    xPos--;
                                }
                            }
                            facing = Direction.WEST;
                            return -1;
                        case 'L':
                            for (int i = 0; i < amount; i++) {
                                Coordinate coordinate = new Coordinate(xPos, yPos);
                                if (!COORDINATE_CACHE.add(coordinate)) {
                                    return distance();
                                } else {
                                    COORDINATE_CACHE.add(coordinate);
                                    xPos++;
                                }
                            }
                            facing = Direction.EAST;
                            return -1;
                        default:
                            throw new IllegalArgumentException("Unknown direction: " + turn);
                    }
                case WEST:
                    switch (turn) {
                        case 'R':
                            for (int i = 0; i < amount; i++) {
                                Coordinate coordinate = new Coordinate(xPos, yPos);
                                if (!COORDINATE_CACHE.add(coordinate)) {
                                    return distance();
                                } else {
                                    COORDINATE_CACHE.add(coordinate);
                                    yPos++;
                                }
                            }
                            facing = Direction.NORTH;
                            return -1;
                        case 'L':
                            for (int i = 0; i < amount; i++) {
                                Coordinate coordinate = new Coordinate(xPos, yPos);
                                if (!COORDINATE_CACHE.add(coordinate)) {
                                    return distance();
                                } else {
                                    COORDINATE_CACHE.add(coordinate);
                                    yPos--;
                                }
                            }
                            facing = Direction.SOUTH;
                            return -1;
                        default:
                            throw new IllegalArgumentException("Unknown direction: " + turn);
                    }
            }
            return -1;
        }

        int distance() {
            return Math.abs(xPos) + Math.abs(yPos);
        }
    }
}
