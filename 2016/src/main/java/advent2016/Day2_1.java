package advent2016;

import util.Coordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day2_1 {
    private static final String FILENAME = "2016/day2_input.txt";

    private static final int[][] KEYPAD = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static String solve(List<String> content) {
        // start at '5'
        Coordinate loc = new Coordinate(1, 1);
        List<Integer> keys = new ArrayList<>(content.size());

        for (String instruction : content) {
            char[] moves = instruction.toCharArray();
            for (char move : moves) {
                move(loc, move);
            }
            keys.add(getKey(loc));
        }

        return keys.toString();
    }

    private static int getKey(Coordinate loc) {
        return KEYPAD[loc.getY()][loc.getX()];
    }

    private static void move(Coordinate loc, char dir) {
        switch (dir) {
            case 'L':
                if (loc.getX() > 0) {
                    loc.addX(-1);
                }
                return;
            case 'R':
                if (loc.getX() < 2) {
                    loc.addX(1);
                }
                return;
            case 'U':
                if (loc.getY() > 0) {
                    loc.addY(-1);
                }
                return;
            case 'D':
                if (loc.getY() < 2) {
                    loc.addY(1);
                }
                return;
            default:
                throw new IllegalArgumentException("Direction unknown: " + dir);
        }
    }
}
