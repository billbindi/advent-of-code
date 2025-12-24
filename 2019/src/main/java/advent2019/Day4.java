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
        return 0;
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
}
