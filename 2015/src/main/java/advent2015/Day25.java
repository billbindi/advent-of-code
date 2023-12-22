package advent2015;

public class Day25 {

    private static final long START = 20151125;
    private static final int ROW = 2981;
    private static final int COLUMN = 3075;

    public static void main(String[] args) {
        System.out.println(solve());
    }

    private static long solve() {
        long ret = START;
        int row = 1;
        int col = 1;
        int maxRow = 1;
        while (row != ROW || col != COLUMN) {
            ret = next(ret);

            row--;
            col++;

            if (row == 0) {
                row = maxRow + 1;
                maxRow++;
                col = 1;
            }
        }
        return ret;
    }

    private static long next(long curr) {
        return (curr * 252533) % 33554393;
    }
}
