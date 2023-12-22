package util;

import com.google.common.base.Preconditions;

import java.util.Arrays;

public class DisjointSets {

    private final int[] sets;

    private DisjointSets(int[] sets) {
        this.sets = sets;
    }

    public static DisjointSets withSize(int size) {
        int[] sets = new int[size];
        Arrays.fill(sets, -1);
        return new DisjointSets(sets);
    }

    public int size() {
        return sets.length;
    }

    public int setSize(int index) {
        return -1 * sets[findRoot(index)];
    }

    public int numSets() {
        int count = 0;
        for (int index : sets) {
            if (index < 0) {
                count++;
            }
        }
        return count;
    }

    public void combine(int firstIndex, int secondIndex) {
        Preconditions.checkArgument(firstIndex >= 0 && firstIndex < size());
        Preconditions.checkArgument(secondIndex >= 0 && secondIndex < size());

        int firstRoot = findRoot(firstIndex);
        int secondRoot = findRoot(secondIndex);

        if (firstRoot == secondRoot) {
            // already in the same group
            return;
        }

        sets[firstRoot] += sets[secondRoot];
        sets[secondRoot] = firstRoot;
    }

    public int findRoot(int index) {
        if (sets[index] < 0) {
            return index;
        } else {
            sets[index] = findRoot(sets[index]);
            return sets[index];
        }
    }
}
