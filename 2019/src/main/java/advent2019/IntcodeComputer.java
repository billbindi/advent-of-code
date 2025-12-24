package advent2019;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IntcodeComputer {
    private final ImmutableList<Integer> initialMemory;
    private final List<Integer> memory;
    private boolean isComplete;
    private boolean isError;
    private Optional<Integer> input = Optional.empty();

    public IntcodeComputer(List<Integer> initialMemory) {
        this.initialMemory = ImmutableList.copyOf(initialMemory);
        this.memory = new ArrayList<>();
        reset();
    }

    public void reset() {
        memory.clear();
        memory.addAll(initialMemory);
        isComplete = false;
        isError = false;
        input = Optional.empty();
    }

    public static IntcodeComputer fromLine(String line) {
        List<Integer> program = Arrays.stream(line.split("\\s*,\\s*")).map(Integer::parseInt).toList();
        return new IntcodeComputer(program);
    }

    public void setInput(int input) {
        this.input = Optional.of(input);
    }

    public boolean runWithNounVerb(int noun, int verb) {
        setValue(1, noun);
        setValue(2, verb);
        return run();
    }

    // only works on 'memory' variable to keep initial state constant
    public boolean run() {
        int pointer = 0;
        while (!isComplete && pointer < memory.size()) {
            OpCode opCode = OpCode.fromValue(getValue(pointer));
            Optional<Instruction> instruction = Instruction.fromOpCode(opCode.code());
            if (instruction.isPresent()) {
                // move pointer by proper amount for operation
                pointer = instruction.get().perform(this, pointer, opCode.parameterModes());
            } else {
                // unrecognized instruction means something went wrong
                markCompleteWithError();
            }
        }

        // if we somehow didn't reach a complete state, mark complete now with error
        if (!isComplete) {
            markCompleteWithError();
        }
        return isError;
    }

    public int getValue(int index) {
        return memory.get(index);
    }

    public int getInput() {
        return input.orElseThrow();
    }

    public int getOutput() {
        return getValue(0);
    }

    public int getValueAtIndexValue(int index) {
        return getValue(getValue(index));
    }

    public List<Integer> getRawProgram() {
        return memory;
    }

    // accessible for testing
    void setValue(int index, int value) {
        memory.set(index, value);
    }

    // check the pointer is in a spot to allow for an operation
    private boolean checkOperation(int pointerIndex, int instructionSize) {
        return memory.size() > pointerIndex + instructionSize;
    }

    private void markComplete() {
        isComplete = true;
    }

    private void markCompleteWithError() {
        isComplete = true;
        isError = true;
    }

    public enum Instruction {
        ADDITION(1, 4) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                int operand1 = getValue(computer, index + 1, parameterModes[0]);
                int operand2 = getValue(computer, index + 2, parameterModes[1]);
                int operand3 = computer.getValue(index + 3);
                computer.setValue(operand3, operand1 + operand2);
                return index + 4;
            }
        },
        MULTIPLICATION(2, 4) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                int operand1 = getValue(computer, index + 1, parameterModes[0]);
                int operand2 = getValue(computer, index + 2, parameterModes[1]);
                int operand3 = computer.getValue(index + 3);
                computer.setValue(operand3, operand1 * operand2);
                return index + 4;
            }
        },
        INPUT(3, 2) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] _parameterModes) {
                int operand1 = computer.getValue(index + 1);
                computer.setValue(operand1, computer.getInput());
                return index + 2;
            }
        },
        OUTPUT(4, 2) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                System.out.println("OUTPUT: " + getValue(computer, index + 1, parameterModes[0]));
                return index + 2;
            }
        },
        JUMP_IF_TRUE(5, 3) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                int operand1 = getValue(computer, index + 1, parameterModes[0]);
                int operand2 = getValue(computer, index + 2, parameterModes[1]);
                return operand1 == 0 ? index + 3 : operand2;
            }
        },
        JUMP_IF_FALSE(6, 3) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                int operand1 = getValue(computer, index + 1, parameterModes[0]);
                int operand2 = getValue(computer, index + 2, parameterModes[1]);
                return operand1 == 0 ? operand2 : index + 3;
            }
        },
        LESS_THAN(7, 4) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                int operand1 = getValue(computer, index + 1, parameterModes[0]);
                int operand2 = getValue(computer, index + 2, parameterModes[1]);
                int operand3 = computer.getValue(index + 3);
                int result = operand1 < operand2 ? 1 : 0;
                computer.setValue(operand3, result);
                return index + 4;
            }
        },
        EQUALS(8, 4) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                int operand1 = getValue(computer, index + 1, parameterModes[0]);
                int operand2 = getValue(computer, index + 2, parameterModes[1]);
                int operand3 = computer.getValue(index + 3);
                int result = operand1 == operand2 ? 1 : 0;
                computer.setValue(operand3, result);
                return index + 4;
            }
        },
        HALT(99, 1) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] _parameterModes) {
                computer.markComplete();
                return index + 1;
            }
        };

        private final int opCode;
        private final int instructionLength;

        Instruction(int opCode, int instructionLength) {
            this.opCode = opCode;
            this.instructionLength = instructionLength;
        }

        public abstract int performOperation(IntcodeComputer computer, int index, int[] parameterModes);

        public int perform(IntcodeComputer computer, int index, int[] parameterModes) {
            if (computer.checkOperation(index, instructionLength)) {
                return performOperation(computer, index, parameterModes);
            } else {
                computer.markCompleteWithError();
                return index; // don't move, I guess? doesn't actually matter
            }
        }

        private static int getValue(IntcodeComputer computer, int index, int mode) {
            return switch (mode) {
                case 0 -> computer.getValueAtIndexValue(index);
                case 1 -> computer.getValue(index);
                default -> throw new IllegalStateException("Invalid mode " + mode);
            };
        }

        public static Optional<Instruction> fromOpCode(int opCode) {
            for (Instruction op : Instruction.values()) {
                if (op.opCode == opCode) {
                    return Optional.of(op);
                }
            }
            System.err.println("No operation with code " + opCode);
            return Optional.empty();
        }
    }

    private record OpCode(int code, int[] parameterModes) {
        public static OpCode fromValue(int value) {
                if (value < 0) {
                    throw new IllegalArgumentException("Invalid opcode " + value);
                } else {
                    int opCode = value % 100;
                    int[] parameterModes = getParameterModes(value / 100);
                    return new OpCode(opCode, parameterModes);
                }
            }

        private static int[] getParameterModes(int value) {
            // purposefully go right to left when parsing
            List<Integer> parameters = new ArrayList<>();
            int remainingValue = value;
            while (remainingValue > 0) {
                int mode = remainingValue % 10;
                if (mode > 1) {
                    throw new IllegalArgumentException("Invalid mode found " + mode + " from value " + value);
                }
                parameters.add(mode);
                remainingValue /= 10;
            }

            // add a few trailing 0s for good measure
            parameters.add(0);
            parameters.add(0);
            parameters.add(0);
            parameters.add(0);
            parameters.add(0);

            // convert to int[]
            return parameters.stream().mapToInt(Integer::intValue).toArray();
        }
    }
}
