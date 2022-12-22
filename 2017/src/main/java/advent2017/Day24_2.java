package advent2017;

import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day24_2 {

    private static final String FILENAME = "2017/day24_input.txt";

    private static final Set<Set<Component>> ALL_SOLUTIONS = new HashSet<>();

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        Set<Component> components = parseComponents(lines);
        Set<Component> solution = new HashSet<>();
        buildBridges(components, 0, solution);
        int strength = strongestLongestBridge();
        System.out.println(strength);
    }

    private static int strongestLongestBridge() {
        int maxLength = 0;
        int maxStrength = 0;
        for (Set<Component> bridge : ALL_SOLUTIONS) {
            if (bridge.size() >= maxLength) {
                maxLength = bridge.size();
                int strength = strengthOfBridge(bridge);
                if (strength > maxStrength) {
                    maxStrength = strength;
                }
            }
        }
        return maxStrength;
    }

    private static int strengthOfBridge(Set<Component> bridge) {
        return bridge.stream()
                .mapToInt(Component::value)
                .sum();
    }

    private static Set<Component> parseComponents(List<String> lines) {
        return lines.stream()
                .map(line -> line.split("/"))
                .map(parts -> new Component(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])))
                .collect(Collectors.toSet());
    }

    private static void buildBridges(Set<Component> components, int connector, Set<Component> solution) {
        if (!components.isEmpty()) {
            Set<Component> potential = findPotentialLinks(components, connector);
            for (Component component : potential) {
                components.remove(component);
                solution.add(component);
                ALL_SOLUTIONS.add(ImmutableSet.copyOf(solution));
                if (component.sideA == connector) {
                    buildBridges(components, component.sideB, solution);
                } else {
                    buildBridges(components, component.sideA, solution);
                }
                components.add(component);
                solution.remove(component);
            }
        }
    }

    private static Set<Component> findPotentialLinks(Set<Component> components, int connector) {
        Set<Component> potential = new HashSet<>();
        for (Component component : components) {
            if (component.sideA == connector || component.sideB == connector) {
                potential.add(component);
            }
        }
        return potential;
    }

    private static class Component {
        final int sideA;
        final int sideB;

        Component(int sideA, int sideB) {
            this.sideA = sideA;
            this.sideB = sideB;
        }

        int value() {
            return sideA + sideB;
        }
    }
}
