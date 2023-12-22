package util;

import java.util.Arrays;
import java.util.List;

public final class ArrayUtils {

    public static void print2DArray(int[][] arr) {
        for (int[] row : arr) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static int[] fromList(List<Integer> nums) {
        return nums.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private ArrayUtils() {
        // no instantiation
    }
}
