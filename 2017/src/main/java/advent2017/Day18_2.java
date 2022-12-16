package advent2017;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day18_2 {

    private static final String FILENAME = "2017/day18_input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILENAME));
        long count = perform(lines);
        System.out.println(count);
    }

    private static long perform(List<String> lines) {
        Map<String, Long> registers0 = new HashMap<>();
        Map<String, Long> registers1 = new HashMap<>();
        Queue<Long> messagesSent0 = new ArrayDeque<>();
        Queue<Long> messagesSent1 = new ArrayDeque<>();

        // each program has prgram id in register "p" at start
        registers0.put("p", 0L);
        registers1.put("p", 1L);

        int count0 = 0;
        int count1 = 0;
        int index0 = 0;
        int index1 = 0;
        boolean waiting0 = false;
        boolean waiting1 = false;
        while (!waiting0 || !waiting1) {
            if (index0 >= 0 && index0 < lines.size()) {
                // process program 0 first
                String line0 = lines.get(index0);
                String[] parts0 = line0.split(" ");
                String instruction0 = parts0[0];
                switch (instruction0) {
                    case "snd":
                        messagesSent0.add(value(registers0, parts0[1]));
                        count0++;
                        index0++;
                        break;
                    case "set":
                        registers0.put(parts0[1], value(registers0, parts0[2]));
                        index0++;
                        break;
                    case "add":
                        long initialAdd = value(registers0, parts0[1]);
                        long addend = value(registers0, parts0[2]);
                        registers0.put(parts0[1], initialAdd + addend);
                        index0++;
                        break;
                    case "mul":
                        long initialMul = value(registers0, parts0[1]);
                        long factor = value(registers0, parts0[2]);
                        registers0.put(parts0[1], initialMul * factor);
                        index0++;
                        break;
                    case "mod":
                        long initialMod = value(registers0, parts0[1]);
                        long modulus = value(registers0, parts0[2]);
                        registers0.put(parts0[1], initialMod % modulus);
                        index0++;
                        break;
                    case "rcv":
                        if (messagesSent1.isEmpty()) {
                            waiting0 = true;
                        } else {
                            registers0.put(parts0[1], messagesSent1.remove());
                            waiting0 = false;
                            index0++;
                        }
                        break;
                    case "jgz":
                        if (value(registers0, parts0[1]) > 0) {
                            index0 += value(registers0, parts0[2]);
                        } else {
                            index0++;
                        }
                        break;
                    default:
                        throw new IllegalStateException("COULD NOT PARSE INSTRUCTION: " + instruction0);
                }
            } else {
                waiting0 = true;
            }

            if (index1 >= 0 && index1 < lines.size()) {
                // process program 0 first
                String line1 = lines.get(index1);
                String[] parts1 = line1.split(" ");
                String instruction1 = parts1[0];
                switch (instruction1) {
                    case "snd":
                        messagesSent1.add(value(registers1, parts1[1]));
                        count1++;
                        index1++;
                        break;
                    case "set":
                        registers1.put(parts1[1], value(registers1, parts1[2]));
                        index1++;
                        break;
                    case "add":
                        long initialAdd = value(registers1, parts1[1]);
                        long addend = value(registers1, parts1[2]);
                        registers1.put(parts1[1], initialAdd + addend);
                        index1++;
                        break;
                    case "mul":
                        long initialMul = value(registers1, parts1[1]);
                        long factor = value(registers1, parts1[2]);
                        registers1.put(parts1[1], initialMul * factor);
                        index1++;
                        break;
                    case "mod":
                        long initialMod = value(registers1, parts1[1]);
                        long modulus = value(registers1, parts1[2]);
                        registers1.put(parts1[1], initialMod % modulus);
                        index1++;
                        break;
                    case "rcv":
                        if (messagesSent0.isEmpty()) {
                            waiting1 = true;
                        } else {
                            registers1.put(parts1[1], messagesSent0.remove());
                            waiting1 = false;
                            index1++;
                        }
                        break;
                    case "jgz":
                        if (value(registers1, parts1[1]) > 0) {
                            index1 += value(registers1, parts1[2]);
                        } else {
                            index1++;
                        }
                        break;
                    default:
                        throw new IllegalStateException("COULD NOT PARSE INSTRUCTION: " + instruction1);
                }
            } else {
                waiting1 = true;
            }
        }
        return count1;
    }

    private static long value(Map<String, Long> registers, String input) {
        if (input.matches("-?\\d+")) {
            return Long.parseLong(input);
        } else {
            if (!registers.containsKey(input)) {
                registers.put(input, 0L);
            }
            return registers.get(input);
        }
    }
}
