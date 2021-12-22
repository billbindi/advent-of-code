package util;

import java.util.Objects;

/**
 * Simple class for a coordinate on a grid. Assumes (0, 0) in top left.
 * In other words, increasing x moves left, increasing y moves down.
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addX(int amount) {
        x += amount;
    }

    public void addY(int amount) {
        y += amount;
    }

    public int euclidDistance() {
        return Math.abs(x) + Math.abs(y);
    }

    public double distance() {
        return Math.sqrt((x * x) + (y * y));
    }

    public Coordinate coordinateLeft() {
        return new Coordinate(x - 1, y);
    }

    public Coordinate coordinateRight() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate coordinateUp() {
        return new Coordinate(x, y - 1);
    }

    public Coordinate coordinateDown() {
        return new Coordinate(x, y + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "util.Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
