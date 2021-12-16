package advent2015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 {

    private static final int[][] DISTANCES = new int[][]{
            {   0,  65, 129, 144,  71, 137,   3, 149},
            {  65,   0,  63,   4, 105, 125,  55,  14},
            { 129,  63,   0,  68,  52,  65,  22, 143},
            { 144,   4,  68,   0,   8,  23, 136, 115},
            {  71, 105,  52,   8,   0, 101,  84,  96},
            { 137, 125,  65,  23, 101,   0, 107,  14},
            {   3,  55,  22, 136,  84, 107,   0,  46},
            { 149,  14, 143, 115,  96,  14,  46,   0}
    };

    private static List<int[]> permutations = new ArrayList<>();

    public static void main(String[] args) {
        int[] ints = new int[]{0,1,2,3,4,5,6,7};
        int n = ints.length;
        permute(ints, 0, n - 1);
        System.out.println(travel());
    }

    private static int travel() {
        return permutations.stream()
                .mapToInt(Day9::distance)
                .min()
                .orElse(-1);
    }

    private static int distance(int[] ints) {
        int total = 0;
        for (int i = 0; i < ints.length - 1; i++) {
            total += DISTANCES[ints[i]][ints[i + 1]];
        }
        return total;
    }

    private static void permute(int[] input, int l, int r) {
        if (l == r) {
            permutations.add(Arrays.copyOf(input, input.length));
        } else {
            for (int i = l; i <= r; i++) {
                input = swap(input, l, i);
                permute(input, l + 1, r);
                input = swap(input, l, i);
            }
        }
    }

    private static int[] swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        return Arrays.copyOf(a, a.length);
    }

    private static void print(List<int[]> a) {
        for (int[] b : a) {
            System.out.println(Arrays.toString(b));
        }

    }
}
