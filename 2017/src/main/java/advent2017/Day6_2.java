package advent2017;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day6_2 {

    private static final String FILENAME = "2017/day6_input.txt";

    public static void main(String[] args) throws IOException {
        String input = Files.readAllLines(Paths.get(FILENAME)).get(0);
        Memory start = Memory.parse(input);
        int count = iterateUntilLoop(start);
        System.out.println(count);
    }

    private static int iterateUntilLoop(Memory start) {
        Map<Memory, Integer> seen = new HashMap<>();
        Memory current = start;
        int steps = 0;
        while (!seen.containsKey(current)) {
            seen.put(current, steps);
            steps++;
            current = redistribute(current);
        }
        return steps - seen.get(current);
    }

    private static Memory redistribute(Memory current) {
        int indexMax = findMax(current.memoryBanks);
        List<Integer> newMemoryBanks = new ArrayList<>(current.memoryBanks);
        int blocks = newMemoryBanks.get(indexMax);
        newMemoryBanks.set(indexMax, 0);
        for (int i = 0; i < blocks; i++) {
            int index = (indexMax + 1 + i) % newMemoryBanks.size();
            int blocksAtIndex = newMemoryBanks.get(index);
            newMemoryBanks.set(index, blocksAtIndex + 1);
        }
        return new Memory(newMemoryBanks);
    }

    private static int findMax(List<Integer> banks) {
        int maxIndex = 0;
        for (int i = 1; i < banks.size(); i++) {
            int blocks = banks.get(i);
            if (blocks > banks.get(maxIndex)) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private static class Memory {
        final List<Integer> memoryBanks;

        private Memory(List<Integer> memoryBanks) {
            this.memoryBanks = memoryBanks;
        }

        static Memory parse(String input) {
            List<Integer> banks = Lists.newArrayList(input.split("\\s+")).stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            return new Memory(banks);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Memory memory = (Memory) o;
            return memoryBanks.equals(memory.memoryBanks);
        }

        @Override
        public int hashCode() {
            return Objects.hash(memoryBanks);
        }
    }
}
