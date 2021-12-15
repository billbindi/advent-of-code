import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day1_1 {
    private static final String FILENAME = "2016/day1_input.txt";

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
            pos.apply(turn, amount);
        }

        return Math.abs(pos.xPos) + Math.abs(pos.yPos);
    }

    private enum Direction {
        NORTH, EAST, SOUTH, WEST
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

        void apply(char turn, int amount) {
            switch (facing) {
                case NORTH:
                    switch (turn) {
                        case 'R':
                            xPos += amount;
                            facing = Direction.EAST;
                            return;
                        case 'L':
                            xPos -= amount;
                            facing = Direction.WEST;
                            return;
                        default:
                            throw new IllegalArgumentException("Unknown direction: " + turn);
                    }
                case EAST:
                    switch (turn) {
                        case 'R':
                            yPos -= amount;
                            facing = Direction.SOUTH;
                            return;
                        case 'L':
                            yPos += amount;
                            facing = Direction.NORTH;
                            return;
                        default:
                            throw new IllegalArgumentException("Unknown direction: " + turn);
                    }
                case SOUTH:
                    switch (turn) {
                        case 'R':
                            xPos -= amount;
                            facing = Direction.WEST;
                            return;
                        case 'L':
                            xPos += amount;
                            facing = Direction.EAST;
                            return;
                        default:
                            throw new IllegalArgumentException("Unknown direction: " + turn);
                    }
                case WEST:
                    switch (turn) {
                        case 'R':
                            yPos += amount;
                            facing = Direction.NORTH;
                            return;
                        case 'L':
                            yPos -= amount;
                            facing = Direction.SOUTH;
                            return;
                        default:
                            throw new IllegalArgumentException("Unknown direction: " + turn);
                    }
            }
        }
    }
}
