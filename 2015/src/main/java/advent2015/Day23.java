package advent2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day23 {

    private static final String FILENAME = "day23_input.txt";
    private static final String INSTRUCTION_DELIMETER = " ";

    public static void main(String[] args) throws IOException {
        List<String> instructions = Files.readAllLines(Paths.get(FILENAME));
        int regA = 0;
        int regB = 0;
        int index = 0;
        String register;
        int jump;
        while (index >= 0 && index < instructions.size()) {
            String line = instructions.get(index);
            String[] parts = line.split(INSTRUCTION_DELIMETER);
            Instruction instruction = Instruction.fromString(parts[0]);
            switch (instruction) {
                case HLF:
                    register = parts[1];
                    if ("a".equalsIgnoreCase(register)) {
                        regA = regA / 2;
                    } else {
                        regB = regB / 2;
                    }
                    index++;
                    break;
                case TPL:
                    register = parts[1];
                    if ("a".equalsIgnoreCase(register)) {
                        regA = regA * 3;
                    } else {
                        regB = regB * 3;
                    }
                    index++;
                    break;
                case INC:
                    register = parts[1];
                    if ("a".equalsIgnoreCase(register)) {
                        regA++;
                    } else {
                        regB++;
                    }
                    index++;
                    break;
                case JMP:
                    jump = Integer.parseInt(parts[1]);
                    index += jump;
                    break;
                case JIE:
                    register = parts[1].substring(0, 1);
                    jump = Integer.parseInt(parts[2]);
                    if ("a".equalsIgnoreCase(register) && regA % 2 == 0) {
                        index += jump;
                    } else if ("b".equalsIgnoreCase(register) && regB % 2 == 0) {
                        index += jump;
                    } else {
                        index++;
                    }
                    break;
                case JIO:
                    register = parts[1].substring(0, 1);
                    jump = Integer.parseInt(parts[2]);
                    if ("a".equalsIgnoreCase(register) && regA == 1) {
                        index += jump;
                    } else if ("b".equalsIgnoreCase(register) && regB == 1) {
                        index += jump;
                    } else {
                        index++;
                    }
                    break;
            }
        }
        System.out.println("a: " + regA + ", b: " + regB);
    }

    private enum Instruction {
        HLF, TPL, INC, JMP, JIE, JIO;

        static Instruction fromString(String input) {
            for (Instruction option : values()) {
                if (option.name().equalsIgnoreCase(input)) {
                    return option;
                }
            }
            throw new IllegalArgumentException("Unknown instruction: " + input);
        }
    }
}
