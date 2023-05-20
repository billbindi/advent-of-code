package advent2018;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Day9 {

    private static final int NUM_PLAYERS = 463;
    private static final int LAST_MARBLE = 7178700;

    public static void main(String[] args) {
        Stopwatch timer = Stopwatch.createStarted();
        System.out.println(solve());
        timer.stop();
        System.out.println(timer.elapsed(TimeUnit.MILLISECONDS));
    }

    private static long solve() {
        List<Player> players = initPlayers();
        runGame(players);
        return players.stream()
                .max(Comparator.comparingLong(Player::getScore))
                .orElseThrow()
                .getScore();
    }

    private static List<Player> initPlayers() {
        List<Player> players = new ArrayList<>(NUM_PLAYERS);
        for (int i = 0; i < NUM_PLAYERS; i++) {
            players.add(new Player());
        }
        return players;
    }

    private static void runGame(List<Player> players) {
        Node currentIndex = new Node(0);
        for (int marble = 1; marble <= LAST_MARBLE; marble++) {
            if (marble % 23 == 0) {
                Player scorer = getPlayer(players, marble);
                currentIndex = currentIndex.seekLeft(7);

                scorer.addMarble(marble);
                scorer.addMarble(currentIndex.value);

                currentIndex = currentIndex.removeAndReturnAfter();
            } else {
                currentIndex = currentIndex.seekRight(1).addAfterAndReturn(marble);
            }
        }
    }

    private static Player getPlayer(List<Player> players, int marble) {
        return players.get(marble % players.size());
    }

    private static class Player {
        long score = 0;

        void addMarble(long value) {
            score += value;
        }

        long getScore() {
            return score;
        }
    }

    private static class Node {
        final long value;
        Node next;
        Node prev;

        private Node(long value) {
            this.value = value;
            next = this;
            prev = this;
        }

        Node addAfterAndReturn(long val) {
            Node node = new Node(val);

            this.next.prev = node;
            node.next = this.next;

            this.next = node;
            node.prev = this;

            return node;
        }

        Node removeAndReturnAfter() {
            Node holdAfter = this.next;

            this.next.prev = this.prev;
            this.prev.next = this.next;

            this.next = null;
            this.prev = null;

            return holdAfter;
        }

        Node seekRight(int numSteps) {
            Node ret = this;
            for (int i = 0; i < numSteps; i++) {
                ret = ret.next;
            }
            return ret;
        }

        Node seekLeft(int numSteps) {
            Node ret = this;
            for (int i = 0; i < numSteps; i++) {
                ret = ret.prev;
            }
            return ret;
        }

    }
}
