package advent2018;

import util.PixelCoordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day13_1 {

    private static final String FILENAME = "2018/day12_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static PixelCoordinate solve(List<String> lines) {
        return null;
    }
}
