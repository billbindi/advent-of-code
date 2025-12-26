package advent2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public final class Day5 {
    private static final String INPUT_FILENAME = "2019/day5_input.txt";
    private static final Path INPUT_PATH = Path.of(INPUT_FILENAME);

    public static void main(String[] args) throws IOException {
        System.out.println(part1(Files.lines(INPUT_PATH)));
        System.out.println(part2(Files.lines(INPUT_PATH)));
    }

    private static int part1(Stream<String> lines) {
        IntcodeComputer computer = IntcodeComputer.fromLineWithInput(lines.findFirst().orElseThrow(), Optional.of(1));
        computer.run();
        return computer.getOutput();
    }

    private static int part2(Stream<String> lines) {
        IntcodeComputer computer = IntcodeComputer.fromLineWithInput(lines.findFirst().orElseThrow(), Optional.of(5));
        computer.run();
        return computer.getOutput();
    }
}
