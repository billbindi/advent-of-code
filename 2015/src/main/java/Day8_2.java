import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day8_2 {

    private static final String FILENAME = "day8_input.txt";

    public static void main(String[] args) throws IOException {
        int total = Files.readAllLines(Paths.get(FILENAME)).stream()
                .mapToInt(Day8_2::characterDifference)
                .sum();
        System.out.println(total);
    }

    private static int characterDifference(String line) {
        int count = 2;
        for (char c : line.toCharArray()) {
            if (c == '\\' || c == '\"') {
                count++;
            }
            count++;
        }
        return count - line.length();
    }

}
