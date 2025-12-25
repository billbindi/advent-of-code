package util.graph;

import java.util.Optional;
import java.util.Set;

public interface Graph {
    Set<Node> nodes();
    Set<Edge> edges();
    Set<Node> adjacentNodes(Node node);
    Set<Edge> incidentEdges(Node node);
    void addNode(Node node);
    void addEdge(Edge edge);
    Optional<Node> removeNode(Node node);
    Optional<Edge> removeEdge(Edge edge);
}
