package advent2018;

import util.Coordinate;
import util.PixelCoordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

    private static final String FILENAME = "2018/day10_input.txt";

    private static final String XCOORD_GROUP = "xcoord";
    private static final String YCOORD_GROUP = "ycoord";
    private static final String XVEL_GROUP = "xvel";
    private static final String YVEL_GROUP = "yvel";
    private static final Pattern POINT_PATTERN = Pattern.compile(
            "position=<\\s*(?<" + XCOORD_GROUP + ">-?\\d+),\\s*(?<" + YCOORD_GROUP + ">-?\\d+)>" +
            "\\s*velocity=<\\s*(?<" + XVEL_GROUP + ">-?\\d+),\\s*(?<" + YVEL_GROUP + ">-?\\d+)>");

    private static final int CHEAT_ITERATIONS = 10500;
    private static final int PRINT_ITERATIONS = 500;
    private static final int MAX_ALLOWED_SIZE = 100;

    private static final Path OUTPUT_FILE = Paths.get("output.txt");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        solve(lines);
    }

    private static void solve(List<String> lines) throws IOException {
        List<CoordWithVelocity> points = parsePoints(lines);
        for (CoordWithVelocity point : points) {
            point.updateMultiple(CHEAT_ITERATIONS);
        }

        List<String> iterations = new ArrayList<>(PRINT_ITERATIONS);
        for (int i = 0; i < PRINT_ITERATIONS; i++) {
            updateAllPoints(points);
            formatIteration(iterations, points, CHEAT_ITERATIONS + i + 1);
        }

        printIterations(iterations);
    }

    private static List<CoordWithVelocity> parsePoints(List<String> lines) {
        List<CoordWithVelocity> points = new ArrayList<>(lines.size());
        for (String line : lines) {
            Matcher m = POINT_PATTERN.matcher(line);
            if (m.find()) {
                int xcoord = Integer.parseInt(m.group(XCOORD_GROUP));
                int ycoord = Integer.parseInt(m.group(YCOORD_GROUP));
                PixelCoordinate coord = new PixelCoordinate(xcoord, ycoord);

                int xvel = Integer.parseInt(m.group(XVEL_GROUP));
                int yvel = Integer.parseInt(m.group(YVEL_GROUP));
                Coordinate velocity = new Coordinate(xvel, yvel);

                points.add(new CoordWithVelocity(coord, velocity));
            } else {
                throw new IllegalArgumentException("COULD NOT PARSE LINE: " + line);
            }
        }
        return points;
    }

    private static void updateAllPoints(List<CoordWithVelocity> points) {
        for (CoordWithVelocity point : points) {
            point.update();
        }
    }

    private static void formatIteration(List<String> iterations, List<CoordWithVelocity> points, int iterationNum) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (CoordWithVelocity point : points) {
            PixelCoordinate coord = point.getCoord();
            int x = coord.getX();
            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }

            int y = coord.getY();
            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }
        }
        int addX = -minX;
        int addY = -minY;

        int sizeX = addX + maxX + 1;
        int sizeY = addY + maxY + 1;
        if (sizeX < MAX_ALLOWED_SIZE && sizeY < MAX_ALLOWED_SIZE) {
            boolean[][] output = new boolean[sizeX][sizeY];
            for (CoordWithVelocity point : points) {
                PixelCoordinate coord = point.getCoord();
                output[coord.getX() + addX][coord.getY() + addY] = true;
            }

            String iteration = buildFullIteration(output, iterationNum);
            iterations.add(iteration);
        }
    }

    private static String buildFullIteration(boolean[][] positions, int iterationNum) {
        StringBuilder output = new StringBuilder();
        output.append(iterationNum)
                .append("\n");
        for (int y = 0; y < positions[0].length; y++) {
            for (int x = 0; x < positions.length; x++) {
                if (positions[x][y]) {
                    output.append("#");
                } else {
                    output.append(".");
                }
            }
            output.append("\n");
        }
        output.append("\n\n");
        return output.toString();
    }

    private static void printIterations(List<String> lines) throws IOException {
        Files.deleteIfExists(OUTPUT_FILE);
        Files.createFile(OUTPUT_FILE);
        Files.write(OUTPUT_FILE, lines);
    }

    private static class CoordWithVelocity {
        final PixelCoordinate coord;
        final Coordinate velocity;

        private CoordWithVelocity(PixelCoordinate coord, Coordinate velocity) {
            this.coord = coord;
            this.velocity = velocity;
        }

        void update() {
            updateMultiple(1);
        }

        void updateMultiple(int multiplier) {
            coord.addX(multiplier * getVelocity().getX());
            coord.addY(multiplier * getVelocity().getY());
        }

        public PixelCoordinate getCoord() {
            return coord;
        }

        public Coordinate getVelocity() {
            return velocity;
        }
    }
}
