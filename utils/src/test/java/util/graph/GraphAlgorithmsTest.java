package util.graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GraphAlgorithmsTest {

    @Test
    public void testPath() {
        SimpleGraph graph = new SimpleGraph();
        graph.addEdge(GraphTests.EDGE_AB);
        graph.addEdge(GraphTests.EDGE_AC);
        graph.addEdge(GraphTests.EDGE_BE);
        graph.addEdge(GraphTests.EDGE_CD);
        graph.addEdge(GraphTests.EDGE_EF);

        List<Node> actual = GraphAlgorithms.findShortestPath(graph, GraphTests.NODE_A, GraphTests.NODE_F);
        assertThat(actual).isNotEmpty();
        assertThat(actual).containsExactly(GraphTests.NODE_A, GraphTests.NODE_B, GraphTests.NODE_E, GraphTests.NODE_F);
    }
}
