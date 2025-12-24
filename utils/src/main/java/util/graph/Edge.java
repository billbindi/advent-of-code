package util.graph;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface Edge extends WithEdge {
    Node source();
    Node target();
    Optional<Integer> weight();

    @Value.Derived
    default String name() {
        return source().name() + ":" + target().name();
    }
}
