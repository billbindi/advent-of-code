package advent2017;

import com.google.common.collect.Iterables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Day16_1 {

    private static final String FILENAME = "2017/day16_input.txt";

    public static void main(String[] args) throws IOException {
        String line = Iterables.getOnlyElement(Files.readAllLines(Paths.get(FILENAME)));
        String result = dance(Objects.requireNonNull(line));
        System.out.println(result);
    }

    private static String dance(String line) {
        String dancers = "abcdefghijklmnop";
        for (String step : line.split(",")) {
            step = step.trim();
            switch (step.charAt(0)) {
                case 's':
                    int spinNum = Integer.parseInt(step.substring(1));
                    dancers = spin(dancers, spinNum);
                    break;
                case 'x':
                    String[] exchangeParts = step.substring(1).split("/");
                    int exchangeIndex1 = Integer.parseInt(exchangeParts[0]);
                    int exchangeIndex2 = Integer.parseInt(exchangeParts[1]);
                    dancers = swap(dancers, exchangeIndex1, exchangeIndex2);
                    break;
                case 'p':
                    String[] partnerParts = step.substring(1).split("/");
                    int partnerIndex1 = dancers.indexOf(partnerParts[0]);
                    int partnerIndex2 = dancers.indexOf(partnerParts[1]);
                    dancers = swap(dancers, partnerIndex1, partnerIndex2);
                    break;
                default:
                    throw new IllegalStateException("COULD NOT PARSE STEP: " + step);
            }
        }
        return dancers;
    }

    private static String spin(String dancers, int spinNum) {
        int breakIndex = dancers.length() - spinNum;
        return dancers.substring(breakIndex) + dancers.substring(0, breakIndex);
    }

    private static String swap(String dancers, int index1, int index2) {
        if (index1 == index2) {
            return dancers;
        } else {
            int minIndex = Math.min(index1, index2);
            int maxIndex = Math.max(index1, index2);
            return dancers.substring(0, minIndex)
                    + dancers.charAt(maxIndex)
                    + dancers.substring(minIndex + 1, maxIndex)
                    + dancers.charAt(minIndex)
                    + dancers.substring(maxIndex + 1);
        }
    }
}
