package advent2017;

import com.google.common.base.Strings;
import util.Hashing;

import java.math.BigInteger;
import java.util.Arrays;

public class Day14_2 {

    private static final String INPUT = "jxqlasbh";

    public static void main(String[] args) {
        String[] hashes = computeHashes();
        boolean[][] used = computeUsedGrid(hashes);
        int regions = computeRegions(used);
        System.out.println(regions);
    }

    private static String[] computeHashes() {
        String[] hashes = new String[128];
        for (int i = 0; i < 128; i++) {
            String inputString = INPUT + "-" + i;
            hashes[i] = Hashing.knotHash(inputString);
        }
        return hashes;
    }

    private static boolean[][] computeUsedGrid(String[] hashes) {
        boolean[][] grid = new boolean[hashes.length][128];
        for (int i = 0; i < hashes.length; i++) {
            grid[i] = computeUsedRow(hashes[i]);
        }
        return grid;
    }

    private static boolean[] computeUsedRow(String hash) {
        String binary = hexToBinary(hash);
        boolean[] row = new boolean[binary.length()];
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                row[i] = true;
            } else if (binary.charAt(i) == '0') {
                row[i] = false;
            } else {
                throw new IllegalStateException("BAD BINARY STRING: " + binary);
            }
        }
        return row;
    }

    // unlike part 1, need to care about padding with 0's
    private static String hexToBinary(String hex) {
        String binary = new BigInteger(hex, 16).toString(2);
        return Strings.padStart(binary, 128, '0');
    }

    private static int computeRegions(boolean[][] used) {
        int[][] regions = new int[used.length][used[0].length];
        int numRegions = 0;
        for (int i = 0; i < used.length; i++) {
            boolean[] row = used[i];
            for (int j = 0; j < row.length; j++) {
                if (used[i][j] && regions[i][j] == 0) {
                    numRegions++;
                    fillRegion(used, regions, numRegions, i, j);
                }
            }
        }
        return numRegions;
    }

    private static void fillRegion(boolean[][] used, int[][] regions, int region, int i, int j) {
        // check bounds
        if (i < 0 || i >= used.length || j < 0 || j >= used[i].length) {
            return;
        }

        // check if part of region
        if (used[i][j] && regions[i][j] == 0) {
            // this will go back almost immediately after, but will not continue since we set first
            regions[i][j] = region;
            fillRegion(used, regions, region, i - 1, j);
            fillRegion(used, regions, region, i + 1, j);
            fillRegion(used, regions, region, i, j - 1);
            fillRegion(used, regions, region, i, j + 1);
        }
    }
}
