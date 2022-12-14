package advent2017;

import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day8_1 {

    private static final String FILENAME = "2017/day8_input.txt";

    public static final String REGISTER_GROUP = "register";
    public static final String OPERATION_GROUP = "operation";
    public static final String AMOUNT_GROUP = "amount";
    public static final String CHECK_REGISTER_GROUP = "check";
    public static final String CONDITION_GROUP = "condition";
    public static final String VALUE_GROUP = "value";
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("(?<" + REGISTER_GROUP + ">\\w+)\\s*(?<" + OPERATION_GROUP + ">\\w+)\\s*(?<" + AMOUNT_GROUP + ">-?\\d+)\\s*if\\s*(?<" + CHECK_REGISTER_GROUP + ">\\w+)\\s*(?<" + CONDITION_GROUP + ">[<>!=]+)\\s*(?<" + VALUE_GROUP + ">-?\\d+)");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        int max = computeRegisters(lines);
        System.out.println(max);
    }

    private static int computeRegisters(List<String> lines) {
        Map<String, Integer> registers = new HashMap<>();
        for (String line : lines) {
            Matcher matcher = INSTRUCTION_PATTERN.matcher(line);
            if (matcher.matches()) {
                // parse out line
                String register = matcher.group(REGISTER_GROUP);
                String operation = matcher.group(OPERATION_GROUP);
                int amount = Integer.parseInt(matcher.group(AMOUNT_GROUP));
                String checkRegister = matcher.group(CHECK_REGISTER_GROUP);
                String condition = matcher.group(CONDITION_GROUP);
                int value = Integer.parseInt(matcher.group(VALUE_GROUP));

                // make sure registers exist
                if (!registers.containsKey(register)) {
                    registers.put(register, 0);
                }
                if (!registers.containsKey(checkRegister)) {
                    registers.put(checkRegister, 0);
                }

                // eval condition
                if (conditionIsTrue(registers, checkRegister, condition, value)) {
                    int newValue;
                    if (operation.equalsIgnoreCase("inc")) {
                        newValue = registers.get(register) + amount;
                    } else if (operation.equalsIgnoreCase("dec")) {
                        newValue = registers.get(register) - amount;
                    } else {
                        throw new IllegalStateException("UNKNOWN OPERATION TO APPLY FROM LINE: " + line);
                    }
                    registers.put(register, newValue);
                }
            } else {
                throw new IllegalStateException("COULD NOT PARSE LINE: " + line);
            }
        }

        return maxValue(registers);
    }

    private static boolean conditionIsTrue(Map<String, Integer> registers, String checkRegister, String condition, int value) {
        int registerValue = registers.get(checkRegister);
        switch (condition) {
            case "<":
                return registerValue < value;
            case "<=":
                return registerValue <= value;
            case ">":
                return registerValue > value;
            case ">=":
                return registerValue >= value;
            case "==":
                return registerValue == value;
            case "!=":
                return registerValue != value;
            default:
                throw new IllegalStateException("UNKNOWN CONDITIONAL: " + condition);
        }
    }

    private static int maxValue(Map<String, Integer> registers) {
        return registers.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();
    }
}
