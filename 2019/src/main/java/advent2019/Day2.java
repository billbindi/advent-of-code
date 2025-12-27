package advent2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class Day2 {
    private static final String INPUT_FILENAME = "2019/day2_input.txt";
    private static final Path INPUT_PATH = Path.of(INPUT_FILENAME);

    private static final int PART_2_TARGET = 19690720;

    public static void main(String[] args) throws IOException {
        System.out.println(part1(Files.lines(INPUT_PATH)));
        System.out.println(part2(Files.lines(INPUT_PATH)));
    }

    private static long part1(Stream<String> lines) {
        IntcodeComputer computer = IntcodeComputer.builder()
                .initialMemory(lines.findFirst().orElseThrow())
                .build();
        computer.runWithNounVerb(12, 2);
        return computer.getOutput();
    }

    private static int part2(Stream<String> lines) {
        IntcodeComputer computer = IntcodeComputer.builder()
                .initialMemory(lines.findFirst().orElseThrow())
                .build();
        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                computer.reset();
                computer.runWithNounVerb(noun, verb);
                if (computer.getOutput() == PART_2_TARGET) {
                    return (100 *  noun) + verb;
                }
            }
        }
        // not found
        return -1;
    }

}
