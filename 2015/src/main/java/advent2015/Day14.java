package advent2015;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class Day14 {

    private static final int TIME = 2503;

    public static void main(String[] args) {
        List<Reindeer> reindeers = ImmutableList.of(
                new Reindeer("Dancer", 27, 5, 132),
                new Reindeer("Cupid", 22, 2, 41),
                new Reindeer("Rudolph", 11, 5, 48),
                new Reindeer("Donner", 28, 5, 134),
                new Reindeer("Dasher", 4, 16, 55),
                new Reindeer("Blitzen", 14, 3, 38),
                new Reindeer("Prancer", 3, 21, 40),
                new Reindeer("Comet", 18, 6, 103),
                new Reindeer("Vixen", 18, 5, 84)
        );
        for (Reindeer reindeer : reindeers) {
            reindeer.printDistance(TIME);
        }
    }

    private static class Reindeer {
        final String name;
        final int speed;
        final int duration;
        final int rest;

        private Reindeer(String name, int speed, int duration, int rest) {
            this.name = name;
            this.speed = speed;
            this.duration = duration;
            this.rest = rest;
        }

        int distance(int time) {
            int remaining = time;
            boolean flying = true;
            int distance = 0;
            while (remaining > 0) {
                if (flying) {
                    int flytime = Math.min(duration, remaining);
                    distance += (speed * flytime);
                    remaining -= flytime;
                    flying = false;
                } else {
                    remaining -= rest;
                    flying = true;
                }
            }
            return distance;
        }

        void printDistance(int time) {
            System.out.println(name + ": " + distance(time));
        }
    }
}
