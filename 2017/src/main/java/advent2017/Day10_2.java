package advent2017;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day10_2 {

    private static final String FILENAME = "2017/day10_input.txt";

    private static final int[] EXTRA_LENGTHS = new int[]{17, 31, 73, 47, 23};
    private static final int SIZE = 256;
    private static final int HASH_ROUNDS = 64;
    private static final int BLOCK_SIZE = 16;

    public static void main(String[] args) throws IOException {
        String line = Iterables.getOnlyElement(Files.readAllLines(Paths.get(FILENAME)));
        int[] lengths = parseLengths(Objects.requireNonNull(line));
        int[] numbers = makeDefault();
        String hash = computeHash(numbers, lengths);
        System.out.println(hash);
    }

    private static int[] parseLengths(String line) {
        int[] lengths = new int[line.length() + EXTRA_LENGTHS.length];
        for (int i = 0; i < line.length(); i++) {
            lengths[i] = line.charAt(i);
        }
        System.arraycopy(EXTRA_LENGTHS, 0, lengths, line.length(), EXTRA_LENGTHS.length);
        return lengths;
    }

    private static int[] makeDefault() {
        int[] numbers = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = i;
        }
        return numbers;
    }

    private static String computeHash(int[] numbers, int[] lengths) {
        int index = 0;
        int skip = 0;
        for (int i = 0; i < HASH_ROUNDS; i++) {
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
        Preconditions.checkArgument(numbers.length % BLOCK_SIZE == 0);
        int numBlocks = numbers.length / BLOCK_SIZE;
        int[] blocks = new int[numBlocks];
        for (int i = 0; i < numBlocks; i++) {
            int blockVal = 0; // 0 ^ X = X, so safe starting point
            for (int offset = 0; offset < BLOCK_SIZE; offset++) {
                blockVal ^= numbers[(BLOCK_SIZE * i) + offset];
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
}
