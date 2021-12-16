package advent2015;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day14_2 {

    private static final int TIME = 2503;

    public static void main(String[] args) {
        Map<Reindeer, Integer> base = ImmutableMap.<Reindeer, Integer>builder()
                .put(new Reindeer("Dancer", 27, 5, 132), 0)
                .put(new Reindeer("Cupid", 22, 2, 41), 0)
                .put(new Reindeer("Rudolph", 11, 5, 48), 0)
                .put(new Reindeer("Donner", 28, 5, 134), 0)
                .put(new Reindeer("Dasher", 4, 16, 55), 0)
                .put(new Reindeer("Blitzen", 14, 3, 38), 0)
                .put(new Reindeer("Prancer", 3, 21, 40), 0)
                .put(new Reindeer("Comet", 18, 6, 103), 0)
                .put(new Reindeer("Vixen", 18, 5, 84), 0)
                .build();
        Map<Reindeer, Integer> points = new HashMap<>(base);
        for (int i = 0; i < TIME; i ++) {
            for (Reindeer reindeer : points.keySet()) {
                reindeer.update();
            }
            updatePoints(points);
        }
        System.out.println(points);
        System.out.println(points.values().stream().mapToInt(i -> i).max().getAsInt());
    }

    private static void updatePoints(Map<Reindeer, Integer> points) {
        int max = points.keySet().stream()
                .mapToInt(Reindeer::getDistance)
                .max()
                .getAsInt();
        for (Reindeer reindeer : points.keySet()) {
            if (reindeer.getDistance() == max) {
                points.put(reindeer, points.get(reindeer) + 1);
            }
        }
    }

    private static class Reindeer {
        final String name;
        final int speed;
        final int duration;
        final int rest;

        int distance = 0;
        int time;
        boolean flying = true;

        private Reindeer(String name, int speed, int duration, int rest) {
            this.name = name;
            this.speed = speed;
            this.duration = duration;
            this.rest = rest;
            this.time = duration;
        }

        void update() {
            if (flying) {
                distance += speed;
                time--;
                if (time == 0) {
                    time = rest;
                    flying = false;
                }
            } else {
                time--;
                if (time == 0) {
                    time = duration;
                    flying = true;
                }
            }
        }

        int getDistance() {
            return distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Reindeer reindeer = (Reindeer) o;
            return speed == reindeer.speed &&
                    duration == reindeer.duration &&
                    rest == reindeer.rest &&
                    name.equals(reindeer.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, speed, duration, rest);
        }

        @Override
        public String toString() {
            return "Reindeer{" +
                    "name='" + name + '\'' +
                    ", speed=" + speed +
                    ", duration=" + duration +
                    ", rest=" + rest +
                    ", distance=" + distance +
                    '}';
        }
    }
}
