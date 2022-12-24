package advent2018;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2_2 {

    private static final String FILENAME = "2018/day2_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    // Brute force is more than efficient enough for small input.
    // Slight optimization in only checking further in the list,
    // no need to chack (a, b) and (b, a) pairs.
    private static String solve(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                String first = lines.get(i);
                String second = lines.get(j);
                String check = checkMatch(first, second);
                if (check != null) {
                    return check;
                }
            }
        }
        throw new IllegalStateException("NO MATCHES FOUND");
    }

    @Nullable
    private static String checkMatch(String first, String second) {
        // just making sure
        if (first.length() != second.length()) {
            return null;
        }
        boolean mismatched = false;
        int mismatchIndex = 0;
        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) != second.charAt(i)) {
                if (mismatched) {
                    // second mismatch, no good
                    return null;
                } else {
                    mismatched = true;
                    mismatchIndex = i;
                }
            }
        }

        // only one mismatch, return rest of string
        return first.substring(0, mismatchIndex) + first.substring(mismatchIndex + 1);
    }
}
