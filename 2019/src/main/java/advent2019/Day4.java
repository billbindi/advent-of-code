package advent2019;

public final class Day4 {
    private static final int MIN = 147981;
    private static final int MAX = 691423;

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }

    private static int part1() {
        int count = 0;
        for (int password = MIN; password <= MAX; password++) {
            if (adjacentDuplicate(password) && monotonicIncrease(password)) {
                count++;
            }
        }
        return count;
    }

    private static int part2() {
        int count = 0;
        for (int password = MIN; password <= MAX; password++) {
            if (adjacentDuplicateContained(password) && monotonicIncrease(password)) {
                count++;
            }
        }
        return count;
    }

    private static boolean adjacentDuplicate(int password) {
        int[] digits = String.valueOf(password).chars().map(Character::getNumericValue).toArray();
        for (int i = 0; i < digits.length - 1; i++) {
            if (digits[i] == digits[i + 1]) {
                return true;
            }
        }
        return false;
    }

    private static boolean monotonicIncrease(int password) {
        int[] digits = String.valueOf(password).chars().map(Character::getNumericValue).toArray();
        for (int i = 0; i < digits.length - 1; i++) {
            if (digits[i] > digits[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static boolean adjacentDuplicateContained(int password) {
        int[] digits = String.valueOf(password).chars().map(Character::getNumericValue).toArray();
        int pointer = 0;
        while (pointer < digits.length - 1) {
            int duplicates = numDuplicates(digits, pointer);
            if (duplicates == 2) {
                return true;
            }
            pointer += duplicates;
        }
        return false;
    }

    private static int numDuplicates(int[] digits, int pointer) {
        int count = 1;
        for (int index = pointer + 1; index < digits.length; index++) {
            if (digits[index] == digits[pointer]) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}
