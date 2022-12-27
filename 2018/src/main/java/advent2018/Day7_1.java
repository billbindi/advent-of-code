package advent2018;

import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7_1 {

    private static final String FILENAME = "2018/day7_input.txt";

    public static final String REQUIREMENT_GROUP = "requirement";
    public static final String STEP_GROUP = "step";
    private static final Pattern LINE_PATTERN = Pattern.compile("Step (?<" + REQUIREMENT_GROUP + ">\\w) must " +
            "be finished before step (?<" + STEP_GROUP + ">\\w) can begin.");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        Set<Step> steps = parseSteps(lines);
        return computeOrder(steps);
    }

    private static Set<Step> parseSteps(List<String> lines) {
        Map<String, Step> stepsByName = new HashMap<>();
        for (String line : lines) {
            Matcher matcher = LINE_PATTERN.matcher(line);
            if (matcher.matches()) {
                String stepName = matcher.group(STEP_GROUP);
                String requirementName = matcher.group(REQUIREMENT_GROUP);

                // make sure steps exist
                if (!stepsByName.containsKey(stepName)) {
                    stepsByName.put(stepName, new Step(stepName.charAt(0)));
                }
                if (!stepsByName.containsKey(requirementName)) {
                    stepsByName.put(requirementName, new Step(requirementName.charAt(0)));
                }

                // add requirement
                Step step = stepsByName.get(stepName);
                Step requirement = stepsByName.get(requirementName);
                step.addRequirement(requirement);
            } else {
                throw new IllegalArgumentException("COULD NOT PARSE LINE: " + line);
            }
        }
        return new HashSet<>(stepsByName.values());
    }

    private static String computeOrder(Set<Step> steps) {
        StringBuilder order = new StringBuilder();
        Set<Step> finished = new HashSet<>();
        while (!steps.isEmpty()) {
            Set<Step> ready = findReady(steps, finished);
            Step first = firstStep(ready);
            order.append(first.step);
            steps.remove(first);
            finished.add(first);
        }
        return order.toString();
    }

    private static Set<Step> findReady(Set<Step> steps, Set<Step> finished) {
        Set<Step> ready = new HashSet<>();
        for (Step step : steps) {
            if (step.isReady(finished)) {
                ready.add(step);
            }
        }
        return ready;
    }

    private static Step firstStep(Set<Step> ready) {
        Step first = ready.stream().findFirst().orElseThrow();
        for (Step step : ready) {
            if (step.step < first.step) {
                first = step;
            }
        }
        return first;
    }

    private static class Step {
        final char step;
        final Set<Step> requirements = new HashSet<>();

        private Step(char step) {
            this.step = step;
        }

        void addRequirement(Step requirement) {
            requirements.add(requirement);
        }

        boolean isReady(Set<Step> finished) {
            return Sets.difference(requirements, finished).isEmpty();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Step step1 = (Step) o;
            return step == step1.step && requirements.equals(step1.requirements);
        }

        @Override
        public int hashCode() {
            return Objects.hash(step, requirements);
        }
    }
}
