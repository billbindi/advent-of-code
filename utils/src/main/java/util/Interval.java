package util;

import java.util.List;
import java.util.Objects;

public class Interval {
    public static final Interval EMPTY = new Interval(Integer.MAX_VALUE, Integer.MIN_VALUE);
    public static final Interval ALL = new Interval(Integer.MIN_VALUE, Integer.MAX_VALUE);

    private final int low;
    private final int high;

    public Interval(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public int getHigh() {
        return high;
    }

    public boolean isContained(int value) {
        return value >= low && value <= high;
    }

    public List<Interval> split(int removal) {
        return split(new Interval(removal, removal));
    }

    public List<Interval> split(Interval removal) {
        if (removal == null || removal.getLow() > high || removal.getHigh() < low || EMPTY.equals(removal)) {
            // nothing to remove
            return List.of(copy());
        } else if (removal.getLow() <= low && removal.getHigh() >= high) {
            // remove everything
            return List.of(EMPTY);
        } else if (removal.getLow() <= low) {
            int newLow = removal.getHigh() + 1;
            return List.of(new Interval(newLow, high));
        } else if (removal.getHigh() >= high) {
            int newHigh = removal.getLow() - 1;
            return List.of(new Interval(low, newHigh));
        } else {
            int newHigh = removal.getLow() - 1;
            int newLow = removal.getHigh() + 1;
            return List.of(
                    new Interval(low, newHigh),
                    new Interval(newLow, high));
        }
    }

    public Interval copy() {
        return new Interval(low, high);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return low == interval.low && high == interval.high;
    }

    @Override
    public int hashCode() {
        return Objects.hash(low, high);
    }

    @Override
    public String toString() {
        return "Interval{" +
                "low=" + low +
                ", high=" + high +
                '}';
    }
}
