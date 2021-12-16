package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day2_2 {
    private static final String FILENAME = "2016/day2_input.txt";

    private static final char[][] KEYPAD = {
            {'X', 'X', '1', 'X', 'X'},
            {'X', '2', '3', '4', 'X'},
            {'5', '6', '7', '8', '9'},
            {'X', 'A', 'B', 'C', 'X'},
            {'X', 'X', 'D', 'X', 'X'}
    };

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static String solve(List<String> content) {
        // start at '5'
        Coordinate loc = new Coordinate(0, 2);
        StringBuilder keys = new StringBuilder();

        for (String instruction : content) {
            char[] moves = instruction.toCharArray();
            for (char move : moves) {
                move(loc, move);
            }
            keys.append(getKey(loc));
        }

        return keys.toString();
    }

    private static char getKey(Coordinate loc) {
        return getKey(loc.getX(), loc.getY());
    }

    private static char getKey(int x, int y) {
        if (x < 0 || x > 4 || y < 0 || y > 4) {
            return 'X';
        } else {
            return KEYPAD[y][x];
        }
    }

    private static void move(Coordinate loc, char dir) {
        switch (dir) {
            case 'L':
                if (getKey(loc.getX() - 1, loc.getY()) != 'X') {
                    loc.addX(-1);
                }
                return;
            case 'R':
                if (getKey(loc.getX() + 1, loc.getY()) != 'X') {
                    loc.addX(1);
                }
                return;
            case 'U':
                if (getKey(loc.getX(), loc.getY() - 1) != 'X') {
                    loc.addY(-1);
                }
                return;
            case 'D':
                if (getKey(loc.getX(), loc.getY() + 1) != 'X') {
                    loc.addY(1);
                }
                return;
            default:
                throw new IllegalArgumentException("Direction unknown: " + dir);
        }
    }
}
