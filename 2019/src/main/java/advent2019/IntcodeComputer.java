package advent2019;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntcodeComputer {
    // opcodes
    private static final int ADDITION = 1;
    private static final int MULTIPLICATION = 2;
    private static final int HALT = 99;

    private final List<Integer> program;
    private boolean isComplete = false;
    private boolean isError = false;

    public IntcodeComputer(List<Integer> program) {
        this.program = program;
    }

    public static IntcodeComputer fromLine(String line) {
        List<Integer> program = Arrays.stream(line.split("\\s*,\\s*")).map(Integer::parseInt).collect(Collectors.toList());
        return new IntcodeComputer(program);
    }

    // modifies IN PLACE
    public boolean run() {
        int pointer = 0;
        while (!isComplete && pointer < program.size()) {
            int opCode = getValue(pointer);
            switch (opCode) {
                case ADDITION:
                    if (checkOperation(pointer, 3)) {
                        setValue(getValue(pointer + 3), getValue(getValue(pointer + 1)) + getValue(getValue(pointer + 2)));
                    } else {
                        markCompleteWithError();
                    }
                    break;
                case MULTIPLICATION:
                    if (checkOperation(pointer, 3)) {
                        setValue(getValue(pointer + 3), getValue(getValue(pointer + 1)) * getValue(getValue(pointer + 2)));
                    } else {
                        markCompleteWithError();
                    }
                    break;
                case HALT:
                    markComplete();
                    break;
                default:
                    // any other op code is an error state
                    markCompleteWithError();
            }
            pointer += 4;
        }

        // if we somehow didn't reach a complete state, mark complete now with error
        if (!isComplete) {
            markCompleteWithError();
        }
        return isError;
    }

    public int getValue(int index) {
        return program.get(index);
    }

    public List<Integer> getRawProgram() {
        return program;
    }

    // accessible for testing
    void setValue(int index, int value) {
        program.set(index, value);
    }

    // check the pointer is in a spot to allow for an operation
    private boolean checkOperation(int pointerIndex, int opSize) {
        return program.size() >= pointerIndex + opSize;
    }

    private void markComplete() {
        isComplete = true;
    }

    private void markCompleteWithError() {
        isComplete = true;
        isError = true;
    }
}
