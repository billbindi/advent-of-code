package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day3_2 {
    private static final String FILENAME = "2016/day3_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        int count = 0;
        for (int i = 0; i < content.size(); i += 3) {
            // a1 a2 a3
            // b1 b2 b3
            // c1 c2 c3
            List<Integer> a = parse(content.get(i));
            List<Integer> b = parse(content.get(i + 1));
            List<Integer> c = parse(content.get(i + 2));

            // a1 b1 c1
            // a2 b2 c2
            // a3 b3 c3
            swap(a, 1, b, 0);
            swap(a, 2, c, 0);
            swap(b, 2, c, 1);

            count += checkPossible(a);
            count += checkPossible(b);
            count += checkPossible(c);
        }
        return count;
    }

    private static int checkPossible(List<Integer> lengths) {
        Collections.sort(lengths);
        if (lengths.get(0) + lengths.get(1) > lengths.get(2)) {
            return 1;
        } else {
            return 0;
        }
    }

    private static List<Integer> parse(String line) {
        String[] split = line.trim().split("\\s+");
        return Arrays.stream(split)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static void swap(List<Integer> a, int i, List<Integer> b, int j) {
        int temp = a.get(i);
        a.set(i, b.get(j));
        b.set(j, temp);
    }
}
