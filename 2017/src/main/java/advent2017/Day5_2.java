package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day5_2 {

    private static final String FILENAME = "2017/day5_input.txt";

    public static void main(String[] args) throws IOException {
        int[] instructions = parseInstructions(Files.readAllLines(Paths.get(FILENAME)));
        int count = countSteps(instructions);
        System.out.println(count);
    }

    private static int[] parseInstructions(List<String> lines) {
        return lines.stream()
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static int countSteps(int[] instructions) {
        int index = 0;
        int steps = 0;
        while (index >= 0 && index < instructions.length) {
            int jump = instructions[index];
            if (jump >= 3) {
                instructions[index]--;
            } else {
                instructions[index]++;
            }
            index += jump;
            steps++;
        }
        return steps;
    }
}
