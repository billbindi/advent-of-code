package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9_2 {
    private static final String MARKER_REGEX = "\\((?<chars>\\d+)x(?<times>\\d+)\\)";
    private static final Pattern MARKER_PATTERN = Pattern.compile(MARKER_REGEX);

    private static final String FILENAME = "2016/day9_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static long solve(List<String> content) {
        long count = 0;
        for (String line : content) {
            count += decompressLineLength(line);
        }
        return count;
    }

    private static long decompressLineLength(String line) {
        long length = 0;
        int index = 0;
        Matcher m = MARKER_PATTERN.matcher(line);
        while (m.find(index)) {
            int start = m.start();
            length += start - index;

            int endIndex = m.end();
            int chars = Integer.parseInt(m.group("chars"));
            int times = Integer.parseInt(m.group("times"));
            String code = line.substring(endIndex, endIndex + chars);
            length += (times * decompressLineLength(code));

            index = endIndex + chars;
        }
        if (index < line.length()) {
            length += line.length() - index;
        }
        return length;
    }
}
