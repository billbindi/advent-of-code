package util.graph;

import org.immutables.value.Value;

import java.util.Optional;

// only allow building through factory methods to ensure proper ordering
@Value.Immutable(builder = false)
public interface Edge {
    @Value.Parameter
    Node lower();

    @Value.Parameter
    Node upper();

    @Value.Parameter
    Optional<Integer> weight();

    @Value.Derived
    default String name() {
        return lower().name() + ":" + upper().name();
    }

    static Edge of(Node u, Node v) {
        return of(u, v, Optional.empty());
    }

    static Edge of(Node u, Node v, Optional<Integer> weight) {
        if (u.compareTo(v) <= 0) {
            return ImmutableEdge.of(u, v, weight);
        } else {
            return ImmutableEdge.of(v, u, weight);
        }
    }
}
