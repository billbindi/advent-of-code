package advent2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class Day5 {
    private static final String INPUT_FILENAME = "2019/day5_input.txt";
    private static final Path INPUT_PATH = Path.of(INPUT_FILENAME);

    public static void main(String[] args) throws IOException {
        System.out.println(part1(Files.lines(INPUT_PATH)));
        System.out.println(part2(Files.lines(INPUT_PATH)));
    }

    private static long part1(Stream<String> lines) {
        IntcodeComputer computer = IntcodeComputer.builder()
                .initialMemory(lines.findFirst().orElseThrow())
                .input(1)
                .build();
        computer.run();
        return computer.getOutput();
    }

    private static long part2(Stream<String> lines) {
        IntcodeComputer computer = IntcodeComputer.builder()
                .initialMemory(lines.findFirst().orElseThrow())
                .input(5)
                .build();
        computer.run();
        return computer.getOutput();
    }
}
