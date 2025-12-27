package advent2019;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class IntcodeComputer {
    private static final Consumer<Long> PRINTLN_OUTPUT_CONSUMER = val -> System.out.println("OUTPUT: " + val);

    private final ImmutableList<Long> initialMemory;
    private final List<Long> memory = new ArrayList<>();

    private boolean isComplete = false;
    private boolean isError = false;
    private boolean isStarted = false;

    private final List<Long> input;
    private int inputPointer = 0;

    private final Consumer<Long> outputConsumer;

    // only build through builder
    private IntcodeComputer(List<Long> initialMemory, List<Long> input, Consumer<Long> outputConsumer) {
        this.initialMemory = ImmutableList.copyOf(initialMemory);
        this.input = input;
        this.outputConsumer = outputConsumer;
        reset();
    }

    public void reset() {
        memory.clear();
        memory.addAll(initialMemory);

        isComplete = false;
        isError = false;
        isStarted = false;

        inputPointer = 0;
    }

    public static IntcodeComputer copyOf(IntcodeComputer other) {
        return new IntcodeComputer(
                new ArrayList<>(other.initialMemory), new ArrayList<>(other.input), other.outputConsumer);
    }

    public void clearInput() {
        input.clear();
    }

    public boolean runWithNounVerb(int noun, int verb) {
        setValue(1, noun);
        setValue(2, verb);
        return run();
    }

    // only works on 'memory' variable to keep initial state constant
    public boolean run() {
        isStarted = true;
        int pointer = 0;
        while (!isComplete && pointer < memory.size()) {
            OpCode opCode = OpCode.fromValue((int) getValue(pointer));
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

    public long getValue(int index) {
        if (index > memory.size()) {
            ((ArrayList<?>) memory).ensureCapacity(index);
            while (memory.size() <= index) {
                memory.add(0L);
            }
        }
        return memory.get(index);
    }

    public long getInput() {
        long value = input.get(inputPointer);
        if (inputPointer < input.size() - 1) {
            inputPointer++;
        }
        return value;
    }

    public long getOutput() {
        return getValue(0);
    }

    public void processOutput(long value) {
        outputConsumer.accept(value);
    }

    public long getValueAtIndexValue(int index) {
        return getValue((int) getValue(index));
    }

    public List<Long> getRawProgram() {
        return memory;
    }

    // accessible for testing
    void setValue(int index, long value) {
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Long> initialMemory;
        private List<Long> input = new ArrayList<>(); // default empty
        private Consumer<Long> outputConsumer = PRINTLN_OUTPUT_CONSUMER; // default println

        public Builder initialMemory(List<Long> initialMemory) {
            this.initialMemory = initialMemory;
            return this;
        }

        public Builder initialMemory(String memoryString) {
            this.initialMemory = Arrays.stream(memoryString.split("\\s*,\\s*")).map(Long::parseLong).toList();
            return this;
        }

        public Builder input(List<Long> input) {
            this.input = input;
            return this;
        }

        public Builder input(long value) {
            this.input.add(value);
            return this;
        }

        public Builder outputConsumer(Consumer<Long> outputConsumer) {
            this.outputConsumer = outputConsumer;
            return this;
        }

        public IntcodeComputer build() {
            Preconditions.checkArgument(!initialMemory.isEmpty(), "initialMemory must not be empty");
            return new IntcodeComputer(initialMemory, input, outputConsumer);
        }
    }

    public enum Instruction {
        ADDITION(1, 4) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                long operand1 = getValue(computer, index + 1, parameterModes[0]);
                long operand2 = getValue(computer, index + 2, parameterModes[1]);
                int operand3 = (int) computer.getValue(index + 3);
                computer.setValue(operand3, operand1 + operand2);
                return index + 4;
            }
        },
        MULTIPLICATION(2, 4) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                long operand1 = getValue(computer, index + 1, parameterModes[0]);
                long operand2 = getValue(computer, index + 2, parameterModes[1]);
                int operand3 = (int) computer.getValue(index + 3);
                computer.setValue(operand3, operand1 * operand2);
                return index + 4;
            }
        },
        INPUT(3, 2) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] _parameterModes) {
                int operand1 = (int) computer.getValue(index + 1);
                long inputValue = computer.getInput();
                computer.setValue(operand1, inputValue);
                return index + 2;
            }
        },
        OUTPUT(4, 2) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                computer.processOutput(getValue(computer, index + 1, parameterModes[0]));
                return index + 2;
            }
        },
        JUMP_IF_TRUE(5, 3) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                long operand1 = getValue(computer, index + 1, parameterModes[0]);
                int operand2 = (int) getValue(computer, index + 2, parameterModes[1]);
                return operand1 == 0 ? index + 3 : operand2;
            }
        },
        JUMP_IF_FALSE(6, 3) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                long operand1 = getValue(computer, index + 1, parameterModes[0]);
                int operand2 = (int) getValue(computer, index + 2, parameterModes[1]);
                return operand1 == 0 ? operand2 : index + 3;
            }
        },
        LESS_THAN(7, 4) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                long operand1 = getValue(computer, index + 1, parameterModes[0]);
                long operand2 = getValue(computer, index + 2, parameterModes[1]);
                int operand3 = (int) computer.getValue(index + 3);
                int result = operand1 < operand2 ? 1 : 0;
                computer.setValue(operand3, result);
                return index + 4;
            }
        },
        EQUALS(8, 4) {
            @Override
            public int performOperation(IntcodeComputer computer, int index, int[] parameterModes) {
                long operand1 = getValue(computer, index + 1, parameterModes[0]);
                long operand2 = getValue(computer, index + 2, parameterModes[1]);
                int operand3 = (int) computer.getValue(index + 3);
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

        private static long getValue(IntcodeComputer computer, int index, int mode) {
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

        private static int[] getParameterModes(long value) {
            // purposefully go right to left when parsing
            List<Long> parameters = new ArrayList<>();
            long remainingValue = value;
            while (remainingValue > 0) {
                long mode = remainingValue % 10;
                if (mode > 1) {
                    throw new IllegalArgumentException("Invalid mode found " + mode + " from value " + value);
                }
                parameters.add(mode);
                remainingValue /= 10;
            }

            // add a few trailing 0s for good measure
            parameters.add(0L);
            parameters.add(0L);
            parameters.add(0L);
            parameters.add(0L);
            parameters.add(0L);

            // convert to int[]
            return parameters.stream().mapToInt(Long::intValue).toArray();
        }
    }
}
