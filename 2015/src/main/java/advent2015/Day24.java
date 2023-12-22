package advent2015;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day24 {
    private static final String FILENAME = "day24_input.txt";

    public static void main(String[] args) throws IOException {
        System.out.println(solve(parse()));
    }

    private static List<Integer> parse() throws IOException {
        return Files.readAllLines(Path.of(FILENAME))
                .stream()
                .filter(s -> !Strings.isNullOrEmpty(s))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static long solve(List<Integer> packages) {
        int sum = packages.stream().mapToInt(Integer::intValue).sum();
        int balance = sum / 3;
        List<Set<Integer>> solutions = new ArrayList<>();
        minPackages(packages, balance, solutions, new HashSet<>(), 0, 0);
        return minQuantum(packages, solutions);
    }

    private static long minQuantum(List<Integer> packages, List<Set<Integer>> solutions) {
        int minIndex = 0;
        for (int i = 0; i < solutions.size(); i++) {
            Set<Integer> possible = solutions.get(i);
            Set<Integer> min = solutions.get(minIndex);
            if (possible.size() < min.size()) {
                minIndex = i;
            } else if (possible.size() == min.size() && quantum(packages, possible) < quantum(packages, min)) {
                minIndex = i;
            }
        }
        return quantum(packages, solutions.get(minIndex));
    }

    private static long quantum(List<Integer> packages, Set<Integer> possible) {
        return possible.stream().map(packages::get).mapToLong(Integer::longValue).reduce(1, (a, b) -> a * b);
    }

    private static void minPackages(
            List<Integer> packages,
            int balance,
            List<Set<Integer>> solutions,
            Set<Integer> curr,
            int index,
            int sum) {
        if (sum == balance && canSolve(packages, balance, curr)) {
            solutions.add(curr);
        } else if (index < packages.size() && curr.size() <= packages.size() / 3 && sum <= balance) {
            // don't take current
            minPackages(packages, balance, solutions, new HashSet<>(curr), index + 1, sum);

            // do take current
            int weight = packages.get(index);
            curr.add(index);
            minPackages(packages, balance, solutions, new HashSet<>(curr), index + 1, sum + weight);
        }
    }

    private static boolean canSolve(List<Integer> packages, int balance, Set<Integer> curr) {
        List<Integer> remaining = Lists.newArrayListWithCapacity(packages.size());
        for (int i = 0; i < packages.size(); i++) {
            if (!curr.contains(i)) {
                remaining.add(packages.get(i));
            }
        }
        return canSolve(remaining, 0, balance);
    }

    private static boolean canSolve(List<Integer> remaining, int index, int balance) {
        if (balance == 0) {
            return true;
        } else if (index >= remaining.size() || balance < 0) {
            return false;
        } else {
            return canSolve(remaining, index + 1, balance) ||
                    canSolve(remaining, index + 1, balance - remaining.get(index));
        }
    }
}
