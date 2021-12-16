package advent2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 {

    private static final String FILENAME = "day13_input.txt";

    private static List<int[]> permutations = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int[][] happiness = parseInput();
        int[] ints = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        int n = ints.length;
        permute(ints, 0, n - 1);
        System.out.println(optimize(happiness));
    }

    private static int optimize(int[][] happiness) {
        int max = Integer.MIN_VALUE;
        for (int[] attempt : permutations) {
            int val = value(happiness, attempt);
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    private static int value(int[][] happiness, int[] attempt) {
        int val = 0;
        for (int i = 0; i < attempt.length - 1; i++) {
            val += happiness[attempt[i]][attempt[i+1]];
            val += happiness[attempt[i+1]][attempt[i]];
        }
        val += happiness[attempt[attempt.length - 1]][attempt[0]];
        val += happiness[attempt[0]][attempt[attempt.length - 1]];
        return val;
    }

    private static int[][] parseInput() throws IOException {
        int i = 0, j = 0;
        int[][] output = new int[8][8];
        for (String line : Files.readAllLines(Paths.get(FILENAME))) {
            String[] parts = line.split(" ");
            int val = Integer.parseInt(parts[3]);
            int happiness = parts[2].startsWith("g") ? val : -val;
            if (i == j) {
                output[i][j] = 0;
                j++;
            }
            output[i][j] = happiness;
            j++;
            if (j >= 8) {
                j = 0;
                i++;
            }
        }
        return output;
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
}
