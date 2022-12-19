package util;

import java.util.Objects;

public class PathElement {
    private final PixelCoordinate coord;
    private final int distance;

    public PathElement(PixelCoordinate coord, int distance) {
        this.coord = coord;
        this.distance = distance;
    }

    public static PathElement start(PixelCoordinate coord) {
        return new PathElement(coord, 0);
    }

    public PixelCoordinate getCoord() {
        return coord;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathElement that = (PathElement) o;
        return distance == that.distance && coord.equals(that.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord, distance);
    }

    @Override
    public String toString() {
        return "PathElement{" +
                "coord=" + coord +
                ", distance=" + distance +
                '}';
    }
}
