package util.graph;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface Node extends WithNode, Comparable<Node> {
    @Value.Parameter
    String name();

    @Value.Parameter
    Optional<Integer> weight();

    static Node of(String name) {
        return ImmutableNode.of(name, Optional.empty());
    }

    static Node of(String name, Optional<Integer> weight) {
        return ImmutableNode.of(name, weight);
    }

    default int compareTo(Node node) {
        return name().compareTo(node.name());
    }
}
