import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day8 {

    private static final String FILENAME = "day8_input.txt";

    private static final String HEX = "[a-f0-9]{2}";

    public static void main(String[] args) throws IOException {
        int total = Files.readAllLines(Paths.get(FILENAME)).stream()
                .mapToInt(Day8::characterDifference)
                .sum();
        System.out.println(total);
    }

    private static int characterDifference(String raw) {
        String line = raw.toLowerCase();
        int count = 2;
        for (int i = 1; i < line.length() - 1; i++) {
            if (line.charAt(i) == '\\') {
                if (line.charAt(i+1) == '\\' || line.charAt(i+1) == '\"') {
                    i++;
                    count++;
                } else if (line.charAt(i+1) == 'x'
                        && i < line.length() - 4
                        && line.substring(i+2, i+4).matches(HEX)) {
                    i+=3;
                    count+=3;
                }
            }
        }
        return count;
    }

}
