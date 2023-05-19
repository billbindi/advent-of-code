package advent2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day8_1 {

    private static final String FILENAME = "2018/day8_input.txt";

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get(FILENAME));
        System.out.println(solve(input));
    }

    private static int solve(String input) {
        List<Integer> data = parseData(input);
        Node root = buildTree(data);
        return checksum(root);
    }

    private static List<Integer> parseData(String input) {
        return Arrays.stream(input.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static Node buildTree(List<Integer> data) {
        Node root = new Node();
        Header header = parseHeader(data, 0);
        Stack<NodeAndHeader> parents = new Stack<>();
        parents.add(new NodeAndHeader(root, header));

        // already primed with root
        int currentIndex = 2;
        while (!parents.isEmpty()) {
            NodeAndHeader current = parents.peek();
            if (isNodeComplete(current)) {
                // consume next few indices as metadata
                for (int i = 0; i < current.header.metadata; i++) {
                    current.node.addMetadata(data.get(currentIndex + i));
                }
                currentIndex += current.header.metadata;
                parents.pop();

                // now add child unless root
                if (!parents.isEmpty()) {
                    parents.peek().node.addChild(current.node);
                }
            } else {
                Node child = new Node();
                Header childHeader = parseHeader(data, currentIndex);
                parents.add(new NodeAndHeader(child, childHeader));
                currentIndex += 2;
            }
        }
        return root;
    }

    private static Header parseHeader(List<Integer> data, int index) {
        return new Header(data.get(index), data.get(index + 1));
    }

    private static boolean isNodeComplete(NodeAndHeader value) {
        return value.node.children.size() == value.header.children;
    }

    private static int checksum(Node node) {
        int childrenSum = node.children.stream()
                .mapToInt(Day8_1::checksum)
                .sum();
        return childrenSum + node.sum();
    }

    private static class Header {
        final int children;
        final int metadata;

        private Header(int children, int metadata) {
            this.children = children;
            this.metadata = metadata;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Header header = (Header) o;
            return children == header.children && metadata == header.metadata;
        }

        @Override
        public int hashCode() {
            return Objects.hash(children, metadata);
        }

        @Override
        public String toString() {
            return "Header{" +
                    "children=" + children +
                    ", metadata=" + metadata +
                    '}';
        }
    }

    private static class Node {
        final List<Node> children = new ArrayList<>();
        final List<Integer> metadata = new ArrayList<>();

        void addChild(Node child) {
            children.add(child);
        }

        void addMetadata(int value) {
            metadata.add(value);
        }

        int sum() {
            return metadata.stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        }
    }

    private static class NodeAndHeader {
        final Node node;
        final Header header;

        private NodeAndHeader(Node node, Header header) {
            this.node = node;
            this.header = header;
        }
    }
}
