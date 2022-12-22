package advent2017;

import java.util.HashMap;
import java.util.Map;

public class Day25 {

    private static final int NUM_STEPS = 12629077;
    private static final State START_STATE = State.A;

    public static void main(String[] args) {
        int checksum = runMachine();
        System.out.println(checksum);
    }

    private static int runMachine() {
        State currState = START_STATE;
        Tape tape = new Tape();
        for (int i = 0; i < NUM_STEPS; i++) {
            currState = currState.performStep(tape);
        }
        return tape.checksum();
    }

    private enum State {
        A, B, C, D, E, F;

        State performStep(Tape tape) {
            switch (this) {
                case A:
                    if (tape.valueAtCursor() == 0) {
                        tape.writeValue(1);
                        tape.moveRight();
                        return B;
                    } else if (tape.valueAtCursor() == 1) {
                        tape.writeValue(0);
                        tape.moveLeft();
                        return B;
                    } else {
                        throw new IllegalStateException("Uhh, why is the tape value " + tape.valueAtCursor() + "?");
                    }
                case B:
                    if (tape.valueAtCursor() == 0) {
                        tape.writeValue(0); // redundant
                        tape.moveRight();
                        return C;
                    } else if (tape.valueAtCursor() == 1) {
                        tape.writeValue(1); // redundant
                        tape.moveLeft();
                        return B;
                    } else {
                        throw new IllegalStateException("Uhh, why is the tape value " + tape.valueAtCursor() + "?");
                    }
                case C:
                    if (tape.valueAtCursor() == 0) {
                        tape.writeValue(1);
                        tape.moveRight();
                        return D;
                    } else if (tape.valueAtCursor() == 1) {
                        tape.writeValue(0);
                        tape.moveLeft();
                        return A;
                    } else {
                        throw new IllegalStateException("Uhh, why is the tape value " + tape.valueAtCursor() + "?");
                    }
                case D:
                    if (tape.valueAtCursor() == 0) {
                        tape.writeValue(1);
                        tape.moveLeft();
                        return E;
                    } else if (tape.valueAtCursor() == 1) {
                        tape.writeValue(1); // redundant
                        tape.moveLeft();
                        return F;
                    } else {
                        throw new IllegalStateException("Uhh, why is the tape value " + tape.valueAtCursor() + "?");
                    }
                case E:
                    if (tape.valueAtCursor() == 0) {
                        tape.writeValue(1);
                        tape.moveLeft();
                        return A;
                    } else if (tape.valueAtCursor() == 1) {
                        tape.writeValue(0);
                        tape.moveLeft();
                        return D;
                    } else {
                        throw new IllegalStateException("Uhh, why is the tape value " + tape.valueAtCursor() + "?");
                    }
                case F:
                    if (tape.valueAtCursor() == 0) {
                        tape.writeValue(1);
                        tape.moveRight();
                        return A;
                    } else if (tape.valueAtCursor() == 1) {
                        tape.writeValue(1); // redundant
                        tape.moveLeft();
                        return E;
                    } else {
                        throw new IllegalStateException("Uhh, why is the tape value " + tape.valueAtCursor() + "?");
                    }
                default:
                    throw new IllegalStateException("INVALID CURRENT STATE: " + this);
            }
        }
    }

    private static class Tape {
        final Map<Integer, Integer> tape = new HashMap<>();
        int index = 0;

        int valueAtCursor() {
            if (!tape.containsKey(index)) {
                tape.put(index, 0);
            }
            return tape.get(index);
        }

        void writeValue(int value) {
            tape.put(index, value);
        }

        void moveLeft() {
            index--;
        }

        void moveRight() {
            index++;
        }

        public int checksum() {
            return tape.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        }
    }
}
