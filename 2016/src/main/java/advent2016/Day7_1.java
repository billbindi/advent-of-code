package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day7_1 {
    private static final String FILENAME = "2016/day7_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        int count = 0;
        for (String ip : content) {
            if (supportsTls(ip)) {
                count++;
            }
        }
        return count;
    }

    private static boolean supportsTls(String ip) {
        boolean supports = false;
        char[] chars = ip.toCharArray();
        boolean inBracket = false;
        for (int i = 0; i < chars.length - 3; i++) {
            if (chars[i] == '[') {
                inBracket = true;
            } else if (chars[i] == ']') {
                inBracket = false;
            } else {
                if (chars[i] != chars[i + 1] && chars[i] == chars[i + 3] && chars[i + 1] == chars[i + 2]) {
                    if (inBracket) {
                        return false;
                    } else {
                        supports = true;
                    }
                }
            }
        }
        return supports;
    }
}
