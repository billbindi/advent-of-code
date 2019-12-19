import static java.lang.Integer.min;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day2 {

    private static final String FILENAME = "day2_input.txt";

    public static void main(String[] args) throws IOException {
        int total = Files.readAllLines(Paths.get(FILENAME)).stream()
                .map(Day2::parseLine)
                .mapToInt(Dim::surfaceArea)
                .sum();
        System.out.println(total);
    }

    private static Dim parseLine(String line) {
        String[] dims = line.split("x");
        return new Dim(
                Integer.parseInt(dims[0]),
                Integer.parseInt(dims[1]),
                Integer.parseInt(dims[2]));
    }

    private static class Dim {
        int l,w,h;

        Dim(int l, int w, int h) {
            this.l = l;
            this.w = w;
            this.h = h;
        }

        int surfaceArea() {
            int lw = l * w;
            int lh = l * h;
            int wh = w * h;
            int spare = min(lw, min(lh, wh));
            return (2 * (lw + lh + wh)) + spare;
        }
    }
}
