package advent2015;

import java.util.Arrays;

public class Day17 {

    private static final int[] CONTAINERS = new int[]{33, 14, 18, 20, 45, 35, 16, 35, 1, 13, 18, 13, 50, 44, 48, 6, 24, 41, 30, 42};

    public static void main(String[] args) {
        boolean[] options = new boolean[CONTAINERS.length];
        int count = 0;
        while (notAllTrue(options)) {
            int total = 0;
            for (int i = 0; i < CONTAINERS.length; i++) {
                if (options[i]) {
                    total += CONTAINERS[i];
                }
            }
            if (total == 150) {
                count++;
            }
            update(options);
        }
        System.out.println(count);
    }

    private static void update(boolean[] a) {
        int n = 0;
        for (int i = a.length - 1; i >= 0; i--) {
            n = (n << 1) + (a[i] ? 1 : 0);
        }

        n++;

        for (int i = 0; i < a.length; i++) {
            a[i] = (n & (1 << i)) != 0;
        }
    }

    private static boolean notAllTrue(boolean[] options) {
        for (boolean option : options) {
            if (!option) {
                return true;
            }
        }
        return false;
    }
}
