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

    @Test
    public void testAddNode() {
        SimpleGraph graph = new SimpleGraph();
        graph.addNode(A);
        graph.addNode(B);
        graph.addNode(A);

        assertThat(graph.nodes()).hasSize(2);
        assertThat(graph.nodes()).containsExactly(A, B);
    }
}
