package util;

public enum Direction {
    RIGHT, LEFT, DOWN, UP;

    public Direction clockwise() {
        switch (this) {
            case RIGHT:
                return DOWN;
            case LEFT:
                return UP;
            case DOWN:
                return LEFT;
            case UP:
                return RIGHT;
            default:
                throw new IllegalStateException("CANNOT TURN FROM UNKNOWN DIRECTION " + this);
        }
    }

    public Direction counterclockwise() {
        switch (this) {
            case RIGHT:
                return UP;
            case LEFT:
                return DOWN;
            case DOWN:
                return RIGHT;
            case UP:
                return LEFT;
            default:
                throw new IllegalStateException("CANNOT TURN FROM UNKNOWN DIRECTION " + this);
        }
    }

    public Direction reverse() {
        switch (this) {
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
            case DOWN:
                return UP;
            case UP:
                return DOWN;
            default:
                throw new IllegalStateException("CANNOT TURN FROM UNKNOWN DIRECTION " + this);
        }
    }
}
