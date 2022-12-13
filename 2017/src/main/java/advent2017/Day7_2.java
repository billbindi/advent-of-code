package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7_2 {

    private static final String FILENAME = "2017/day7_input.txt";

    public static final String PROGRAM_GROUP = "program";
    public static final String WEIGHT_GROUP = "weight";
    public static final String SUPPORTED_GROUP = "supported";
    private static final Pattern PROGRAM_PATTERN = Pattern.compile("(?<" + PROGRAM_GROUP + ">\\w+)\\s+\\((?<" + WEIGHT_GROUP + ">\\d+)\\)(\\s*->\\s*(?<" + SUPPORTED_GROUP + ">[\\s\\w,]+))?");

    private static final String BASE = "cqmvs"; // found from Day7_1

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        findImbalance(lines);
    }

    private static void findImbalance(List<String> lines) {
        // parse
        Map<String, Program> programs = new HashMap<>(lines.size());
        for (String line : lines) {
            Program program = Program.fromLine(line);
            programs.put(program.name, program);
        }

        // start recursing
        findImbalance(programs, BASE);
    }

    private static int findImbalance(Map<String, Program> programs, String base) {
        Program program = programs.get(base);
        Set<String> supporteds = program.supported;
        if (supporteds.isEmpty()) {
            return program.weight;
        }
        int weight = program.weight;
        Map<Integer, List<String>> weights = new HashMap<>(); // counts of weights
        for (String supported : supporteds) {
            int supportedWeightTotal = findImbalance(programs, supported);
            weight += supportedWeightTotal;
            if (!weights.containsKey(supportedWeightTotal)) {
                weights.put(supportedWeightTotal, new ArrayList<>());
            }
            weights.get(supportedWeightTotal).add(supported);
        }

        // Check for imbalance.
        if (weights.keySet().size() > 1) {
            // exactly 1 imbal, so only 2 keys possible
            Iterator<Integer> weightsIterator = weights.keySet().iterator();
            int weight1 = weightsIterator.next();
            int weight2 = weightsIterator.next();

            // ASSUME that if there is an imbal there will be at least three sub
            // towers, otherwise the answer is not unique (must have at least two
            // sub towers that agree on weight to make it unique)
            int newWeight = 0;
            if (weights.get(weight1).size() == 1) {
                String imbalProgram = weights.get(weight1).get(0);
                newWeight = programs.get(imbalProgram).weight + (weight2 - weight1);
            } else {
                String imbalProgram = weights.get(weight2).get(0);
                newWeight = programs.get(imbalProgram).weight + (weight1 - weight2);
            }

            // could be printed out many times, only FIRST matters
            System.out.println("IMBALANCE FOUND: EITHER " + newWeight);
        }
        return weight;
    }

    private static class Program {
        final String name;
        final int weight;
        final Set<String> supported;

        private Program(String name, int weight, Set<String> supported) {
            this.name = name;
            this.weight = weight;
            this.supported = supported;
        }

        static Program fromLine(String line) {
            Matcher matcher = PROGRAM_PATTERN.matcher(line);
            if (matcher.matches()) {
                String program = matcher.group(PROGRAM_GROUP);
                int weight = Integer.parseInt(matcher.group(WEIGHT_GROUP));
                Set<String> supported = parseSupported(matcher.group(SUPPORTED_GROUP));
                return new Program(program, weight, supported);
            } else {
                throw new IllegalStateException("COULD NOT MATCH LINE: " + line);
            }
        }

        static Set<String> parseSupported(String supported) {
            if (supported == null || supported.isBlank()) {
                return Collections.emptySet();
            }
            return Arrays.stream(supported.split(","))
                    .map(String::strip)
                    .collect(Collectors.toSet());
        }
    }
}
