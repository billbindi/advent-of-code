package util.graph;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface Node extends WithNode {
    String name();
    Optional<Integer> weight();
}
