package util;

import java.util.Objects;

/**
 * Simple class for a pixels on a screen. Assumes (0, 0) in top left.
 * In other words, increasing x moves right, increasing y moves down.
 */
public class PixelCoordinate {
    private int x;
    private int y;

    public PixelCoordinate(int x, int y) {
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

    public PixelCoordinate coordinateLeft() {
        return new PixelCoordinate(x - 1, y);
    }

    public void moveLeft() {
        x--;
    }

    public PixelCoordinate coordinateRight() {
        return new PixelCoordinate(x + 1, y);
    }

    public void moveRight() {
        x++;
    }

    public PixelCoordinate coordinateUp() {
        return new PixelCoordinate(x, y - 1);
    }

    public void moveUp() {
        y--;
    }

    public PixelCoordinate coordinateDown() {
        return new PixelCoordinate(x, y + 1);
    }

    public void moveDown() {
        y++;
    }

    // check if this coordinate is in bounds in a WxH bounding box
    public boolean isInBounds(int width, int height) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixelCoordinate that = (PixelCoordinate) o;
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
