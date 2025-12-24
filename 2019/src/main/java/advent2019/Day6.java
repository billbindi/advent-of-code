package advent2019;

import com.google.common.base.Splitter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

public class Day6 {
    private static final String INPUT_FILENAME = "2019/day6_input.txt";
    private static final Path INPUT_PATH = Path.of(INPUT_FILENAME);

    private static final Planet COM_PLANET = new Planet("COM");

    public static void main(String[] args) throws IOException {
        System.out.println(part1(Files.lines(INPUT_PATH)));
        System.out.println(part2(Files.lines(INPUT_PATH)));
    }

    private static int part1(Stream<String> lines) {
        Multimap<Planet, Planet> map = parseOrbitalMap(lines);
        return countOrbits(map);
    }

    private static int countOrbits(Multimap<Planet, Planet> map) {
        return countOrbitsRecursive(map, COM_PLANET, 1);
    }

    private static int countOrbitsRecursive(Multimap<Planet, Planet> map, Planet planet, int level) {
        Collection<Planet> orbits = map.get(planet);
        if (orbits.isEmpty()) {
            return 0;
        } else {
            int currentLevelOrbits = level * orbits.size();
            int recursiveOrbits = orbits.stream().mapToInt(orbiter -> countOrbitsRecursive(map, orbiter, level +1)).sum();
            return currentLevelOrbits + recursiveOrbits;
        }
    }

    private static Multimap<Planet, Planet> parseOrbitalMap(Stream<String> lines) {
        Multimap<Planet, Planet> map = HashMultimap.create();
        lines.forEach(line -> {
            Iterator<String> splitIterator = Splitter.on(')').split(line).iterator();
            String base = splitIterator.next();
            String orbiter = splitIterator.next();
            map.put(new Planet(base), new Planet(orbiter));
        });
        return map;
    }

    private static int part2(Stream<String> lines) {
        return 0;
    }

    private record Planet(String name) {}
}
