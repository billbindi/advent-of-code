package advent2019;

import com.google.common.collect.Collections2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@SuppressWarnings("UnstableApiUsage") // guava permutations
public final class Day7 {
    private static final String INPUT_FILENAME = "2019/day7_input.txt";
    private static final Path INPUT_PATH = Path.of(INPUT_FILENAME);

    public static void main(String[] args) throws IOException {
        System.out.println(part1(Files.lines(INPUT_PATH)));
        System.out.println(part2(Files.lines(INPUT_PATH)));
    }

    private static int part1(Stream<String> lines) {
        Collection<List<Integer>> permutations = Collections2.permutations(List.of(0, 1, 2, 3, 4));
        int maxThrust = Integer.MIN_VALUE;
        String computerString = lines.findFirst().orElseThrow();
        for (List<Integer> permutation : permutations) {
            int thrust = tryPermutation(computerString, permutation);
            if (thrust > maxThrust) {
                maxThrust = thrust;
            }
        }
        return maxThrust;
    }

    private static int tryPermutation(String computerString, List<Integer> phaseSignals) {
        AtomicInteger outputValue = new AtomicInteger(0);
        for (int phaseSignal : phaseSignals) {
            IntcodeComputer computer = IntcodeComputer.builder()
                    .initialMemory(computerString)
                    .input(phaseSignal)
                    .input(outputValue.get())
                    .outputConsumer(outputValue::set)
                    .build();
            computer.run();
        }
        return outputValue.get();
    }

    private static int part2(Stream<String> lines) {
        return 0;
    }
}
