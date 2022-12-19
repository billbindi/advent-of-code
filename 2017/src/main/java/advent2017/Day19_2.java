package advent2017;

import com.google.common.base.Strings;
import util.PixelCoordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day19_2 {

    private static final String FILENAME = "2017/day19_input.txt";

    private static final int SQUARE_SIZE = 200;

    public static void main(String[] args) throws IOException {
        char[][] routeMap = getRouteMap(Files.readAllLines(Paths.get(FILENAME)));
        int steps = solve(routeMap);
        System.out.println(steps);
    }

    private static char[][] getRouteMap(List<String> lines) {
        char[][] routeMap = new char[SQUARE_SIZE][SQUARE_SIZE];
        for (int i = 0; i < SQUARE_SIZE; i++) {
            String line = lines.get(i);
            String padded = Strings.padEnd(line, SQUARE_SIZE, ' ');
            routeMap[i] = padded.toCharArray();
        }
        return routeMap;
    }

    private static int solve(char[][] routeMap) {
        PixelCoordinate coord = findStart(routeMap);
        Direction direction = Direction.DOWN;

        int steps = 0;
        while (charAt(routeMap, coord) != ' ') {
            steps++;
            char curr = charAt(routeMap, coord);
            switch (curr) {
                case '|':
                case '-':
                    // assume path is still valid, just continue on path
                    coord = nextCoordinate(coord, direction);
                    break;
                case '+':
                    CoordWithDir turn = turn(routeMap, coord, direction);
                    coord = turn.coord;
                    direction = turn.dir;
                    break;
                default:
                    coord = nextCoordinate(coord, direction);
            }
        }
        return steps;
    }

    private static PixelCoordinate findStart(char[][] routeMap) {
        for (int i = 0; i < routeMap[0].length; i++) {
            if (routeMap[0][i] == '|') {
                return new PixelCoordinate(0, i);
            }
        }
        throw new IllegalStateException("COULD NOT FIND START FOR TOP ROW: " + new String(routeMap[0]));
    }

    // N.B. Coordinates increase in X going down, increase in Y going right
    // 'right' -> down
    // 'up' -> left
    // 'down' -> right
    // 'left' -> up
    private static PixelCoordinate nextCoordinate(PixelCoordinate curr, Direction dir) {
        switch (dir) {
            case RIGHT:
                return curr.coordinateDown();
            case UP:
                return curr.coordinateLeft();
            case DOWN:
                return curr.coordinateRight();
            case LEFT:
                return curr.coordinateUp();
            default:
                throw new IllegalStateException("INVALID DIRECTION: " + dir);
        }
    }

    private static char charAt(char[][] arr, PixelCoordinate coord) {
        if (coord.isInBounds(SQUARE_SIZE, SQUARE_SIZE)) {
            return arr[coord.getX()][coord.getY()];
        } else {
            throw new IllegalArgumentException("OUT OF BOUNDS PATH AT: " + coord);
        }
    }

    // TODO: check bounds
    private static CoordWithDir turn(char[][] routeMap, PixelCoordinate coord, Direction direction) {
        switch (direction) {
            case LEFT:
            case RIGHT:
                PixelCoordinate up = nextCoordinate(coord, Direction.UP);
                PixelCoordinate down = nextCoordinate(coord, Direction.DOWN);
                if (up.isInBounds(SQUARE_SIZE, SQUARE_SIZE) && (charAt(routeMap, up) == '|' || isLetter(charAt(routeMap, up)))) {
                    return new CoordWithDir(up, Direction.UP);
                } else {
                    return new CoordWithDir(down, Direction.DOWN);
                }
            case UP:
            case DOWN:
                PixelCoordinate right = nextCoordinate(coord, Direction.RIGHT);
                PixelCoordinate left = nextCoordinate(coord, Direction.LEFT);
                if (right.isInBounds(SQUARE_SIZE, SQUARE_SIZE) && (charAt(routeMap, right) == '-' || isLetter(charAt(routeMap, right)))) {
                    return new CoordWithDir(right, Direction.RIGHT);
                } else {
                    return new CoordWithDir(left, Direction.LEFT);
                }
            default:
                throw new IllegalArgumentException("INVALID DIRECTION: " + direction);
        }
    }

    private static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    private enum Direction {
        RIGHT, UP, DOWN, LEFT
    }

    private static class CoordWithDir {
        final PixelCoordinate coord;
        final Direction dir;

        private CoordWithDir(PixelCoordinate coord, Direction dir) {
            this.coord = coord;
            this.dir = dir;
        }
    }
}
