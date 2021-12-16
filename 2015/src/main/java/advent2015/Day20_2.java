package advent2015;

public class Day20_2 {

    private static final int TARGET = 34000000;
    private static final int PRESENTS_PER_ELF = 11;
    private static final int MAX_PRESENTS = 50;

    public static void main(String[] args) {
        int current = 0;
        int house = 0;
        while (current < TARGET) {
            house++;
            current = value(house);
        }
        System.out.println("House: " + house + ", val: " + current);
    }

    private static int value(int house) {
        int total = 0;
        double sqrt = Math.sqrt(house);
        int intSqrt = (int) sqrt;
        for (int i = 1; i <= intSqrt; i++) {
            if (house % i == 0) {
                int inverse = house / i;
                if (house <= (inverse * MAX_PRESENTS)) {
                    total += (PRESENTS_PER_ELF * inverse);
                }
                if (house <= (i * MAX_PRESENTS) && i != inverse) {
                    total += (PRESENTS_PER_ELF * i);
                }
            }
        }
        return total;
    }
}
