package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day8 {
    private static final String RECT_INSTRUCTION_PREFIX = "rect ";
    private static final String ROTATE_COLUMN_INSTRUCTION_PREFIX = "rotate column x=";
    private static final String ROTATE_ROW_INSTRUCTION_PREFIX = "rotate row y=";

    private static final String FILENAME = "2016/day8_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        Rect rect = new Rect(50, 6);
        for (String instruction : content) {
            if (instruction.startsWith(RECT_INSTRUCTION_PREFIX)) {
                String[] split = instruction.substring(RECT_INSTRUCTION_PREFIX.length()).split("x");
                int width = Integer.parseInt(split[0]);
                int height = Integer.parseInt(split[1]);
                rect.rect(width, height);
            } else if (instruction.startsWith(ROTATE_COLUMN_INSTRUCTION_PREFIX)) {
                String[] split = instruction.substring(ROTATE_COLUMN_INSTRUCTION_PREFIX.length()).split(" by ");
                int column = Integer.parseInt(split[0]);
                int shift = Integer.parseInt(split[1]);
                rect.rotateColumn(column, shift);
            } else if (instruction.startsWith(ROTATE_ROW_INSTRUCTION_PREFIX)) {
                String[] split = instruction.substring(ROTATE_ROW_INSTRUCTION_PREFIX.length()).split(" by ");
                int row = Integer.parseInt(split[0]);
                int shift = Integer.parseInt(split[1]);
                rect.rotateRow(row, shift);
            }
        }
        rect.display();
        return rect.countOn();
    }

    private static class Rect {
        boolean[][] grid;

        Rect(int width, int height) {
            this(new boolean[height][width]);
        }

        Rect(boolean[][] grid) {
            this.grid = grid;
        }

        void rect(int width, int height) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    grid[i][j] = true;
                }
            }
        }

        void rotateRow(int row, int shift) {
            int length = grid[row].length;
            boolean[] newRow = new boolean[length];
            for (int i = 0; i < length; i++) {
                newRow[(i + shift) % length] = grid[row][i];
            }
            grid[row] = newRow;
        }

        void rotateColumn(int column, int shift) {
            int length = grid.length;
            boolean[] newColumn = new boolean[length];
            for (int i = 0; i < length; i++) {
                newColumn[(i + shift) % length] = grid[i][column];
            }

            for (int i = 0; i < length; i++) {
                grid[i][column] = newColumn[i];
            }
        }

        int countOn() {
            int count = 0;
            for (boolean[] row : grid) {
                for (boolean cell : row) {
                    if (cell) {
                        count++;
                    }
                }
            }
            return count;
        }

        void display() {
            for (boolean[] row : grid) {
                for (boolean cell : row) {
                    if (cell) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();
            }
        }
    }
}
