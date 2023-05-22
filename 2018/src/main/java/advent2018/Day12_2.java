package advent2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12_2 {

    private static final String FILENAME = "2018/day12_input.txt";

    private static final String STATE_GROUP = "state";
    private static final Pattern INITIAL_STATE_PATTERN = Pattern.compile("initial state: (?<" + STATE_GROUP + ">[#.]*)");

    private static final String INPUT_GROUP = "input";
    private static final String OUTPUT_GROUP = "output";
    private static final Pattern RULE_PATTERN = Pattern.compile("(?<" + INPUT_GROUP + ">[#.]{5}) => (?<" + OUTPUT_GROUP + ">[#.])");

    private static final long NUM_GENERATIONS = 50_000_000_000L;
    private static final int PREPEND_LENGTH = 2;
    private static final int APPEND_LENGTH = 10000;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static long solve(List<String> lines) {
        String state = extendPots(parseInitialState(lines.get(0)));
        Map<String, Character> rules = parseRules(lines);

        for (long generation = 1; generation <= NUM_GENERATIONS; generation++) {
            state = step(state, rules);
            System.out.println("GENERATION NUMBER: " + generation);
            if (state.endsWith("#")) {
                System.out.println("UH-OH!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }

        return countPots(state);
    }

    private static String parseInitialState(String line) {
        Matcher m = INITIAL_STATE_PATTERN.matcher(line);
        if (m.matches()) {
            return m.group(STATE_GROUP);
        } else {
            throw new IllegalArgumentException("COULD NOT PARSE INITIAL STATE: " + line);
        }
    }

    private static String extendPots(String state) {
        return ".".repeat(PREPEND_LENGTH) + state + ".".repeat(APPEND_LENGTH);
    }

    private static Map<String, Character> parseRules(List<String> lines) {
        Map<String, Character> rules = new HashMap<>();
        for (String rule : lines.subList(2, lines.size())) {
            Matcher m = RULE_PATTERN.matcher(rule);
            if (m.matches()) {
                rules.put(m.group(INPUT_GROUP), m.group(OUTPUT_GROUP).charAt(0));
            } else {
                throw new IllegalArgumentException("COULD NOT PARSE RULE: " + rule);
            }
        }
        return rules;
    }

    private static String step(String state, Map<String, Character> rules) {
        StringBuilder nextGeneration = new StringBuilder();
        for (int index = 0; index < state.length(); index++) {
            nextGeneration.append(getPot(state, index, rules));
        }
        return nextGeneration.toString();
    }

    private static char getPot(String state, int index, Map<String, Character> rules) {
        String setting = setting(state, index);
        return rules.getOrDefault(setting, '.');
    }

    private static String setting(String state, int rootIndex) {
        StringBuilder setting = new StringBuilder();
        for (int index = rootIndex - 2; index <= rootIndex + 2; index++) {
            if (index < 0 || index >= state.length()) {
                setting.append('.');
            } else {
                setting.append(state.charAt(index));
            }
        }
        return setting.toString();
    }

    private static long countPots(String state) {
        long sum = 0;
        for (int i = 0; i < state.length(); i++) {
            if (state.charAt(i) == '#') {
                sum += (i - PREPEND_LENGTH);
            }
        }
        return sum;
    }
}
