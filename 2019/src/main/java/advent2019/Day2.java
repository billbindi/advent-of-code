package advent2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {
    private static final String INPUT_FILENAME = "2019/day2_input.txt";
    private static final Path INPUT_PATH = Path.of(INPUT_FILENAME);

    public static void main(String[] args) throws IOException {
        System.out.println(part1(Files.lines(INPUT_PATH)));
        System.out.println(part2(Files.lines(INPUT_PATH)));
    }

    private static int part1(Stream<String> lines) {
        IntcodeProgram program = fromLine(lines.findFirst().orElseThrow());
        program.setValue(1, 12);
        program.setValue(2, 2);
        program.run();
        return program.getValue(0);
    }

    private static int part2(Stream<String> lines) {
        return 0;
    }

    private static IntcodeProgram fromLine(String line) {
        List<Integer> program = Arrays.stream(line.split("\\s*,\\s*")).map(Integer::parseInt).collect(Collectors.toList());
        return new IntcodeProgram(program);
    }

    public static class IntcodeProgram {
        // opcodes
        private static final int ADDITION = 1;
        private static final int MULTIPLICATION = 2;
        private static final int HALT = 99;

        private final List<Integer> program;
        private boolean isComplete = false;
        private boolean isError = false;

        public IntcodeProgram(List<Integer> program) {
            this.program = program;
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
}
