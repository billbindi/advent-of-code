package advent2018;

import util.Coordinate;
import util.PixelCoordinate;

public class Day11_1 {

    private static final int GRID_X = 300;
    private static final int GRID_Y = 300;
    private static final int SQUARE_SIZE = 3;

    private static final int SERIAL_NUMBER = 8444;

    public static void main(String[] args) {
        System.out.println(solve());
    }

    private static PixelCoordinate solve() {
        int[][] fuelCells = computeFuelLevels();
        int maxLevel = Integer.MIN_VALUE;
        PixelCoordinate maxIndex = new PixelCoordinate(0, 0);
        for (int x = 1; x <= GRID_X - SQUARE_SIZE + 1; x++) {
            for (int y = 1; y <= GRID_Y - SQUARE_SIZE + 1; y++) {
                int totalFuel = computeTotalFuel(fuelCells, x, y);
                if (totalFuel > maxLevel) {
                    maxLevel = totalFuel;
                    maxIndex = new PixelCoordinate(x, y);
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

    private static int computeTotalFuel(int[][] fuelCells, int startX, int startY) {
        int total = 0;
        for (int x = startX; x < startX + SQUARE_SIZE; x++) {
            for (int y = startY; y < startY + SQUARE_SIZE; y++) {
                total += fuelCells[x][y];
            }
        }
        return total;
    }
}
