package advent2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

public class Day6 {

    private static final String FILENAME = "day6_input.txt";

    public static void main(String[] args) throws IOException {
        boolean[][] lights = new boolean[1000][1000];
        Files.readAllLines(Paths.get(FILENAME))
                .forEach(line -> {
                    Instruction instruction = Instruction.fromLine(line);
                    instruction.apply(lights);
                });
        System.out.println(countOn(lights));
    }

    private static int countOn(boolean[][] lights) {
        int count = 0;
        for (boolean[] row : lights) {
            for (boolean b : row) {
                if (b) {
                    count++;
                }
            }
        }
        return count;
    }

    private static class Instruction {
        int startx, starty, endx, endy;
        Function<Boolean, Boolean> action;

        Instruction(int startx, int starty, int endx, int endy, Function<Boolean, Boolean> action) {
            this.startx = startx;
            this.starty = starty;
            this.endx = endx;
            this.endy = endy;
            this.action = action;
        }

        static Instruction fromLine(String line) {
            String start, end;
            Function<Boolean, Boolean> action;
            String[] words = line.split(" ");
            if (words[0].equals("toggle")) {
                start = words[1];
                end = words[3];
                action = b -> !b;
            } else if (words[1].equals("off")) {
                start = words[2];
                end = words[4];
                action = b -> false;
            } else {
                start = words[2];
                end = words[4];
                action = b -> true;
            }
            String[] starts = start.split(",");
            String[] ends = end.split(",");
            int startx = Integer.parseInt(starts[0]);
            int starty = Integer.parseInt(starts[1]);
            int endx = Integer.parseInt(ends[0]);
            int endy = Integer.parseInt(ends[1]);
            return new Instruction(startx,starty,endx,endy, action);
        }

        void apply(boolean[][] lights) {
            for (int i = startx; i <= endx; i++) {
                for (int j = starty; j <= endy; j++) {
                    lights[i][j] = action.apply(lights[i][j]);
                }
            }
        }
    }
}
