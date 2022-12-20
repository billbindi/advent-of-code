package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23_1 {

    private static final String FILENAME = "2017/day23_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        long frequency = perform(lines);
        System.out.println(frequency);
    }

    private static long perform(List<String> lines) {
        Map<String, Long> registers = new HashMap<>();
        int index = 0;
        long count = 0;
        while (index >= 0 && index < lines.size()) {
            String line = lines.get(index);
            String[] parts = line.split(" ");
            String instruction = parts[0];
            switch (instruction) {
                case "set":
                    registers.put(parts[1], value(registers, parts[2]));
                    index++;
                    break;
                case "sub":
                    long initialAdd = value(registers, parts[1]);
                    long addend = value(registers, parts[2]);
                    registers.put(parts[1], initialAdd - addend);
                    index++;
                    break;
                case "mul":
                    long initialMul = value(registers, parts[1]);
                    long factor = value(registers, parts[2]);
                    registers.put(parts[1], initialMul * factor);
                    index++;
                    count++;
                    break;
                case "jnz":
                    if (value(registers, parts[1]) != 0) {
                        index += value(registers, parts[2]);
                    } else {
                        index++;
                    }
                    break;
                default:
                    throw new IllegalStateException("COULD NOT PARSE INSTRUCTION: " + instruction);
            }
        }
        return count;
    }

    private static long value(Map<String, Long> registers, String input) {
        if (input.matches("-?\\d+")) {
            return Long.parseLong(input);
        } else {
            if (!registers.containsKey(input)) {
                registers.put(input, 0L);
            }
            return registers.get(input);
        }
    }
}
