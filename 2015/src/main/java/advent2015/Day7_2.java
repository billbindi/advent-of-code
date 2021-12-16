package advent2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day7_2 {

    private static final String FILENAME = "day7_2_input.txt";

    private static final Map<String, Integer> map = new HashMap<>();
    private static final String TARGET = "a";

    public static void main(String[] args) throws IOException {
        List<Instruction> instructions = Files.readAllLines(Paths.get(FILENAME)).stream()
                .map(Day7_2::parseLine)
                .collect(Collectors.toList());

        int index = 0;
        while (!map.containsKey(TARGET)) {
            Instruction instruction = instructions.get(index);
            apply(map, instruction);
            index = (index + 1) % instructions.size();
        }
        System.out.println(map.get(TARGET));
    }

    private static Instruction parseLine(String line) {
        String[] parts = line.split(" ");
        String output = parts[parts.length - 1];

        String left = null;
        String right = null;
        Operation op = Operation.NONE;
        if (parts.length == 3) {
            left = parts[0];
        } else if (line.startsWith("NOT")) {
            op = Operation.NOT;
            left = parts[1];
        } else {
            left = parts[0];
            right = parts[2];
            op = Operation.fromString(parts[1]);
        }
        Instruction instruction = new Instruction(left, right, output, op);
        return instruction;
    }

    private static void apply(Map<String, Integer> map, Instruction instruction) {
        if (map.containsKey(instruction.output)) {
            return;
        }
        Integer left = convert(instruction.left);
        Integer right = convert(instruction.right);
        switch (instruction.op) {
            case AND:
                if (left != null && right != null) {
                    map.put(instruction.output, left & right);
                }
                break;
            case NOT:
                if (left != null) {
                    map.put(instruction.output, ~left);
                }
                break;
            case OR:
                if (left != null && right != null) {
                    map.put(instruction.output, left | right);
                }
                break;
            case LSHIFT:
                if (left != null && right != null) {
                    map.put(instruction.output, left << right);
                }
                break;
            case RSHIFT:
                if (left != null && right != null) {
                    map.put(instruction.output, left >> right);
                }
                break;
            case NONE:
                if (left != null) {
                    map.put(instruction.output, left);
                }
                break;
        }
    }

    private static Integer convert(String str) {
        if (str == null) {
            return null;
        }
        if (str.matches("\\d+")) {
            return Integer.parseInt(str);
        } else {
            return map.get(str);
        }
    }

    private static class Instruction {
        String left;
        String right;
        String output;
        Operation op;


        private Instruction(String left, String right, String output, Operation op) {
            this.left = left;
            this.right = right;
            this.output = output;
            this.op = op;
        }

        @Override
        public String toString() {
            return "Instruction{" +
                    "left='" + left + '\'' +
                    ", right='" + right + '\'' +
                    ", output='" + output + '\'' +
                    ", op=" + op +
                    '}';
        }
    }

    private enum Operation {
        AND, NOT, OR, LSHIFT, RSHIFT, NONE;

        static Operation fromString(String s) {
            for (Operation op : values()) {
                if (s.equals(op.name())) {
                    return op;
                }
            }
            return null;
        }
    }
}
