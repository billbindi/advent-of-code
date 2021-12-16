import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4_1 {
    private static final String ROOM_REGEX = "(?<name>[-a-z]+)-(?<id>\\d+)\\[(?<checksum>[a-z]{5})]";
    private static final Pattern ROOM_PATTERN = Pattern.compile(ROOM_REGEX);

    private static final String FILENAME = "2016/day4_input.txt";

    public static void main(String[] args) throws IOException {
        Path input = Path.of(FILENAME);
        List<String> content = Files.readAllLines(input);

        System.out.println(solve(content));
    }

    private static int solve(List<String> content) {
        int total = 0;
        for (String line : content) {
            Room room = parseRoom(line);
            if (room.isReal()) {
                total += room.getId();
                System.out.println(room.decrypt() + "   " + room.getId());
            }
        }
        return total;
    }

    private static Room parseRoom(String line) {
        Matcher m = ROOM_PATTERN.matcher(line);
        if (m.find()) {
            String name = m.group("name");
            int id = Integer.parseInt(m.group("id"));
            String checksum = m.group("checksum");
            return new Room(name, id, checksum);
        } else {
            throw new IllegalArgumentException("Line not parsable: " + line);
        }
    }

    private static class Room {
        final String encryptedName;
        final int id;
        final String checksum;

        private Room(String encryptedName, int id, String checksum) {
            this.encryptedName = encryptedName;
            this.id = id;
            this.checksum = checksum;
        }

        boolean isReal() {
            char[] chars = encryptedName.toCharArray();
            Map<Character, Integer> freq = new HashMap<>();
            for (char c : chars) {
                if (!freq.containsKey(c)) {
                    freq.put(c, 0);
                }
                freq.put(c, freq.get(c) + 1);
            }

            Comparator<Map.Entry<Character, Integer>> val = Map.Entry.comparingByValue(Comparator.reverseOrder());
            Comparator<Map.Entry<Character, Integer>> key = Map.Entry.comparingByKey();
            Comparator<Map.Entry<Character, Integer>> valKey = val.thenComparing(key);
            Character[] expectedChecksum = freq.entrySet().stream()
                    .filter(e -> e.getKey() != '-')
                    .sorted(valKey)
                    .limit(5)
                    .map(Map.Entry::getKey)
                    .toArray(Character[]::new);

            Character[] actualChecksum = checksum.chars()
                    .mapToObj(c -> (char)c)
                    .toArray(Character[]::new);
            return Arrays.equals(expectedChecksum, actualChecksum);
        }

        public int getId() {
            return id;
        }

        public String decrypt() {
            char[] orig = encryptedName.toCharArray();
            char[] decrypted = new char[orig.length];
            int shift = id % 26;
            for (int i = 0; i < orig.length; i++) {
                if (orig[i] == '-') {
                    decrypted[i] = ' ';
                } else {
                    decrypted[i] = (char) ('a' + (((orig[i] + shift) % 'a') % 26));
                }
            }
            return new String(decrypted);
        }
    }
}
