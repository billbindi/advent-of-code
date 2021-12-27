package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day25 {
    private static final String NUMBER_REGEX = "-?\\d+";
    private static final String REGISTER_REGEX = "[a-d]";

    private static final int[] REGISTERS = new int[4];

    private static final String FILENAME = "2016/day25_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        int start = 0;
        while (start < Integer.MAX_VALUE) {
            REGISTERS[0] = start;
            REGISTERS[1] = REGISTERS[2] = REGISTERS[3] = 0;
            if (runInstructions(content)) {
                return start;
            }
            start++;
        }
        return -1;
    }

    private static boolean runInstructions(List<String> content) {
        int outputs = 0;
        int size = content.size();
        for (int index = 0; index < size;) {
            String instruction = content.get(index);
            String[] parts = instruction.split(" ");
            String function = parts[0];

            switch (function) {
                case "cpy":
                    if (parts[2].matches(REGISTER_REGEX)) {
                        REGISTERS[registerIndex(parts[2])] = valueAtRegisterOrInt(parts[1]);
                    }
                    index++;
                    break;
                case "inc":
                    if (parts[1].matches(REGISTER_REGEX)) {
                        REGISTERS[registerIndex(parts[1])]++;
                    }
                    index++;
                    break;
                case "dec":
                    if (parts[1].matches(REGISTER_REGEX)) {
                        REGISTERS[registerIndex(parts[1])]--;
                    }
                    index++;
                    break;
                case "jnz":
                    int test = valueAtRegisterOrInt(parts[1]);
                    if (test != 0) {
                        int jump = valueAtRegisterOrInt(parts[2]);
                        index += jump;
                    } else {
                        index++;
                    }
                    break;
                case "tgl":
                    int jump = valueAtRegisterOrInt(parts[1]);
                    toggle(content, index + jump);
                    index++;
                    break;
                case "out":
                    int output = valueAtRegisterOrInt(parts[1]);
                    if (output != (outputs % 2)) {
                        return false;
                    }

                    outputs++;
                    if (outputs >= 20) {
                        return true;
                    }

                    index++;
                    break;
                default:
                    throw new IllegalStateException("Unknown function [" + function + "] of line: " + instruction);
            }
        }
        return false;
    }

    private static int registerIndex(String reg) {
        return reg.charAt(0) - 'a';
    }

    private static int valueAtRegisterOrInt(String value) {
        if (value.matches(NUMBER_REGEX)) {
            return Integer.parseInt(value);
        } else {
            return REGISTERS[registerIndex(value)];
        }
    }

    private static void toggle(List<String> content, int index) {
        if (index >= 0 && index < content.size()) {
            String instruction = content.get(index);
            String[] parts = instruction.split(" ");
            String function = parts[0];
            String newInstruction;

            switch (function) {
                case "cpy":
                    newInstruction = "jnz " + parts[1] + " " + parts[2];
                    break;
                case "inc":
                    newInstruction = "dec " + parts[1];
                    break;
                case "dec":
                case "tgl":
                    newInstruction = "inc " + parts[1];
                    break;
                case "jnz":
                    newInstruction = "cpy " + parts[1] + " " + parts[2];
                    break;
                default:
                    throw new IllegalStateException("Unknown function [" + function + "] of line: " + instruction);
            }
            content.remove(index);
            content.add(index, newInstruction);
        }
    }

}
