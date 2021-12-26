package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21_2 {
    private final static String SWAP_POSITION_REGEX = "swap position (?<first>\\d+) with position (?<second>\\d+)";
    private final static Pattern SWAP_POSITION_PATTERN = Pattern.compile(SWAP_POSITION_REGEX);

    private final static String SWAP_LETTER_REGEX = "swap letter (?<first>\\w) with letter (?<second>\\w)";
    private final static Pattern SWAP_LETTER_PATTERN = Pattern.compile(SWAP_LETTER_REGEX);

    private final static String ROTATE_STEPS_REGEX = "rotate (?<direction>\\w+) (?<num>\\d+) steps?";
    private final static Pattern ROTATE_STEPS_PATTERN = Pattern.compile(ROTATE_STEPS_REGEX);

    private final static String ROTATE_LETTER_REGEX = "rotate based on position of letter (?<letter>\\w)";
    private final static Pattern ROTATE_LETTER_PATTERN = Pattern.compile(ROTATE_LETTER_REGEX);

    private final static String REVERSE_POSITION_REGEX = "reverse positions (?<first>\\d+) through (?<second>\\d+)";
    private final static Pattern REVERSE_POSITION_PATTERN = Pattern.compile(REVERSE_POSITION_REGEX);

    private final static String MOVE_POSITION_REGEX = "move position (?<first>\\d+) to position (?<second>\\d+)";
    private final static Pattern MOVE_POSITION_PATTERN = Pattern.compile(MOVE_POSITION_REGEX);

    private static final String KEY = "fbgdceah";

    private static final String FILENAME = "2016/day21_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static String solve(List<String> content) {
        String password = KEY;
        Collections.reverse(content);
        for (String instruction : content) {
            Matcher swapPosition = SWAP_POSITION_PATTERN.matcher(instruction);
            Matcher swapLetter = SWAP_LETTER_PATTERN.matcher(instruction);
            Matcher rotateSteps = ROTATE_STEPS_PATTERN.matcher(instruction);
            Matcher rotateLetter = ROTATE_LETTER_PATTERN.matcher(instruction);
            Matcher reversePosition = REVERSE_POSITION_PATTERN.matcher(instruction);
            Matcher movePosition = MOVE_POSITION_PATTERN.matcher(instruction);

            if (swapPosition.find()) {
                int first = Integer.parseInt(swapPosition.group("first"));
                int second = Integer.parseInt(swapPosition.group("second"));
                password = swapPosition(password, first, second);
            } else if (swapLetter.find()) {
                char first = swapLetter.group("first").charAt(0);
                char second = swapLetter.group("second").charAt(0);
                password = swapLetter(password, first, second);
            } else if (rotateSteps.find()) {
                // opposite rotate
                String direction = rotateSteps.group("direction");
                int numSteps = Integer.parseInt(rotateSteps.group("num"));
                password = rotateSteps(password, direction, numSteps);
            } else if (rotateLetter.find()) {
                // yikes
                char letter = rotateLetter.group("letter").charAt(0);
                password = rotateLetter(password, letter);
            } else if (reversePosition.find()) {
                int first = Integer.parseInt(reversePosition.group("first"));
                int second = Integer.parseInt(reversePosition.group("second"));
                password = reversePosition(password, first, second);
            } else if (movePosition.find()) {
                // swap it
                int first = Integer.parseInt(movePosition.group("first"));
                int second = Integer.parseInt(movePosition.group("second"));
                password = movePosition(password, second, first);
            } else {
                throw new IllegalStateException("Could not parse line: " + instruction);
            }
        }
        return password;
    }

    private static String swapPosition(String input, int first, int second) {
        StringBuilder result = new StringBuilder(input);
        char temp = result.charAt(first);
        result.setCharAt(first, result.charAt(second));
        result.setCharAt(second, temp);
        return result.toString();
    }

    private static String swapLetter(String input, char first, char second) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c == first) {
                result.append(second);
            } else if (c == second) {
                result.append(first);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private static String rotateSteps(String input, String direction, int numSteps) {
        if (direction.equalsIgnoreCase("right")) {
            return rotateLeft(input, numSteps);
        } else if (direction.equalsIgnoreCase("left")) {
            return rotateRight(input, numSteps);
        } else {
            throw new IllegalArgumentException("Bad direction: " + direction);
        }
    }

    private static String rotateRight(String input, int numSteps) {
        StringBuilder result = new StringBuilder();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            result.append(input.charAt((i - numSteps + length + length) % length));
        }
        return result.toString();
    }

    private static String rotateLeft(String input, int numSteps) {
        StringBuilder result = new StringBuilder();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            result.append(input.charAt((i + numSteps) % length));
        }
        return result.toString();
    }

    private static String rotateLetter(String input, char letter) {
        int endIndex = input.indexOf(letter);
        int startIndex = computeStart(input, endIndex);
        int rotations = (endIndex - startIndex + input.length()) % input.length();
        return rotateLeft(input, rotations);
    }

    private static int computeStart(String input, int endIndex) {
        for (int i = 0; i < input.length(); i++) {
            int potentialStart = i + i + 1 + (i >= 4 ? 1 : 0);
            if (potentialStart % input.length() == endIndex) {
                return i;
            }
        }
        throw new IllegalStateException("No start possible for [" + endIndex + "] in line: " + input);
    }

    private static String reversePosition(String input, int first, int second) {
        String middle = input.substring(first, second + 1);
        String reverse = new StringBuilder(middle).reverse().toString();
        return input.substring(0, first) +
                reverse +
                input.substring(second + 1);
    }

    private static String movePosition(String input, int first, int second) {
        if (first == second) {
            return input;
        } else if (first < second) {
            return input.substring(0, first) +
                    input.substring(first + 1, second + 1) +
                    input.charAt(first) +
                    input.substring(second + 1);
        } else {
            return input.substring(0, second) +
                    input.charAt(first) +
                    input.substring(second, first) +
                    input.substring(first + 1);
        }
    }
}
