package advent2017;

import util.Coordinate;

public class Day3_2 {

    public static void main(String[] args) {
        int input = 325489;
        System.out.println(firstLarger(input));
    }

    private static int firstLarger(int input) {
        int size = getSize(input) + 2; // certainly big enough, plus some padding
        int[][] memory = new int[size][size];
        memory[size / 2][size / 2] = 1;
        Coordinate center = new Coordinate(size / 2, size / 2);

        int shell = 3; // which n*n layer
        Coordinate coord = center.coordinateRight();
        while (true) {
            // up on right
            for (int i = 0; i < shell - 2; i++) {
                int sum = writeSum(memory, coord);
                if (sum > input) {
                    return sum;
                } else {
                    coord = coord.coordinateUp();
                }
            }

            // left on top
            for (int i = 0; i < shell - 1; i++) {
                int sum = writeSum(memory, coord);
                if (sum > input) {
                    return sum;
                } else {
                    coord = coord.coordinateLeft();
                }
            }

            // down on left
            for (int i = 0; i < shell - 1; i++) {
                int sum = writeSum(memory, coord);
                if (sum > input) {
                    return sum;
                } else {
                    coord = coord.coordinateDown();
                }
            }

            // right on bottom
            for (int i = 0; i < shell; i++) {
                int sum = writeSum(memory, coord);
                if (sum > input) {
                    return sum;
                } else {
                    coord = coord.coordinateRight();
                }
            }
            shell += 2;
        }
    }

    private static int getSize(int input) {
        int size = (int) Math.ceil(Math.sqrt(input));
        if (size % 2 == 0) {
            size++;
        }
        return size;
    }

    private static int writeSum(int[][] memory, Coordinate coord) {
        int sum = 0;
        // the cell itself will be 0, so we don't care about counting it
        for (int x = coord.getX() - 1; x <= coord.getX() + 1; x++) {
            for (int y = coord.getY() - 1; y <= coord.getY() + 1; y++) {
                sum += memory[x][y];
            }
        }
        memory[coord.getX()][coord.getY()] = sum;
        return sum;
    }
}
