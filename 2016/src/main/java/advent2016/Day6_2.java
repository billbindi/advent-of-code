package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day6_2 {
    private static final String FILENAME = "2016/day6_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static String solve(List<String> content) {
        List<List<Character>> characters = new ArrayList<>();
        for (int i = 0; i < content.get(0).length(); i++) {
            characters.add(new ArrayList<>());
        }

        for (String line : content) {
            char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                characters.get(i).add(chars[i]);
            }
        }

        List<Character> message = characters.stream()
                .map(list -> list.stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .min(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .get()
                )
                .collect(Collectors.toList());
        return message.toString();
    }
}
