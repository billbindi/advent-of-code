package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day20_1 {

    private static final String FILENAME = "2017/day20_input.txt";

    private static final String NUMBER_PATTERN = "-?\\d+";
    private static final Pattern PARTICLE_PATTERN = Pattern.compile(
            "p=<(?<xpos>" +NUMBER_PATTERN + "),\\s*(?<ypos>" + NUMBER_PATTERN + "),\\s*(?<zpos>" + NUMBER_PATTERN + ")>,\\s*"
            + "v=<(?<xvel>" + NUMBER_PATTERN + "),\\s*(?<yvel>" + NUMBER_PATTERN + "),\\s*(?<zvel>" + NUMBER_PATTERN + ")>,\\s*"
            + "a=<(?<xacc>" + NUMBER_PATTERN + "),\\s*(?<yacc>" + NUMBER_PATTERN + "),\\s*(?<zacc>" + NUMBER_PATTERN + ")>");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        int closestPoint = computeClosestPoint(lines);
        System.out.println(closestPoint);
    }

    // just find the point where the sum of the magnitude of accelerations is closest to 0
    // if there are ties, then will need to move onto vel, but no ties in this input!
    private static int computeClosestPoint(List<String> lines) {
        List<Particle> particles = lines.stream()
                .map(Particle::fromLine)
                .collect(Collectors.toList());
        int minIndex = 0;
        double minAcceleration = particles.get(0).manhattenMagnitudeAcc();
        for (int i = 1; i < particles.size(); i++) {
            double acceleration = particles.get(i).manhattenMagnitudeAcc();
            if (acceleration < minAcceleration) {
                minIndex = i;
                minAcceleration = acceleration;
            }
        }
        return minIndex;
    }

    private static class Particle {
        double xPos;
        double yPos;
        double zPos;

        double xVel;
        double yVel;
        double zVel;

        double xAcc;
        double yAcc;
        double zAcc;

        private Particle(double xPos, double yPos, double zPos, double xVel, double yVel, double zVel, double xAcc, double yAcc, double zAcc) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.zPos = zPos;
            this.xVel = xVel;
            this.yVel = yVel;
            this.zVel = zVel;
            this.xAcc = xAcc;
            this.yAcc = yAcc;
            this.zAcc = zAcc;
        }

        static Particle fromLine(String line) {
            Matcher matcher = PARTICLE_PATTERN.matcher(line);
            if (matcher.matches()) {
                double xpos = Double.parseDouble(matcher.group("xpos"));
                double ypos = Double.parseDouble(matcher.group("ypos"));
                double zpos = Double.parseDouble(matcher.group("zpos"));
                double xvel = Double.parseDouble(matcher.group("xvel"));
                double yvel = Double.parseDouble(matcher.group("yvel"));
                double zvel = Double.parseDouble(matcher.group("zvel"));
                double xacc = Double.parseDouble(matcher.group("xacc"));
                double yacc = Double.parseDouble(matcher.group("yacc"));
                double zacc = Double.parseDouble(matcher.group("zacc"));
                return new Particle(xpos, ypos, zpos, xvel, yvel, zvel, xacc, yacc, zacc);
            } else {
                throw new IllegalArgumentException("COULD NOT PARSE LINE: " + line);
            }
        }

        double manhattenMagnitudeAcc() {
            return Math.abs(xAcc) + Math.abs(yAcc) + Math.abs(zAcc);
        }
    }
}
