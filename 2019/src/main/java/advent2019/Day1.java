package advent2019;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Day1 {
    private static final String INPUT_FILENAME = "2019/day1_input.txt";
    private static final Path INPUT_PATH = Path.of(INPUT_FILENAME);

    public static void main(String[] args) throws IOException {
        System.out.println(part1(Files.lines(INPUT_PATH)));
        // System.out.println(part2(Files.lines(INPUT_PATH)));
    }

    private static int part1(Stream<String> lines) {
        return lines.mapToInt(Integer::parseInt).map(Day1::fuelNeeded).sum();
    }

    private static int fuelNeeded(int mass) {
        Preconditions.checkArgument(mass > 8, "Will have negative fuel needed!");
        return (mass / 3) - 2;
    }
}
