package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9_1 {
    private static final String MARKER_REGEX = "\\((?<chars>\\d+)x(?<times>\\d+)\\)";
    private static final Pattern MARKER_PATTERN = Pattern.compile(MARKER_REGEX);

    private static final String FILENAME = "2016/day9_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        int count = 0;
        for (String line : content) {
            count += decompressLine(line).length();
        }
        return count;
    }

    private static String decompressLine(String line) {
        int index = 0;
        StringBuilder decompressedLine = new StringBuilder();
        Matcher m = MARKER_PATTERN.matcher(line);
        while (m.find(index)) {
            int start = m.start();
            decompressedLine.append(line, index, start);

            int endIndex = m.end();
            int chars = Integer.parseInt(m.group("chars"));
            int times = Integer.parseInt(m.group("times"));
            String code = line.substring(endIndex, endIndex + chars);
            decompressedLine.append(code.repeat(times));

            index = endIndex + chars;
        }
        if (index < line.length()) {
            decompressedLine.append(line.substring(index));
        }
        return decompressedLine.toString();
    }
}
