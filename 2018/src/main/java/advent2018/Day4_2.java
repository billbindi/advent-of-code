package advent2018;

import com.google.common.collect.Iterables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4_2 {

    private static final String FILENAME = "2018/day4_input.txt";

    public static final String DATE_GROUP = "timestamp";
    public static final String ACTION_GROUP = "action";
    private static final Pattern LINE_PATTERN = Pattern.compile("\\[(?<" + DATE_GROUP + ">.*)] (?<" + ACTION_GROUP + ">.*)");

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static String solve(List<String> lines) {
        SortedSet<Event> events = parseEvents(lines);
        return Integer.toString(bestTime(events));
    }

    private static SortedSet<Event> parseEvents(List<String> lines) {
        SortedSet<Event> events = new TreeSet<>();
        for (String line : lines) {
            Matcher matcher = LINE_PATTERN.matcher(line);
            if (matcher.matches()) {
                LocalDateTime timestamp = LocalDateTime.parse(matcher.group(DATE_GROUP), DATE_FORMAT);
                String action = matcher.group(ACTION_GROUP);
                events.add(new Event(timestamp, action));
            } else {
                throw new IllegalArgumentException("COULD NOT PARSE LINE: " + line);
            }
        }
        return events;
    }

    private static int bestTime(SortedSet<Event> events) {
        Map<Integer, Guard> guardsById = new HashMap<>();
        int sleepStart = 0;
        Guard curr = null;
        for (Event event : events) {
            String[] parts = event.action.split("\\s+");
            switch (parts[0]) {
                case "Guard":
                    int id = Integer.parseInt(parts[1].substring(1));
                    if (!guardsById.containsKey(id)) {
                        guardsById.put(id, new Guard(id));
                    }
                    curr = guardsById.get(id);
                    break;
                case "falls":
                    sleepStart = event.timestamp.getMinute();
                    break;
                case "wakes":
                    Objects.requireNonNull(curr).sleep(sleepStart, event.timestamp.getMinute());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + parts[0]);
            }
        }
        return bestTimeForGuard(guardsById.values());
    }

    private static int bestTimeForGuard(Collection<Guard> guards) {
        Guard sleepGuard = Iterables.getFirst(guards, null);
        for (Guard guard : guards) {
            if (guard.maxSleepCount() > Objects.requireNonNull(sleepGuard).maxSleepCount()) {
                sleepGuard = guard;
            }
        }
        return Objects.requireNonNull(sleepGuard).id * sleepGuard.maxSleepTime();
    }

    private static class Event implements Comparable<Event> {
        final LocalDateTime timestamp;
        final String action;

        private Event(LocalDateTime timestamp, String action) {
            this.timestamp = timestamp;
            this.action = action;
        }

        @Override
        public int compareTo(Event other) {
            return timestamp.compareTo(other.timestamp);
        }
    }

    private static class Guard {
        final int[] sleep = new int[60];
        final int id;

        private Guard(int id) {
            this.id = id;
        }

        void sleep(int start, int end) {
            for (int time = start; time < end; time++) {
                sleep[time]++;
            }
        }

        int maxSleepCount() {
            return Arrays.stream(sleep).max().orElseThrow();
        }

        int maxSleepTime() {
            int maxMinute = sleep[0];
            for (int i = 1; i < sleep.length; i++) {
                if (sleep[i] > sleep[maxMinute]) {
                    maxMinute = i;
                }
            }
            return maxMinute;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Guard guard = (Guard) o;
            return id == guard.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
