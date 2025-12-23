package advent2019;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
            Optional<Operation> op = Operation.fromOpCode(opCode);
            if (op.isPresent()) {
                op.get().perform(this, pointer);
            } else {
                // unrecognized op means something went wrong
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

    public int getValueAtIndexValue(int index) {
        return getValue(getValue(index));
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

    public enum Operation {
        ADDITION(1, 3) {
            @Override
            public void performOperation(IntcodeComputer computer, int index) {
                int operand1 = computer.getValueAtIndexValue(index + 1);
                int operand2 = computer.getValueAtIndexValue(index + 2);
                int operand3 = computer.getValue(index + 3);
                computer.setValue(operand3, operand1 + operand2);
            }
        },
        MULTIPLICATION(2, 3) {
            @Override
            public void performOperation(IntcodeComputer computer, int index) {
                int operand1 = computer.getValueAtIndexValue(index + 1);
                int operand2 = computer.getValueAtIndexValue(index + 2);
                int operand3 = computer.getValue(index + 3);
                computer.setValue(operand3, operand1 * operand2);
            }
        },
        HALT(99, 0) {
            @Override
            public void performOperation(IntcodeComputer computer, int index) {
                computer.markComplete();
            }
        };

        private final int opCode;
        private final int neededOperands;

        Operation(int opCode, int neededOperands) {
            this.opCode = opCode;
            this.neededOperands = neededOperands;
        }

        public abstract void performOperation(IntcodeComputer computer, int index);

        public void perform(IntcodeComputer computer, int index) {
            if (computer.checkOperation(index, neededOperands)) {
                performOperation(computer, index);
            } else {
                computer.markCompleteWithError();
            }
        }

        public static Optional<Operation> fromOpCode(int opCode) {
            for (Operation op : Operation.values()) {
                if (op.opCode == opCode) {
                    return Optional.of(op);
                }
            }
            System.err.println("No operation with code " + opCode);
            return Optional.empty();
        }
    }
}
