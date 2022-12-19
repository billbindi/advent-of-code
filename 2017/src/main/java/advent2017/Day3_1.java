package advent2017;

import util.PixelCoordinate;

public class Day3_1 {

    public static void main(String[] args) {
        int input = 325489;
        int size = getSize(input);
        PixelCoordinate end = findValue(size, input);
        int dist = distanceFromCenter(size, end);
        System.out.println(dist);
    }

    private static int getSize(int input) {
        int size = (int) Math.ceil(Math.sqrt(input));
        if (size % 2 == 0) {
            size++;
        }
        return size;
    }

    private static PixelCoordinate findValue(int size, int input) {
        // inner memory covers (n-2)^2 numbers
        int val = (size - 2) * (size - 2) + 1;

        // start from outer edge, one space up from bottom right
        for (int y = size - 2; y >= 0; y--) {
            if (val == input) {
                return new PixelCoordinate(size - 1, y);
            } else {
                val++;
            }
        }
        for (int x = size - 2; x >= 0; x--) {
            if (val == input) {
                return new PixelCoordinate(x, 0);
            } else {
                val++;
            }
        }
        for (int y = 1; y < size; y++) {
            if (val == input) {
                return new PixelCoordinate(0, y);
            } else {
                val++;
            }
        }
        for (int x = 1; x < size; x++) {
            if (val == input) {
                return new PixelCoordinate(x, size - 1);
            } else {
                val++;
            }
        }

        throw new IllegalStateException("SHOULD HAVE FOUND IT BY NOW");
    }

    private static int distanceFromCenter(int size, PixelCoordinate end) {
        int center = size / 2;
        return Math.abs(end.getX() - center) + Math.abs(end.getY() - center);
    }
}
