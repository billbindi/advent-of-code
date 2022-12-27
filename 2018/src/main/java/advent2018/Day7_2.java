package advent2018;

import com.google.common.collect.Sets;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7_2 {

    private static final String FILENAME = "2018/day7_input.txt";

    public static final String REQUIREMENT_GROUP = "requirement";
    public static final String STEP_GROUP = "step";
    private static final Pattern LINE_PATTERN = Pattern.compile("Step (?<" + REQUIREMENT_GROUP + ">\\w) must " +
            "be finished before step (?<" + STEP_GROUP + ">\\w) can begin.");

    private static final int NUM_WORKERS = 5;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        Set<Step> steps = parseSteps(lines);
        return Integer.toString(computeTime(steps));
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

    private static int computeTime(Set<Step> steps) {
        Set<Step> finished = new HashSet<>();
        int timestamp = 0;
        while (!steps.isEmpty()) {
            List<Step> ready = findReady(steps, finished);
            for (int worker = 0; worker < Math.min(NUM_WORKERS, ready.size()); worker++) {
                Step step = ready.get(worker);
                step.tick();
                if (step.isFinished()) {
                    steps.remove(step);
                    finished.add(step);
                }
            }
            timestamp++;
        }
        return timestamp;
    }

    private static List<Step> findReady(Set<Step> steps, Set<Step> finished) {
        List<Step> ready = new ArrayList<>();
        for (Step step : steps) {
            if (step.isReady(finished)) {
                ready.add(step);
            }
        }
        ready.sort(null);
        return ready;
    }

    private static class Step implements Comparable<Step> {
        final char step;
        final Set<Step> requirements = new HashSet<>();

        int timeRemaining;
        boolean hasStarted = false;

        private Step(char step) {
            this.step = step;
            this.timeRemaining = 60 + (step - 'A' + 1);
        }

        void addRequirement(Step requirement) {
            requirements.add(requirement);
        }

        boolean isReady(Set<Step> finished) {
            return Sets.difference(requirements, finished).isEmpty();
        }

        void tick() {
            hasStarted = true;
            timeRemaining--;
        }

        boolean isFinished() {
            return timeRemaining <= 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Step step1 = (Step) o;
            return step == step1.step;
        }

        @Override
        public int hashCode() {
            return Objects.hash(step);
        }

        @Override
        public int compareTo(@Nonnull Step other) {
            if (this.hasStarted && ! other.hasStarted) {
                return -1;
            } else if (!this.hasStarted && other.hasStarted) {
                return 1;
            } else {
                return step - other.step;
            }
        }
    }
}
