package advent2017;

import com.google.common.collect.Iterables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Day9_1 {

    private static final String FILENAME = "2017/day9_input.txt";

    public static void main(String[] args) throws IOException {
        String line = Iterables.getOnlyElement(Files.readAllLines(Paths.get(FILENAME)));
        int score = countScore(Objects.requireNonNull(line));
        System.out.println(score);
    }

    // assume all groups are closed properly for simplicity
    private static int countScore(String line) {
        int sum = 0;
        int score = 0;
        for (int index = 0; index < line.length(); index++) {
            switch (line.charAt(index)) {
                case '{':
                    // add the new group only on open, not on close
                    score++;
                    sum += score;
                    break;
                case '}':
                    score--;
                    break;
                case '!':
                    // skip an extra character
                    index++;
                    break;
                case '<':
                    // set to index of closing '>', then next iteration will be char after
                    index = indexOfClose(line, index + 1);
                    break;
                default:
                    // do nothing
            }
        }
        return sum;
    }

    private static int indexOfClose(String line, int startIndex) {
        for (int index = startIndex; index < line.length(); index++) {
            switch (line.charAt(index)) {
                case '>':
                    return index;
                case '!':
                    // skip an extra character
                    index++;
                    break;
            }
        }
        throw new IllegalStateException("COULD NOT FIND A CLOSE TO GARBAGE FROM INDEX: " + startIndex);
    }
}
