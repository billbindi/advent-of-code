package advent2016;

public class Day19_1 {
    private static final int NUM_ELVES = 3014387;

    public static void main(String[] args) {
        System.out.println(solve());
    }

    private static int solve() {
        int elves = NUM_ELVES;
        int leader = 1;
        int rounds = 0;
        while (elves > 1) {
            if (elves % 2 == 0) {
                elves = elves / 2;
            } else {
                leader += Math.pow(2, rounds + 1);
                elves = (elves - 1) / 2;
            }
            rounds++;
        }
        return leader;
    }
}
