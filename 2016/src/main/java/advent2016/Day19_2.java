package advent2016;

import java.util.ArrayList;
import java.util.List;

public class Day19_2 {
    private static final int NUM_ELVES = 3014387;

    public static void main(String[] args) {
        System.out.println(solve());
    }

    private static int solve() {
        List<Elf> elves = initElves();
        int size = elves.size();
        int stealIndex = size / 2;
        while (size > 1) {
            elves.get(stealIndex).steal();
            stealIndex = getNextStealIndex(elves, stealIndex);
            if (size % 2 == 1) {
                stealIndex = getNextStealIndex(elves, stealIndex);
            }
            size--;
        }
        return getSurvivor(elves);
    }

    private static int getNextStealIndex(List<Elf> elves, int currentIndex) {
        int nextIndex = (currentIndex + 1) % elves.size();
        while (elves.get(nextIndex).isStolen()) {
            nextIndex = (nextIndex + 1) % elves.size();
        }
        return nextIndex;
    }

    private static int getSurvivor(List<Elf> elves) {
        for (Elf elf : elves) {
            if (!elf.isStolen()) {
                return elf.getIndex();
            }
        }
        return -1;
    }

    private static List<Elf> initElves() {
        List<Elf> elves = new ArrayList<>();
        for (int i = 1; i <= NUM_ELVES; i++) {
            elves.add(new Elf(i));
        }
        return elves;
    }

    private static class Elf {
        int index;
        boolean isStolen;

        public Elf(int index) {
            this.index = index;
            this.isStolen = false;
        }

        public int getIndex() {
            return index;
        }

        public boolean isStolen() {
            return isStolen;
        }

        public void steal() {
            isStolen = true;
        }
    }
}
