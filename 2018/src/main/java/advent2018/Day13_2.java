package advent2018;

import util.Direction;
import util.PixelCoordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day13_2 {

    private static final String FILENAME = "2018/day13_input.txt";
    private static final int SIZE = 150;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        System.out.println(solve(lines));
    }

    private static PixelCoordinate solve(List<String> lines) {
        Track track = parse(lines);
        return finalCart(track);
    }

    private static Track parse(List<String> lines) {
        char[][] track = new char[SIZE][SIZE];
        List<Cart> carts = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(track[i], ' ');
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char spot = line.charAt(j);
                if (spot == '>') {
                    spot = '-';
                    carts.add(new Cart(new PixelCoordinate(j, i), Direction.RIGHT));
                } else if (spot == '<') {
                    spot = '-';
                    carts.add(new Cart(new PixelCoordinate(j, i), Direction.LEFT));
                }
                else if (spot == '^') {
                    spot = '|';
                    carts.add(new Cart(new PixelCoordinate(j, i), Direction.UP));
                }
                else if (spot == 'v') {
                    spot = '|';
                    carts.add(new Cart(new PixelCoordinate(j, i), Direction.DOWN));
                }
                track[i][j] = spot;
            }
        }
        return new Track(track, carts);
    }

    private static PixelCoordinate finalCart(Track track) {
        List<Cart> carts = track.carts();
        while (carts.size() > 1) {
            carts.sort(Comparator.naturalOrder());
            Set<Cart> collidingCarts = new HashSet<>();
            for (Cart cart : carts) {
                if (!collidingCarts.contains(cart)) {
                    cart.step(track.track());
                    Cart collidingCart = checkColliding(carts, cart);
                    if (collidingCart != null) {
                        collidingCarts.add(collidingCart);
                        collidingCarts.add(cart);
                    }
                }
            }
            carts.removeAll(collidingCarts);
        }
        return carts.get(0).getLocation();
    }

    private static Cart checkColliding(List<Cart> carts, Cart cart) {
        for (Cart check : carts) {
            if (!cart.equals(check) && cart.getLocation().equals(check.getLocation())) {
                return check;
            }
        }
        return null;
    }

    private record Track(char[][] track, List<Cart> carts) {}

    private static class Cart implements Comparable<Cart> {
        private final PixelCoordinate location;
        private Direction direction;

        private int turnCount = 0;

        private Cart(PixelCoordinate location, Direction direction) {
            this.location = location;
            this.direction = direction;
        }

        public void step(char[][] track) {
            switch (direction) {
                case UP -> location.moveUp();
                case DOWN -> location.moveDown();
                case LEFT -> location.moveLeft();
                case RIGHT -> location.moveRight();
            }

            char newSpot = track[location.getY()][location.getX()];
            if (newSpot == '+') {
                turn();
            } else if (newSpot == '\\') {
                switch (direction) {
                    case UP, DOWN -> direction = direction.counterclockwise();
                    case LEFT, RIGHT -> direction = direction.clockwise();
                }
            } else if (newSpot == '/') {
                switch (direction) {
                    case UP, DOWN -> direction = direction.clockwise();
                    case LEFT, RIGHT -> direction = direction.counterclockwise();
                }
            }
        }

        public void turn() {
            if (turnCount == 0) {
                direction = direction.counterclockwise();
            } else if (turnCount == 2) {
                direction = direction.clockwise();
            }
            turnCount = (turnCount + 1) % 3;
        }

        public PixelCoordinate getLocation() {
            return location;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cart cart = (Cart) o;
            return turnCount == cart.turnCount && Objects.equals(location, cart.location) && direction == cart.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(location, direction, turnCount);
        }

        @Override
        public int compareTo(Cart o) {
            if (o.location.getX() != location.getX()) {
                return location.getX() - o.location.getX();
            } else {
                return location.getY() - o.location.getY();
            }
        }
    }
}
