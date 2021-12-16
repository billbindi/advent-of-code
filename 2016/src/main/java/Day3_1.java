import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day3_1 {
    private static final String FILENAME = "2016/day3_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        int count = 0;
        for (String line : content) {
            List<Integer> lengths = parse(line);
            Collections.sort(lengths);
            if (lengths.get(0) + lengths.get(1) > lengths.get(2)) {
                count++;
            }
        }
        return count;
    }

    private static List<Integer> parse(String line) {
        String[] split = line.trim().split("\\s+");
        return Arrays.stream(split)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
