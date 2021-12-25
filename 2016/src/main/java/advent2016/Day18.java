package advent2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day18 {
    private static final String FIRST_ROW = ".^..^....^....^^.^^.^.^^.^.....^.^..^...^^^^^^.^^^^.^.^^^^^^^.^^^^^..^.^^^.^^..^.^^.^....^.^...^^.^.";
    private static final int NUM_ROWS = 40;
    // part 2
    //private static final int NUM_ROWS = 400_000;
    private static final Tile SAFE = new Tile(true);

    public static void main(String[] args) {
        System.out.println(solve());
    }

    private static int solve() {
        List<List<Tile>> room = new ArrayList<>(NUM_ROWS);
        room.add(parseInput());
        for (int i = 0; i < NUM_ROWS - 1; i++) {
            List<Tile> nextRow = computeRow(room.get(room.size() - 1));
            room.add(nextRow);
        }
        return numSafeTiles(room);
    }

    private static List<Tile> parseInput() {
        List<Tile> tiles = new ArrayList<>(FIRST_ROW.length());
        for (char tile : FIRST_ROW.toCharArray()) {
            if (tile == '.') {
                tiles.add(new Tile(true));
            } else if (tile == '^') {
                tiles.add(new Tile(false));
            } else {
                throw new IllegalArgumentException("First row not parsable for char '" + tile + "' in: " + FIRST_ROW);
            }
        }
        return tiles;
    }

    private static List<Tile> computeRow(List<Tile> previousRow) {
        List<Tile> newRow = new ArrayList<>(previousRow.size());
        for (int index = 0; index < previousRow.size(); index++) {
            Tile left = getPreviousRowTile(previousRow, index - 1);
            Tile right = getPreviousRowTile(previousRow, index + 1);
            Tile newTile = check(left, right);
            newRow.add(newTile);
        }
        return newRow;
    }

    private static Tile getPreviousRowTile(List<Tile> tiles, int index) {
        if (index < 0 || index >= tiles.size()) {
            return SAFE;
        } else {
            return tiles.get(index);
        }
    }

    private static Tile check(Tile left, Tile right) {
        boolean trapped = left.isSafe() ^ right.isSafe();
        return new Tile(!trapped);
    }

    private static int numSafeTiles(List<List<Tile>> room) {
        int numSafe = 0;
        for (List<Tile> row : room) {
            for (Tile tile : row) {
                if (tile.isSafe()) {
                    numSafe++;
                }
            }
        }
        return numSafe;
    }

    @SuppressWarnings("unused")
    private static void printRoom(List<List<Tile>> room) {
        for (List<Tile> row : room) {
            for (Tile tile : row) {
                if (tile.isSafe()) {
                    System.out.print('.');
                } else {
                    System.out.print('^');
                }
            }
            System.out.println();
        }
    }

    private static class Tile {

        boolean isSafe;

        public Tile(boolean isSafe) {
            this.isSafe = isSafe;
        }

        public boolean isSafe() {
            return isSafe;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return isSafe == tile.isSafe;
        }

        @Override
        public int hashCode() {
            return Objects.hash(isSafe);
        }

        @Override
        public String toString() {
            return "Tile{" +
                    "isSafe=" + isSafe +
                    '}';
        }
    }
}
