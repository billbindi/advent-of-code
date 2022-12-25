package advent2018;

import com.google.common.collect.Iterables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3_2 {

    private static final String FILENAME = "2018/day3_input.txt";

    private static final String ID_GROUP = "id";
    private static final String STARTX_GROUP = "startx";
    private static final String STARTY_GROUP = "starty";
    private static final String WIDTH_GROUP = "width";
    private static final String HEIGHT_GROUP = "height";
    private static final Pattern LINE_PATTERN = Pattern.compile("#(?<" + ID_GROUP + ">\\d+) @ " +
            "(?<" + STARTX_GROUP + ">\\d+),(?<" + STARTY_GROUP + ">\\d+): " +
            "(?<" + WIDTH_GROUP + ">\\d+)x(?<" + HEIGHT_GROUP + ">\\d+)");

    private static final int SIZE = 1000;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        Cell[][] cloth = claimCloth(lines);
        return Integer.toString(findUnique(cloth));
    }

    private static Cell[][] claimCloth(List<String> lines) {
        Cell[][] cloth = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cloth[i][j] = new Cell();
            }
        }

        for (String line : lines) {
            Matcher matcher = LINE_PATTERN.matcher(line);
            if (matcher.matches()) {
                int id = Integer.parseInt(matcher.group(ID_GROUP));
                int startx = Integer.parseInt(matcher.group(STARTX_GROUP));
                int starty = Integer.parseInt(matcher.group(STARTY_GROUP));
                int width = Integer.parseInt(matcher.group(WIDTH_GROUP));
                int height = Integer.parseInt(matcher.group(HEIGHT_GROUP));
                claimCloth(cloth, id, startx, starty, width, height);
            } else {
                throw new IllegalArgumentException("COULD NOT MATCH LINE: " + line);
            }
        }
        return cloth;
    }

    private static void claimCloth(Cell[][] cloth, int id, int startx, int starty, int width, int height) {
        for (int i = startx; i < startx + width; i++) {
            for (int j = starty; j < starty + height; j++) {
                cloth[i][j].addId(id);
            }
        }
    }

    private static int findUnique(Cell[][] cloth) {
        Set<Integer> ids = allIds();
        for (Cell[] row : cloth) {
            for (Cell cell : row) {
                if (cell.size() > 1) {
                    for (int id : cell.ids) {
                        ids.remove(id);
                    }
                }
            }
        }
        //noinspection ConstantConditions
        return Iterables.getOnlyElement(ids);
    }

    private static Set<Integer> allIds() {
        Set<Integer> ids = new HashSet<>();
        for (int i = 1; i <= 1411; i++) {
            ids.add(i);
        }
        return ids;
    }

    private static class Cell {
        final List<Integer> ids = new ArrayList<>();

        void addId(int id) {
            ids.add(id);
        }

        int size() {
            return ids.size();
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "ids=" + ids +
                    '}';
        }
    }
}
