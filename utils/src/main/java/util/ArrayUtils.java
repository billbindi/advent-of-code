package util;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class ArrayUtils {

    public static void print2DArray(int[][] arr) {
        for (int[] row : arr) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void print2DArray(boolean[][] arr) {
        for (boolean[] row : arr) {
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
