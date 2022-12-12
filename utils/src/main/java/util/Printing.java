package util;

import java.util.Arrays;

public final class Printing {

    public static void printArray(int[][] arr) {
        for (int[] row : arr) {
            System.out.println(Arrays.toString(row));
        }
    }

    private Printing() {
        // no instantiation
    }
}
