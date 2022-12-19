package advent2017;

import util.Coordinate;
import util.Direction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22_1 {

    private static final String FILENAME = "2017/day22_input.txt";

    private static final int BURSTS = 10000;

    // begin in the middle, facing up
    private static final Coordinate START_COORDINATE = new Coordinate(12, 12);
    private static final Direction START_FACING = Direction.UP;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        Map<Coordinate, Boolean> mapping = parseCenterGrid(lines);
        int numInfected = performBursts(mapping);
        System.out.println(numInfected);
    }

    private static Map<Coordinate, Boolean> parseCenterGrid(List<String> lines) {
        Map<Coordinate, Boolean> mapping = new HashMap<>(lines.size() * lines.get(0).length());
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(lines.size() - 1 - i);
            for (int j = 0; j < line.length(); j++) {
                Coordinate coord = new Coordinate(j, i);
                boolean infected = line.charAt(j) == '#';
                mapping.put(coord, infected);
            }

        }
        return mapping;
    }

    private static int performBursts(Map<Coordinate, Boolean> mapping) {
        Coordinate curr = START_COORDINATE;
        Direction facing = START_FACING;
        int count = 0;
        for (int unused = 0; unused < BURSTS; unused++) {
            if (isInfected(mapping, curr)) {
                facing = facing.clockwise();
                mapping.put(curr, false);
            } else {
                facing = facing.counterclockwise();
                mapping.put(curr, true);
                count++;
            }
            curr = nextCoordinate(curr, facing);
        }
        return count;
    }

    private static boolean isInfected(Map<Coordinate, Boolean> mapping, Coordinate coord) {
        if (!mapping.containsKey(coord)) {
            mapping.put(coord, false);
        }
        return mapping.get(coord);
    }

    private static Coordinate nextCoordinate(Coordinate curr, Direction facing) {
        switch (facing) {
            case UP:
                return curr.coordinateUp();
            case DOWN:
                return curr.coordinateDown();
            case LEFT:
                return curr.coordinateLeft();
            case RIGHT:
                return curr.coordinateRight();
            default:
                throw new IllegalArgumentException("INVALID DIRECTION: " + facing);
        }
    }
}
