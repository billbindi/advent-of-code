package util;

public enum Direction {
    RIGHT, LEFT, DOWN, UP;

    public Direction clockwise() {
        return switch (this) {
            case RIGHT -> DOWN;
            case LEFT -> UP;
            case DOWN -> LEFT;
            case UP -> RIGHT;
        };
    }

    public Direction counterclockwise() {
        return switch (this) {
            case RIGHT -> UP;
            case LEFT -> DOWN;
            case DOWN -> RIGHT;
            case UP -> LEFT;
        };
    }

    public Direction reverse() {
        return switch (this) {
            case RIGHT -> LEFT;
            case LEFT -> RIGHT;
            case DOWN -> UP;
            case UP -> DOWN;
        };
    }
}
