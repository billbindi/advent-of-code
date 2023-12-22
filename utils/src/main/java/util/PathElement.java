package util;

public record PathElement(PixelCoordinate coord, int distance) {

    public static PathElement start(PixelCoordinate coord) {
        return new PathElement(coord, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathElement that = (PathElement) o;
        return distance == that.distance && coord.equals(that.coord);
    }

    @Override
    public String toString() {
        return "PathElement{" +
                "coord=" + coord +
                ", distance=" + distance +
                '}';
    }
}
