package util.graph;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface Node extends WithNode, Comparable<Node> {
    String name();
    Optional<Integer> weight();

    default int compareTo(Node node) {
        return name().compareTo(node.name());
    }
}
