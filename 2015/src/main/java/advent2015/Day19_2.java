package advent2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

// 150
// 225
public class Day19_2 {

    private static final String FILENAME = "day19_input.txt";

    private static final String TARGET = "CRnSiRnCaPTiMgYCaPTiRnFArSiThFArCaSiThSiThPBCaCaSiRnSiRnTiTiMgArPBCaPMgYPTiRnFArFArCaSiRnBPMgArPRnCaPTiRnFArCaSiThCaCaFArPBCaCaPTiTiRnFArCaSiRnSiAlYSiThRnFArArCaSiRnBFArCaCaSiRnSiThCaCaCaFYCaPTiBCaSiThCaSiThPMgArSiRnCaPBFYCaCaFArCaCaCaCaSiThCaSiRnPRnFArPBSiThPRnFArSiRnMgArCaFYFArCaSiRnSiAlArTiTiTiTiTiTiTiRnPMgArPTiTiTiBSiRnSiAlArTiTiRnPMgArCaFYBPBPTiRnSiRnMgArSiThCaFArCaSiThFArPRnFArCaSiRnTiBSiThSiRnSiAlYCaFArPRnFArSiThCaFArCaCaSiThCaCaCaSiRnPRnCaFArFYPMgArCaPBCaPBSiRnFYPBCaFArCaSiAl";

    private static final Set<String> SEEN = new HashSet<>();

    public static void main(String[] args) throws IOException {
        List<Rule> rules = Files.readAllLines(Paths.get(FILENAME)).stream()
                .map(Day19_2::toRule)
                .collect(Collectors.toList());
        Queue<Element> molecules = new PriorityQueue<>((a, b) -> b.getScore() - a.getScore());
        molecules.add(new Element("e", 0));
        int steps = 0;
        while (!molecules.isEmpty()) {
            Element element = molecules.poll();
            String molecule = element.getMolecule();
            steps = element.getSteps();
            if (molecule.equals(TARGET)) {
                break;
            } else if (molecule.length() < TARGET.length()) {
                Set<String> nextSteps = computeNextSteps(rules, molecule);
                prune(nextSteps);
                Set<Element> nextElements = nextSteps.stream()
                        .map(s -> new Element(s, element.getSteps() + 1))
                        .collect(Collectors.toSet());
                molecules.addAll(nextElements);
            }
        }
        System.out.println(steps);
    }

    private static void prune(Set<String> nextSteps) {
        nextSteps.removeAll(SEEN);
        SEEN.addAll(nextSteps);
    }

    private static Set<String> computeNextSteps(List<Rule> rules, String molecule) {
        Set<String> next = new HashSet<>();
        for (Rule rule : rules) {
            next.addAll(rule.apply(molecule));
        }
        return next;
    }

    private static Rule toRule(String line) {
        String[] parts = line.split(" ");
        return new Rule(parts[0], parts[2]);
    }

    private static class Element {
        final String molecule;
        final int steps;

        private Element(String molecule, int steps) {
            this.molecule = molecule;
            this.steps = steps;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Element element = (Element) o;
            return steps == element.steps &&
                    molecule.equals(element.molecule);
        }

        @Override
        public int hashCode() {
            return Objects.hash(molecule, steps);
        }

        public String getMolecule() {
            return molecule;
        }

        public int getSteps() {
            return steps;
        }

        public int getScore() {
            return TARGET.length() - molecule.length();
        }
    }

    private static class Rule {
        final String input;
        final String output;

        private Rule(String input, String output) {
            this.input = input;
            this.output = output;
        }

        Set<String> apply(String molecule) {
            Set<String> molecules = new HashSet<>();
            for (int i = 0; i < molecule.length() - input.length() + 1; i++) {
                if (molecule.substring(i, i + input.length()).equals(input)) {
                    molecules.add(replace(molecule, i));
                }
            }
            return molecules;
        }

        private String replace(String molecule, int startIndex) {
            return molecule.substring(0, startIndex) + output + molecule.substring(startIndex + input.length());
        }
    }
}
