package advent2017;

import com.google.common.base.Strings;

import java.math.BigInteger;

public class Day15_1 {

    private static final int GENERATOR_A_START = 516;
    private static final int GENERATOR_B_START = 190;

    private static final BigInteger GENERATOR_A_FACTOR = BigInteger.valueOf(16807);
    private static final BigInteger GENERATOR_B_FACTOR = BigInteger.valueOf(48271);

    private static final BigInteger MODULUS = BigInteger.valueOf(Integer.MAX_VALUE);

    private static final int NUM_ROUNDS = 40_000_000;

    public static void main(String[] args) {
        int count = countMatches();
        System.out.println(count);
    }

    private static int countMatches() {
        BigInteger a = BigInteger.valueOf(GENERATOR_A_START);
        BigInteger b = BigInteger.valueOf(GENERATOR_B_START);
        int count = 0;
        for (int i = 0; i < NUM_ROUNDS; i++) {
            a = a.multiply(GENERATOR_A_FACTOR).mod(MODULUS);
            b = b.multiply(GENERATOR_B_FACTOR).mod(MODULUS);
            if (matches(a, b)) {
                count++;
            }
        }

        return count;
    }

    // only check last 16 digits
    private static boolean matches(BigInteger a, BigInteger b) {
        String aBinary = Strings.padStart(a.toString(2), 16, '0');
        String bBinary = Strings.padStart(b.toString(2), 16, '0');
        String aBinary16 = aBinary.substring(aBinary.length() - 16);
        String bBinary16 = bBinary.substring(bBinary.length() - 16);
        return aBinary16.equalsIgnoreCase(bBinary16);
    }
}
