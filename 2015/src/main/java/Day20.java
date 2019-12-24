public class Day20 {

    private static final int TARGET = 34000000;
    private static final int PRESENTS_PER_ELF = 10;

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
                total += (PRESENTS_PER_ELF * i);
                total += (PRESENTS_PER_ELF * (house/i));
            }
        }
        if (intSqrt * intSqrt == house) {
            total -= (PRESENTS_PER_ELF * intSqrt);
        }
        return total;
    }
}
