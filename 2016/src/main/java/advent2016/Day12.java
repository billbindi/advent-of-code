package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day12 {
    private static final int[] REGISTERS = new int[]{0, 0, 0, 0};
    //private static final int[] REGISTERS_2 = new int[]{0, 0, 1, 0};

    private static final String FILENAME = "2016/day12_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        for (int index = 0; index < content.size();) {
            String instruction = content.get(index);
            String[] parts = instruction.split(" ");
            String function = parts[0];

            switch (function) {
                case "cpy":
                    REGISTERS[registerIndex(parts[2])] = valueAtRegisterOrInt(parts[1]);
                    index++;
                    break;
                case "inc":
                    REGISTERS[registerIndex(parts[1])]++;
                    index++;
                    break;
                case "dec":
                    REGISTERS[registerIndex(parts[1])]--;
                    index++;
                    break;
                case "jnz":
                    int test = valueAtRegisterOrInt(parts[1]);
                    if (test != 0) {
                        int jump = Integer.parseInt(parts[2]);
                        index += jump;
                    } else {
                        index++;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown function [" + function + "] of line: " + instruction);
            }
        }
        return REGISTERS[0];
    }

    private static int registerIndex(String reg) {
        return reg.charAt(0) - 'a';
    }

    private static int valueAtRegisterOrInt(String value) {
        if (value.matches("\\d+")) {
            return Integer.parseInt(value);
        } else {
            return REGISTERS[registerIndex(value)];
        }
    }
}
