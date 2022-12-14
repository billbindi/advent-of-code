package advent2017;

import util.DisjointSets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day12_2 {

    private static final String FILENAME = "2017/day12_input.txt";

    public static final String ID_GROUP = "id";
    public static final String OTHER_IDS_GROUP = "others";
    private static final Pattern PIPE_PATTERN = Pattern.compile("(?<" + ID_GROUP + ">\\d+)\\s*<->\\s*(?<" + OTHER_IDS_GROUP + ">\\d+(, \\d+)*)");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        int size = countGroupSize(lines, 0);
        System.out.println(size);
    }

    private static int countGroupSize(List<String> lines, int index) {
        DisjointSets sets = DisjointSets.withSize(lines.size());
        lines.forEach(line -> {
            Matcher matcher = PIPE_PATTERN.matcher(line);
            if (matcher.matches()) {
                int id = Integer.parseInt(matcher.group(ID_GROUP));
                List<Integer> otherIds = parseOtherIds(matcher.group(OTHER_IDS_GROUP));
                for (int otherId : otherIds) {
                    sets.combine(id, otherId);
                }
            } else {
                throw new IllegalStateException("CANNOT PARSE LINE: " + line);
            }
        });
        return sets.numSets();
    }

    private static List<Integer> parseOtherIds(String otherIdsString) {
        return Arrays.stream(otherIdsString.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
