package advent2018;

import util.PixelCoordinate;

public class Day11_2 {

    private static final int GRID_X = 300;
    private static final int GRID_Y = 300;
    private static final int MAX_SQUARE_SIZE = 300;

    private static final int SERIAL_NUMBER = 8444;

    public static void main(String[] args) {
        System.out.println(solve());
    }

    private static CoordWithSize solve() {
        int[][] fuelCells = computeFuelLevels();
        int maxLevel = Integer.MIN_VALUE;
        CoordWithSize maxIndex = new CoordWithSize(new PixelCoordinate(0, 0), 0);
        for (int squareSize = 1; squareSize <= MAX_SQUARE_SIZE; squareSize++) {
            for (int x = 1; x <= GRID_X - squareSize + 1; x++) {
                for (int y = 1; y <= GRID_Y - squareSize + 1; y++) {
                    int totalFuel = computeTotalFuel(fuelCells, x, y, squareSize);
                    if (totalFuel > maxLevel) {
                        maxLevel = totalFuel;
                        maxIndex = new CoordWithSize(new PixelCoordinate(x, y), squareSize);
                    }
                }
            }
        }
        return maxIndex;
    }

    private static int[][] computeFuelLevels() {
        int[][] fuelCells = new int[GRID_X + 1][GRID_Y + 1];
        for (int x = 1; x <= GRID_X; x++) {
            for (int y = 1; y <= GRID_Y; y++) {
                int rackId = x + 10;
                int powerLevel = rackId * y;
                powerLevel += SERIAL_NUMBER;
                powerLevel *= rackId;
                int hundredsDigit = computeHundredsDigit(powerLevel);
                fuelCells[x][y] = hundredsDigit - 5;
            }
        }
        return fuelCells;
    }

    private static int computeHundredsDigit(int powerLevel) {
        if (powerLevel < 100) {
            return 0;
        } else {
            String stringValue = String.valueOf(powerLevel);
            String hundredsChar = stringValue.substring(stringValue.length() - 3, stringValue.length() - 2);
            return Integer.parseInt(hundredsChar);
        }
    }

    private static int computeTotalFuel(int[][] fuelCells, int startX, int startY, int squareSize) {
        int total = 0;
        for (int x = startX; x < startX + squareSize; x++) {
            for (int y = startY; y < startY + squareSize; y++) {
                total += fuelCells[x][y];
            }
        }
        return total;
    }

    private static class CoordWithSize {
        final PixelCoordinate coord;
        final int squareSize;

        private CoordWithSize(PixelCoordinate coord, int squareSize) {
            this.coord = coord;
            this.squareSize = squareSize;
        }

        @Override
        public String toString() {
            return "CoordWithSize{" +
                    "coord=" + coord +
                    ", squareSize=" + squareSize +
                    '}';
        }
    }
}
