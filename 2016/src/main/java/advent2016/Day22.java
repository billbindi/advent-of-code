package advent2016;

import util.Coordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 {
    private static final String NODE_REGEX = "/dev/grid/node-x(?<x>\\d+)-y(?<y>\\d+)\\s*(?<size>\\d+)T\\s*(?<used>\\d+)T\\s*(?<avail>\\d+)T\\s*(?<usePct>\\d+)%";
    private static final Pattern NODE_PATTERN = Pattern.compile(NODE_REGEX);

    private static final int GRID_HEIGHT = 27;
    private static final int GRID_WIDTH = 37;

    private static final String FILENAME = "2016/day22_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        Node[][] grid = parseNodes(content);
        prettyPrintGrid(grid);
        return countViable(grid);
    }

    private static int countViable(Node[][] grid) {
        int total = 0;
        for (Node[] row : grid) {
            for (Node node : row) {
                total += countViable(grid, node);
            }
        }
        return total;
    }

    private static int countViable(Node[][] grid, Node start) {
        if (start.getUsed() == 0) {
            return 0;
        } else {
            int total = 0;
            for (Node[] row : grid) {
                for (Node node : row) {
                    if (!start.equals(node) && node.getAvail() >= start.getUsed())
                        total ++;
                }
            }
            return total;
        }
    }

    private static void prettyPrintGrid(Node[][] grid) {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (x == 0 && y == 0) {
                    System.out.print("S ");
                } else if (x == GRID_WIDTH - 1 && y == 0) {
                    System.out.print("G ");
                } else if (grid[y][x].getUsed() >= 100) {
                    System.out.print("# ");
                } else if (grid[y][x].getUsePct() == 0) {
                    System.out.print("_ ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    private static Node[][] parseNodes(List<String> content) {
        Node[][] grid = new Node[GRID_HEIGHT][GRID_WIDTH];
        for (String node : content) {
            Matcher m = NODE_PATTERN.matcher(node);
            if (m.find()) {
                int x = Integer.parseInt(m.group("x"));
                int y = Integer.parseInt(m.group("y"));
                int size = Integer.parseInt(m.group("size"));
                int used = Integer.parseInt(m.group("used"));
                int avail = Integer.parseInt(m.group("avail"));
                int usePct = Integer.parseInt(m.group("usePct"));

                grid[y][x] = new Node(new Coordinate(x, y), size, used, avail, usePct);
            } else {
                throw new IllegalStateException("Could not parse node: " + node);
            }
        }
        return grid;
    }

    private static class Node {
        Coordinate coord;
        int size;
        int used;
        int avail;
        int usePct;

        public Node(Coordinate coord, int size, int used, int avail, int usePct) {
            this.coord = coord;
            this.size = size;
            this.used = used;
            this.avail = avail;
            this.usePct = usePct;
        }

        public Coordinate getCoord() {
            return coord;
        }

        public int getSize() {
            return size;
        }

        public int getUsed() {
            return used;
        }

        public int getAvail() {
            return avail;
        }

        public int getUsePct() {
            return usePct;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return size == node.size && used == node.used && avail == node.avail && usePct == node.usePct && Objects.equals(coord, node.coord);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coord, size, used, avail, usePct);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "coord=" + coord +
                    ", size=" + size +
                    ", used=" + used +
                    ", avail=" + avail +
                    ", usePct=" + usePct +
                    '}';
        }
    }
}
