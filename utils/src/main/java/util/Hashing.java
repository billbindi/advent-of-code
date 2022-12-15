package util;

import com.google.common.base.Preconditions;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    private static final int[] KNOT_HASH_EXTRA_LENGTHS = new int[]{17, 31, 73, 47, 23};
    private static final int KNOT_HASH_SIZE = 256;
    private static final int KNOT_HASH_ROUNDS = 64;
    private static final int KNOT_HASH_BLOCK_SIZE = 16;

    public static String md5(String input) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = input.getBytes();
            md.update(bytes);
            return DatatypeConverter.printHexBinary(md.digest());
    }

    // see https://adventofcode.com/2017/day/10
    public static String knotHash(String input) {
        int[] lengths = parseLengths(input);
        int[] numbers = makeDefault();
        return computeHash(numbers, lengths);
    }

    private static int[] parseLengths(String line) {
        int[] lengths = new int[line.length() + KNOT_HASH_EXTRA_LENGTHS.length];
        for (int i = 0; i < line.length(); i++) {
            lengths[i] = line.charAt(i);
        }
        System.arraycopy(KNOT_HASH_EXTRA_LENGTHS, 0, lengths, line.length(), KNOT_HASH_EXTRA_LENGTHS.length);
        return lengths;
    }

    private static int[] makeDefault() {
        int[] numbers = new int[KNOT_HASH_SIZE];
        for (int i = 0; i < KNOT_HASH_SIZE; i++) {
            numbers[i] = i;
        }
        return numbers;
    }

    private static String computeHash(int[] numbers, int[] lengths) {
        int index = 0;
        int skip = 0;
        for (int i = 0; i < KNOT_HASH_ROUNDS; i++) {
            for (int length : lengths) {
                rotate(numbers, index, index + length - 1);
                index = (index + length + skip) % numbers.length;
                skip++;
            }
        }

        int[] blocks = combineBlocks(numbers);
        return hexString(blocks);
    }

    // end may be larger than the array, that's fine, everything will be modded
    private static void rotate(int[] numbers, int start, int end) {
        int numFlips = (int) (Math.ceil((end - start) / 2.0));
        for (int i = 0; i < numFlips; i++) {
            int firstIndex = (start + i) % numbers.length;
            int secondIndex = (end - i) % numbers.length;
            int temp = numbers[firstIndex];
            numbers[firstIndex] = numbers[secondIndex];
            numbers[secondIndex] = temp;
        }
    }

    // need length of array to be divisible by BLOCK_SIZE
    private static int[] combineBlocks(int[] numbers) {
        Preconditions.checkArgument(numbers.length % KNOT_HASH_BLOCK_SIZE == 0);
        int numBlocks = numbers.length / KNOT_HASH_BLOCK_SIZE;
        int[] blocks = new int[numBlocks];
        for (int i = 0; i < numBlocks; i++) {
            int blockVal = 0; // 0 ^ X = X, so safe starting point
            for (int offset = 0; offset < KNOT_HASH_BLOCK_SIZE; offset++) {
                blockVal ^= numbers[(KNOT_HASH_BLOCK_SIZE * i) + offset];
            }
            blocks[i] = blockVal;
        }
        return blocks;
    }

    private static String hexString(int[] blocks) {
        StringBuilder hex = new StringBuilder();
        for (int block : blocks) {
            String str = Integer.toHexString(block);
            if (str.length() == 1) {
                hex.append('0');
            }
            hex.append(str);
        }
        return hex.toString();
    }

    private Hashing() {
        // no instantiation
    }
}
