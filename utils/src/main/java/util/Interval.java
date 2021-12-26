package util;

import java.util.List;
import java.util.Objects;

public class Interval {
    public static final Interval EMPTY = new Interval(Long.MAX_VALUE, Long.MIN_VALUE);
    public static final Interval ALL = new Interval(Long.MIN_VALUE, Long.MAX_VALUE);

    private final long low;
    private final long high;

    public Interval(long low, long high) {
        this.low = low;
        this.high = high;
    }

    public long getLow() {
        return low;
    }

    public long getHigh() {
        return high;
    }

    public boolean isContained(long value) {
        return value >= low && value <= high;
    }

    public boolean isEmpty() {
        return this.equals(EMPTY);
    }

    public List<Interval> split(long removal) {
        return split(new Interval(removal, removal));
    }

    public long size() {
        if (isEmpty()) {
            return 0;
        } else {
            return high - low + 1;
        }
    }

    public List<Interval> split(Interval removal) {
        if (removal == null || removal.getLow() > high || removal.getHigh() < low || EMPTY.equals(removal)) {
            // nothing to remove
            return List.of(copy());
        } else if (removal.getLow() <= low && removal.getHigh() >= high) {
            // remove everything
            return List.of();
        } else if (removal.getLow() <= low) {
            long newLow = removal.getHigh() + 1;
            return List.of(new Interval(newLow, high));
        } else if (removal.getHigh() >= high) {
            long newHigh = removal.getLow() - 1;
            return List.of(new Interval(low, newHigh));
        } else {
            long newHigh = removal.getLow() - 1;
            long newLow = removal.getHigh() + 1;
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
