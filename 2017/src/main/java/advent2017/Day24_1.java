package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day24_1 {

    private static final String FILENAME = "2017/day24_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        Set<Component> components = parseComponents(lines);
        int strength = strongestBridge(components, 0);
        System.out.println(strength);
    }

    private static Set<Component> parseComponents(List<String> lines) {
        return lines.stream()
                .map(line -> line.split("/"))
                .map(parts -> new Component(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])))
                .collect(Collectors.toSet());
    }

    private static int strongestBridge(Set<Component> components, int connector) {
        if (components.isEmpty()) {
            return 0;
        } else {
            Set<Component> potential = findPotentialLinks(components, connector);
            int max = 0;
            for (Component component : potential) {
                components.remove(component);
                int value = component.value();
                if (component.sideA == connector) {
                    value += strongestBridge(components, component.sideB);
                } else {
                    value += strongestBridge(components, component.sideA);
                }
                components.add(component);

                if (value > max) {
                    max = value;
                }
            }
            return max;
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
