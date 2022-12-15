package advent2017;

import util.Hashing;

import java.math.BigInteger;
import java.util.Arrays;

public class Day14_1 {

    private static final String INPUT = "jxqlasbh";

    public static void main(String[] args) {
        String[] hashes = computeHashes();
        long used = computeUsed(hashes);
        System.out.println(used);
    }

    private static String[] computeHashes() {
        String[] hashes = new String[128];
        for (int i = 0; i < 128; i++) {
            String inputString = INPUT + "-" + i;
            hashes[i] = Hashing.knotHash(inputString);
        }
        return hashes;
    }

    private static long computeUsed(String[] hashes) {
        return Arrays.stream(hashes)
                .mapToLong(Day14_1::computeUsed)
                .sum();
    }

    private static long computeUsed(String hash) {
        String binary = hexToBinary(hash);
        return binary.chars()
                .filter(c -> c == '1')
                .count();
    }

    // no leading 0's
    private static String hexToBinary(String hex) {
        return new BigInteger(hex, 16).toString(2);
    }
}
