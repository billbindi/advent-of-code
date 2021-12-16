package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7_2 {
    private static final String FILENAME = "2016/day7_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        int count = 0;
        for (String ip : content) {
            if (supportsSsl(ip)) {
                count++;
            }
        }
        return count;
    }

    private static boolean supportsSsl(String ip) {
        Set<String> supernetMatches = new HashSet<>();
        Set<String> hypernetMatches = new HashSet<>();
        char[] chars = ip.toCharArray();
        boolean inBracket = false;
        for (int i = 0; i < chars.length - 2; i++) {
            if (chars[i] == '[') {
                inBracket = true;
            } else if (chars[i] == ']') {
                inBracket = false;
            } else {
                if (chars[i] != chars[i + 1] && chars[i] == chars[i + 2]) {
                    if (inBracket) {
                        hypernetMatches.add(new String(Arrays.copyOfRange(chars, i, i + 3)));
                    } else {
                        supernetMatches.add(new String(Arrays.copyOfRange(chars, i, i + 3)));
                    }
                }
            }
        }

        // check match
        for (String aba : supernetMatches) {
            String bab = invert(aba);
            if (hypernetMatches.contains(bab)) {
                return true;
            }
        }
        return false;
    }

    private static String invert(String aba) {
        char[] bab = new char[3];
        bab[0] = aba.charAt(1);
        bab[1] = aba.charAt(0);
        bab[2] = aba.charAt(1);
        return new String(bab);
    }
}
