package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day20_2 {

    private static final String FILENAME = "2017/day20_input.txt";

    private static final int CHECKPOINT_FREQUENCY = 200;
    private static final int NUM_CHECKPOINTS = 25;

    private static final String NUMBER_PATTERN = "-?\\d+";
    private static final Pattern PARTICLE_PATTERN = Pattern.compile(
            "p=<(?<xpos>" +NUMBER_PATTERN + "),\\s*(?<ypos>" + NUMBER_PATTERN + "),\\s*(?<zpos>" + NUMBER_PATTERN + ")>,\\s*"
            + "v=<(?<xvel>" + NUMBER_PATTERN + "),\\s*(?<yvel>" + NUMBER_PATTERN + "),\\s*(?<zvel>" + NUMBER_PATTERN + ")>,\\s*"
            + "a=<(?<xacc>" + NUMBER_PATTERN + "),\\s*(?<yacc>" + NUMBER_PATTERN + "),\\s*(?<zacc>" + NUMBER_PATTERN + ")>");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(computeSurvivors(lines));
    }

    // run the simulation a number of times and just try some
    // outputs for high number of simulations
    private static Map<Integer, Integer> computeSurvivors(List<String> lines) {
        List<Particle> particles = lines.stream()
                .map(Particle::fromLine)
                .collect(Collectors.toList());
        Map<Integer, Integer> survivorsMap = new HashMap<>();
        for (int i = 1; i <= NUM_CHECKPOINTS * CHECKPOINT_FREQUENCY; i++) {
            particles = simulateWithCollisions(particles);
            if (i % CHECKPOINT_FREQUENCY == 0) {
                survivorsMap.put(i, particles.size());
            }
        }
        return survivorsMap;
    }

    private static List<Particle> simulateWithCollisions(List<Particle> particles) {
        List<Particle> survivors = new ArrayList<>(particles.size());
        particles.forEach(Particle::update);
        for (int i = 0; i < particles.size(); i++) {
            Particle test = particles.get(i);
            boolean isColliding = false;
            for (Particle other : particles) {
                if (!test.equals(other) && test.isColliding(other)) {
                    isColliding = true;
                    break;
                }
            }
            if (!isColliding) {
                survivors.add(test);
            }
        }
        return survivors;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Particle particle = (Particle) o;
            return Double.compare(particle.xPos, xPos) == 0 && Double.compare(particle.yPos, yPos) == 0 && Double.compare(particle.zPos, zPos) == 0 && Double.compare(particle.xVel, xVel) == 0 && Double.compare(particle.yVel, yVel) == 0 && Double.compare(particle.zVel, zVel) == 0 && Double.compare(particle.xAcc, xAcc) == 0 && Double.compare(particle.yAcc, yAcc) == 0 && Double.compare(particle.zAcc, zAcc) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(xPos, yPos, zPos, xVel, yVel, zVel, xAcc, yAcc, zAcc);
        }

        void update() {
            xVel += xAcc;
            yVel += yAcc;
            zVel += zAcc;

            xPos += xVel;
            yPos += yVel;
            zPos += zVel;
        }

        boolean isColliding(Particle other) {
            return xPos == other.xPos && yPos == other.yPos && zPos == other.zPos;
        }
    }
}
