package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2_2 {

    private static final String FILENAME = "2017/day2_input.txt";

    public static void main(String[] args) throws IOException {
        int total = Files.readAllLines(Paths.get(FILENAME)).stream()
                .mapToInt(Day2_2::evenlyDivide)
                .sum();
        System.out.println(total);
    }

    private static int evenlyDivide(String line) {
        Integer[] ints = Arrays.stream(line.split("\\s+"))
                .map(Integer::parseInt).
                toArray(Integer[]::new);
        for (int i = 0; i < ints.length - 1; i++) {
            for (int j = i + 1; j < ints.length; j++) {
                Integer first = ints[i];
                Integer second = ints[j];
                if (first % second == 0){
                    return first / second;
                } else if (second % first == 0) {
                    return second / first;
                }
            }
        }
        throw new IllegalStateException("THINGS DID NOT DIVIDE");
    }
}
