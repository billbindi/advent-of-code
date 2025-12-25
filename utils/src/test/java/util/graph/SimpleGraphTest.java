package util.graph;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleGraphTest {

    private final static Node A = Node.of("A");
    private final static Node B = Node.of("B");
    private final static Node C = Node.of("C");
    private final static Node D = Node.of("D");
    private final static Node E = Node.of("E");
    private final static Node F = Node.of("F");

    private final static Edge AB = Edge.of(A, B);
    private final static Edge AC = Edge.of(A, C);
    private final static Edge CD = Edge.of(C, D);

    @Test
    public void testAddNode() {
        SimpleGraph graph = new SimpleGraph();
        graph.addNode(A);
        graph.addNode(B);
        graph.addNode(A);

        assertThat(graph.nodes()).hasSize(2);
        assertThat(graph.nodes()).containsExactlyInAnyOrder(A, B);
    }

    @Test
    public void testAddEdge() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(AB);

        assertThat(graph.nodes()).hasSize(2);
        assertThat(graph.nodes()).containsExactlyInAnyOrder(A, B);
        assertThat(graph.edges()).hasSize(1);
        assertThat(graph.edges()).containsExactly(AB);
    }

    @Test
    public void testRemoveNode() {
        SimpleGraph graph = new SimpleGraph();
        graph.addNode(A);
        assertThat(graph.nodes()).containsExactly(A);

        graph.removeNode(A);
        assertThat(graph.nodes()).isEmpty();
    }

    @Test
    public void testRemoveNodeWithEdges() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(AC);
        graph.addEdge(CD);
        assertThat(graph.nodes()).containsExactlyInAnyOrder(A, C, D);
        assertThat(graph.edges()).containsExactlyInAnyOrder(AC, CD);

        graph.removeNode(A);
        assertThat(graph.nodes()).containsExactlyInAnyOrder(C, D);
        assertThat(graph.edges()).containsExactly(CD);
    }

    @Test
    public void testRemoveEdge() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(AB);
        assertThat(graph.edges()).containsExactly(AB);

        graph.removeEdge(AB);
        assertThat(graph.edges()).isEmpty();
    }

    @Test
    public void testIncidentEdges() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(AB);
        graph.addEdge(AC);
        graph.addEdge(CD);
        graph.addNode(E);
        graph.addNode(F);

        assertThat(graph.incidentEdges(A)).containsExactlyInAnyOrder(AB, AC);
        assertThat(graph.incidentEdges(D)).containsExactly(CD);
        assertThat(graph.incidentEdges(E)).isEmpty();
    }

    @Test
    public void testAdjacentNodes() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(AB);
        graph.addEdge(AC);
        graph.addEdge(CD);
        graph.addNode(E);
        graph.addNode(F);

        assertThat(graph.adjacentNodes(A)).containsExactlyInAnyOrder(B, C);
        assertThat(graph.adjacentNodes(C)).containsExactlyInAnyOrder(A, D);
        assertThat(graph.adjacentNodes(E)).isEmpty();
    }
}
