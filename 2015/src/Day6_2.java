import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;

public class Day6_2 {

    private static final String FILENAME = "day6_input.txt";

    public static void main(String[] args) throws IOException {
        int[][] lights = new int[1000][1000];
        Files.readAllLines(Paths.get(FILENAME))
                .forEach(line -> {
                    Instruction instruction = Instruction.fromLine(line);
                    instruction.apply(lights);
                });
        System.out.println(countBrightness(lights));
    }

    private static int countBrightness(int[][] lights) {
        int count = 0;
        for (int[] row : lights) {
            for (int brightness : row) {
                count += brightness;
            }
        }
        return count;
    }

    private static class Instruction {
        int startx, starty, endx, endy;
        Function<Integer, Integer> action;

        Instruction(int startx, int starty, int endx, int endy, Function<Integer, Integer> action) {
            this.startx = startx;
            this.starty = starty;
            this.endx = endx;
            this.endy = endy;
            this.action = action;
        }

        static Instruction fromLine(String line) {
            String start, end;
            Function<Integer, Integer> action;
            String[] words = line.split(" ");
            if (words[0].equals("toggle")) {
                start = words[1];
                end = words[3];
                action = b -> b+2;
            } else if (words[1].equals("off")) {
                start = words[2];
                end = words[4];
                action = b -> Math.max(0, b-1);
            } else {
                start = words[2];
                end = words[4];
                action = b -> b+1;
            }
            String[] starts = start.split(",");
            String[] ends = end.split(",");
            int startx = Integer.parseInt(starts[0]);
            int starty = Integer.parseInt(starts[1]);
            int endx = Integer.parseInt(ends[0]);
            int endy = Integer.parseInt(ends[1]);
            return new Instruction(startx,starty,endx,endy, action);
        }

        void apply(int[][] lights) {
            for (int i = startx; i <= endx; i++) {
                for (int j = starty; j <= endy; j++) {
                    lights[i][j] = action.apply(lights[i][j]);
                }
            }
        }
    }
}
