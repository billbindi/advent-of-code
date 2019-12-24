import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day19 {

    private static final String FILENAME = "day19_input.txt";

    private static final String MOLECULE = "CRnSiRnCaPTiMgYCaPTiRnFArSiThFArCaSiThSiThPBCaCaSiRnSiRnTiTiMgArPBCaPMgYPTiRnFArFArCaSiRnBPMgArPRnCaPTiRnFArCaSiThCaCaFArPBCaCaPTiTiRnFArCaSiRnSiAlYSiThRnFArArCaSiRnBFArCaCaSiRnSiThCaCaCaFYCaPTiBCaSiThCaSiThPMgArSiRnCaPBFYCaCaFArCaCaCaCaSiThCaSiRnPRnFArPBSiThPRnFArSiRnMgArCaFYFArCaSiRnSiAlArTiTiTiTiTiTiTiRnPMgArPTiTiTiBSiRnSiAlArTiTiRnPMgArCaFYBPBPTiRnSiRnMgArSiThCaFArCaSiThFArPRnFArCaSiRnTiBSiThSiRnSiAlYCaFArPRnFArSiThCaFArCaCaSiThCaCaCaSiRnPRnCaFArFYPMgArCaPBCaPBSiRnFYPBCaFArCaSiAl";

    public static void main(String[] args) throws IOException {
        List<Rule> rules = Files.readAllLines(Paths.get(FILENAME)).stream()
                .map(Day19::toRule)
                .collect(Collectors.toList());
        Set<String> next = new HashSet<>();
        for (Rule rule : rules) {
            next.addAll(rule.apply());
        }
        System.out.println(next.size());
    }

    private static Rule toRule(String line) {
        String[] parts = line.split(" ");
        return new Rule(parts[0], parts[2]);
    }

    private static class Rule {
        final String input;
        final String output;

        private Rule(String input, String output) {
            this.input = input;
            this.output = output;
        }

        Set<String> apply() {
            Set<String> molecules = new HashSet<>();
            for (int i = 0; i < MOLECULE.length() - input.length() + 1; i++) {
                if (MOLECULE.substring(i, i + input.length()).equals(input)) {
                    molecules.add(replace(i));
                }
            }
            return molecules;
        }

        private String replace(int startIndex) {
            return MOLECULE.substring(0, startIndex) + output + MOLECULE.substring(startIndex + input.length());
        }
    }
}
