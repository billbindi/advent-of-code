package advent2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {
    private static final int OUTPUT_OFFSET = 1000;

    private static final String VALUE_REGEX = "value (?<value>\\d+) goes to bot (?<bot>\\d+)";
    private static final Pattern VALUE_PATTERN = Pattern.compile(VALUE_REGEX);

    private static final String INSTRUCTION_REGEX = "bot (?<bot>\\d+) gives low to (?<low>\\w+) (?<lowValue>\\d+) and high to (?<high>\\w+) (?<highValue>\\d+)";
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile(INSTRUCTION_REGEX);

    private static final String FILENAME = "2016/day10_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        solve(content);
    }

    private static void solve(List<String> content) {
        Bot[] bots = initBots();
        // instructions first
        for (String instruction : content) {
            if (instruction.startsWith("bot")) {
                Matcher m = INSTRUCTION_PATTERN.matcher(instruction);
                if (m.find()) {
                    int botIndex = Integer.parseInt(m.group("bot"));
                    int lowValue = Integer.parseInt(m.group("lowValue"));
                    int highValue = Integer.parseInt(m.group("highValue"));
                    String lowString = m.group("low");
                    String highString = m.group("high");
                    int low = lowString.equals("bot") ? lowValue : lowValue + OUTPUT_OFFSET;
                    int high = highString.equals("bot") ? highValue : highValue + OUTPUT_OFFSET;

                    Bot bot = bots[botIndex];
                    bot.setHigh(high);
                    bot.setLow(low);
                } else {
                    throw new IllegalStateException("Could not parse instruction line: " + instruction);
                }
            }
        }

        // now look for values to add
        for (String instruction : content) {
            if (instruction.startsWith("value")) {
                // give value to bot
                Matcher m = VALUE_PATTERN.matcher(instruction);
                if (m.find()) {
                    int botIndex = Integer.parseInt(m.group("bot"));
                    int value = Integer.parseInt(m.group("value"));
                    Bot bot = bots[botIndex];
                    bot.addChip(value);
                    cascade(bots, botIndex);
                } else {
                    throw new IllegalStateException("Could not parse value line: " + instruction);
                }
            }
        }

        System.out.println("\nPRINTING OUTPUTS 0, 1, 2:");
        printOutputs012(bots);
    }

    // assumption: botIndex is a real bot
    private static void cascade(Bot[] bots, int  botIndex) {
        Bot bot = bots[botIndex];
        if (bot.isFull()) {
            int low = bot.getLow();
            int high = bot.getHigh();
            int lowValue = bot.getLowValue();
            int highValue = bot.getHighValue();

            System.out.printf("Bot %d comparing chip values %d and %d.%n", botIndex, highValue, lowValue);

            bots[low].addChip(lowValue);
            if (low < OUTPUT_OFFSET) {
                cascade(bots, low);
            }

            bots[high].addChip(highValue);
            if (high < OUTPUT_OFFSET) {
                cascade(bots, high);
            }
        }
    }

    private static void printOutputs012(Bot[] bots) {
        System.out.println(bots[OUTPUT_OFFSET].getChips());
        System.out.println(bots[OUTPUT_OFFSET + 1].getChips());
        System.out.println(bots[OUTPUT_OFFSET + 2].getChips());
    }

    private static Bot[] initBots() {
        Bot[] bots = new Bot[OUTPUT_OFFSET + OUTPUT_OFFSET];
        for (int i = 0; i < bots.length; i++) {
            bots[i] = new Bot();
        }
        return bots;
    }

    private static class Bot {
        int low = -1;
        int high = -1;
        List<Integer> chips = new ArrayList<>();

        public int getLow() {
            return low;
        }

        public void setLow(int low) {
            this.low = low;
        }

        public int getHigh() {
            return high;
        }

        public void setHigh(int high) {
            this.high = high;
        }

        public boolean isFull() {
            return chips.size() >= 2;
        }

        public List<Integer> getChips() {
            return chips;
        }

        public void addChip(int value) {
            chips.add(value);
        }

        public int getLowValue() {
            return chips.stream().min(Comparator.naturalOrder()).orElseThrow();
        }

        public int getHighValue() {
            return chips.stream().max(Comparator.naturalOrder()).orElseThrow();
        }
    }
}
