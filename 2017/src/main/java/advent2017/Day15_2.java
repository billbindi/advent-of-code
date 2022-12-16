package advent2017;

import com.google.common.base.Strings;

import java.math.BigInteger;

public class Day15_2 {

    private static final int GENERATOR_A_START = 516;
    private static final int GENERATOR_B_START = 190;

    private static final BigInteger GENERATOR_A_FACTOR = BigInteger.valueOf(16807);
    private static final BigInteger GENERATOR_B_FACTOR = BigInteger.valueOf(48271);

    private static final BigInteger MODULUS = BigInteger.valueOf(Integer.MAX_VALUE);
    private static final BigInteger GENERATOR_A_DIVISOR = BigInteger.valueOf(4);
    private static final BigInteger GENERATOR_B_DIVISOR = BigInteger.valueOf(8);

    private static final int NUM_ROUNDS = 5_000_000;

    public static void main(String[] args) {
        int count = countMatches();
        System.out.println(count);
    }

    private static int countMatches() {
        BigInteger a = BigInteger.valueOf(GENERATOR_A_START);
        BigInteger b = BigInteger.valueOf(GENERATOR_B_START);
        int count = 0;
        for (int i = 0; i < NUM_ROUNDS; i++) {
            a = getNextValue(a, GENERATOR_A_FACTOR, GENERATOR_A_DIVISOR);
            b = getNextValue(b, GENERATOR_B_FACTOR, GENERATOR_B_DIVISOR);
            if (matches(a, b)) {
                count++;
            }
        }

        return count;
    }

    private static BigInteger getNextValue(BigInteger initial, BigInteger factor, BigInteger divisor) {
        BigInteger attempt = initial.multiply(factor).mod(MODULUS);
        while (attempt.mod(divisor).intValue() != 0) {
            attempt = attempt.multiply(factor).mod(MODULUS);
        }
        return attempt;
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
