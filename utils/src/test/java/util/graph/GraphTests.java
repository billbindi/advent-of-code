package util.graph;

public class GraphTests {
    public final static Node NODE_A = Node.of("A");
    public final static Node NODE_B = Node.of("B");
    public final static Node NODE_C = Node.of("C");
    public final static Node NODE_D = Node.of("D");
    public final static Node NODE_E = Node.of("E");
    public final static Node NODE_F = Node.of("F");

    public final static Edge EDGE_AB = Edge.of(NODE_A, NODE_B);
    public final static Edge EDGE_AC = Edge.of(NODE_A, NODE_C);
    public final static Edge EDGE_BE = Edge.of(NODE_B, NODE_E);
    public final static Edge EDGE_BF = Edge.of(NODE_B, NODE_F);
    public final static Edge EDGE_CD = Edge.of(NODE_C, NODE_D);
    public final static Edge EDGE_EF = Edge.of(NODE_E, NODE_F);
}
