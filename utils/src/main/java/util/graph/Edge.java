package util.graph;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@Value.Style(visibility = Value.Style.ImplementationVisibility.PACKAGE) // only build with provider factory methods
public interface Edge {
    Node lower();
    Node upper();
    Optional<Integer> weight();

    static Edge of(Node u, Node v, Optional<Integer> weight) {
        if (u.compareTo(v) <= 0) {
            return ImmutableEdge.builder().lower(u).upper(v).weight(weight).build();
        } else {
            return ImmutableEdge.builder().lower(v).upper(u).weight(weight).build();
        }
    }

    @Value.Derived
    default String name() {
        return lower().name() + ":" + upper().name();
    }
}
