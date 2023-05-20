package util;

import java.util.ArrayList;
import java.util.List;

public class CircularList<T> {

    private final List<T> data;

    public CircularList() {
        this(100);
    }

    public CircularList(int size) {
        data = new ArrayList<>(size);
    }

    public void add(T item) {
        data.add(item);
    }

    public void add(int index, T item) {
        data.add(normalizeIndex(index), item);
    }

    public int addAfter(int index, T item) {
        int realIndex = normalizeIndex(index) + 1;
        if (realIndex == data.size()) {
            add(item);
            return data.size() - 1;
        } else {
            add(realIndex, item);
            return realIndex;
        }
    }

    public T remove(int index) {
        return data.remove(normalizeIndex(index));
    }

    public T get(int index) {
        return data.get(normalizeIndex(index));
    }

    private int normalizeIndex(int index) {
        int mod = index % data.size();
        if (mod < 0) {
            return mod + data.size();
        } else {
            return mod;
        }
    }
}
