package util.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * No weights, undirected.
 */
public class SimpleGraph implements Graph {
    private final Map<Node, Set<Edge>> incidentEdgeMap = new HashMap<>();

    @Override
    public Set<Node> nodes() {
        return Set.copyOf(incidentEdgeMap.keySet());
    }

    @Override
    public Set<Edge> edges() {
        return incidentEdgeMap.values()
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Node> adjacentNodes(Node node) {
        return incidentEdgeMap.get(node).stream()
                .map(edge -> GraphAlgorithms.getOther(edge, node))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Edge> incidentEdges(Node node) {
        return incidentEdgeMap.get(node);
    }

    @Override
    public void addNode(Node node) {
        incidentEdgeMap.put(node, new HashSet<>());
    }

    @Override
    public void addEdge(Edge edge) {
        incidentEdgeMap.computeIfAbsent(edge.upper(), _unused -> new HashSet<>()).add(edge);
        incidentEdgeMap.computeIfAbsent(edge.lower(), _unused -> new HashSet<>()).add(edge);
    }

    @Override
    public Optional<Node> removeNode(Node node) {
        if (incidentEdgeMap.containsKey(node)) {
            Set<Edge> incidentEdges = incidentEdgeMap.get(node);
            for (Edge edge : incidentEdges) {
                Node other = GraphAlgorithms.getOther(edge, node);
                incidentEdgeMap.get(other).remove(edge);
            }
            incidentEdgeMap.remove(node);
            return Optional.of(node);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Edge> removeEdge(Edge edge) {
        Node upper = edge.upper();
        Node lower = edge.lower();
        if (incidentEdgeMap.containsKey(lower) &&  incidentEdgeMap.containsKey(upper)
                && incidentEdgeMap.get(lower).contains(edge) &&  incidentEdgeMap.get(upper).contains(edge)) {
                incidentEdgeMap.get(lower).remove(edge);
                incidentEdgeMap.get(upper).remove(edge);
                return Optional.of(edge);
        } else {
            return Optional.empty();
        }
    }
}
