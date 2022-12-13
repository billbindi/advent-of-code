package advent2017;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7_1 {

    private static final String FILENAME = "2017/day7_input.txt";

    public static final String PROGRAM_GROUP = "program";
    public static final String WEIGHT_GROUP = "weight";
    public static final String SUPPORTED_GROUP = "supported";
    private static final Pattern PROGRAM_PATTERN = Pattern.compile("(?<" + PROGRAM_GROUP + ">\\w+)\\s+\\((?<" + WEIGHT_GROUP + ">\\d+)\\)(\\s*->\\s*(?<" + SUPPORTED_GROUP + ">[\\s\\w,]+))?");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        String baseProgram = findBase(lines);
        System.out.println(baseProgram);
    }

    // base program is only one that doesn't appear on the right
    private static String findBase(List<String> lines) {
        Set<String> programs = new HashSet<>(lines.size());
        Set<String> supporteds = new HashSet<>(lines.size());
        for (String line : lines) {
            Matcher matcher = PROGRAM_PATTERN.matcher(line);
            if (matcher.matches()) {
                String program = matcher.group(PROGRAM_GROUP);
                programs.add(program);

                String supported = matcher.group(SUPPORTED_GROUP);
                supporteds.addAll(parseSupported(supported));
            } else {
                throw new IllegalStateException("COULD NOT MATCH LINE: " + line);
            }
        }
        Set<String> diff = Sets.difference(programs, supporteds);
        return Iterables.getOnlyElement(diff);
    }

    private static Collection<String> parseSupported(String supported) {
        if (supported == null || supported.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(supported.split(","))
                .map(String::strip)
                .collect(Collectors.toList());
    }
}
