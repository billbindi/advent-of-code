package util.graph;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public final class GraphAlgorithms {

    /**
     * Dijkstra's algorithm for shortest path
     */
    public static List<Node> findShortestPath(Graph graph, Node start, Node end) {
        if (!checkEdgeWeights(graph)) {
            throw new IllegalArgumentException("Cannot compute path for graph with some weighted edges and some"
                    + " non-weighted edges.\nGraph: " + graph);
        } else {
            // tracking structures
            PriorityQueue<NodeWithValue> queue = new PriorityQueue<>(Comparator.comparingInt(NodeWithValue::value));
            Map<Node, Integer> distanceMap = new HashMap<>(graph.nodes().size());
            Map<Node, Node> parentMap = new HashMap<>(graph.nodes().size());

            // initial state
            distanceMap.put(start, 0);
            queue.add(new NodeWithValue(start, 0));

            while (!queue.isEmpty()) {
                NodeWithValue current = queue.poll();
                int distance = current.value();
                Node node = current.node();

                if (node.equals(end)) {
                    // found our target
                    break;
                }

                if (distanceMap.getOrDefault(node, Integer.MAX_VALUE) < distance) {
                    // already found a better path to node
                    continue;
                }

                for (Edge incident : graph.incidentEdges(node)) {
                    Node adjacent = getOther(incident, node);
                    int edgeDistance = incident.weight().orElse(1);
                    int testDistance = distanceMap.get(node) + edgeDistance;
                    if (testDistance < distanceMap.getOrDefault(adjacent, Integer.MAX_VALUE)) {
                        distanceMap.put(adjacent, testDistance);
                        queue.offer(new NodeWithValue(adjacent, testDistance));
                        parentMap.put(adjacent, node);
                    }
                }
            }

            if (parentMap.containsKey(end)) {
                // build the path and reverse
                List<Node> reversePath = new ArrayList<>(parentMap.size());
                Node currentPathNode = end;
                while (currentPathNode != start) {
                    reversePath.add(currentPathNode);
                    currentPathNode = parentMap.get(currentPathNode);
                }
                reversePath.add(start);
                return Lists.reverse(reversePath);
            } else {
                // did not find a path
                return List.of();
            }
        }
    }

    public static Node getOther(Edge edge, Node node) {
        if (edge.lower().equals(node)) {
            return edge.upper();
        } else if (edge.upper().equals(node)) {
            return edge.lower();
        } else {
            throw new IllegalArgumentException("Given node [" + node + "] is not in the edge: " + edge);
        }
    }

    private static boolean checkEdgeWeights(Graph graph) {
        Set<Edge> edges = graph.edges();
        boolean allWeightedEdges = edges.stream().allMatch(edge -> edge.weight().isPresent());
        boolean allUnweightedEdges = edges.stream().allMatch(edge -> edge.weight().isEmpty());
        return allWeightedEdges || allUnweightedEdges;
    }

    private GraphAlgorithms() {}

    private record NodeWithValue(Node node, int value) {}
}
