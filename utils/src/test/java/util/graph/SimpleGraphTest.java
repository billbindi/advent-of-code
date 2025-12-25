package util.graph;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleGraphTest {

    @Test
    public void testAddNode() {
        SimpleGraph graph = new SimpleGraph();
        graph.addNode(GraphTests.NODE_A);
        graph.addNode(GraphTests.NODE_B);
        graph.addNode(GraphTests.NODE_A);

        assertThat(graph.nodes()).hasSize(2);
        assertThat(graph.nodes()).containsExactlyInAnyOrder(GraphTests.NODE_A, GraphTests.NODE_B);
    }

    @Test
    public void testAddEdge() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(GraphTests.EDGE_AB);

        assertThat(graph.nodes()).hasSize(2);
        assertThat(graph.nodes()).containsExactlyInAnyOrder(GraphTests.NODE_A, GraphTests.NODE_B);
        assertThat(graph.edges()).hasSize(1);
        assertThat(graph.edges()).containsExactly(GraphTests.EDGE_AB);
    }

    @Test
    public void testRemoveNode() {
        SimpleGraph graph = new SimpleGraph();
        graph.addNode(GraphTests.NODE_A);
        assertThat(graph.nodes()).containsExactly(GraphTests.NODE_A);

        graph.removeNode(GraphTests.NODE_A);
        assertThat(graph.nodes()).isEmpty();
    }

    @Test
    public void testRemoveNodeWithEdges() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(GraphTests.EDGE_AC);
        graph.addEdge(GraphTests.EDGE_CD);
        assertThat(graph.nodes())
                .containsExactlyInAnyOrder(GraphTests.NODE_A, GraphTests.NODE_C, GraphTests.NODE_D);
        assertThat(graph.edges()).containsExactlyInAnyOrder(GraphTests.EDGE_AC, GraphTests.EDGE_CD);

        graph.removeNode(GraphTests.NODE_A);
        assertThat(graph.nodes()).containsExactlyInAnyOrder(GraphTests.NODE_C, GraphTests.NODE_D);
        assertThat(graph.edges()).containsExactly(GraphTests.EDGE_CD);
    }

    @Test
    public void testRemoveEdge() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(GraphTests.EDGE_AB);
        assertThat(graph.edges()).containsExactly(GraphTests.EDGE_AB);

        graph.removeEdge(GraphTests.EDGE_AB);
        assertThat(graph.edges()).isEmpty();
    }

    @Test
    public void testIncidentEdges() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(GraphTests.EDGE_AB);
        graph.addEdge(GraphTests.EDGE_AC);
        graph.addEdge(GraphTests.EDGE_CD);
        graph.addNode(GraphTests.NODE_E);
        graph.addNode(GraphTests.NODE_F);

        assertThat(graph.incidentEdges(GraphTests.NODE_A))
                .containsExactlyInAnyOrder(GraphTests.EDGE_AB, GraphTests.EDGE_AC);
        assertThat(graph.incidentEdges(GraphTests.NODE_D)).containsExactly(GraphTests.EDGE_CD);
        assertThat(graph.incidentEdges(GraphTests.NODE_E)).isEmpty();
    }

    @Test
    public void testAdjacentNodes() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(GraphTests.EDGE_AB);
        graph.addEdge(GraphTests.EDGE_AC);
        graph.addEdge(GraphTests.EDGE_CD);
        graph.addNode(GraphTests.NODE_E);
        graph.addNode(GraphTests.NODE_F);

        assertThat(graph.adjacentNodes(GraphTests.NODE_A))
                .containsExactlyInAnyOrder(GraphTests.NODE_B, GraphTests.NODE_C);
        assertThat(graph.adjacentNodes(GraphTests.NODE_C))
                .containsExactlyInAnyOrder(GraphTests.NODE_A, GraphTests.NODE_D);
        assertThat(graph.adjacentNodes(GraphTests.NODE_E)).isEmpty();
    }
}
