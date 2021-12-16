package advent2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Day12_2 {

    private static final String FILENAME = "day12_input.txt";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        int total = Files.readAllLines(Paths.get(FILENAME)).stream()
                .mapToInt(Day12_2::count)
                .sum();
        System.out.println(total);
    }

    private static int count(String s) {
        try {
            JsonNode node = MAPPER.readValue(s, JsonNode.class);
            return count(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static int count(JsonNode node) {
        int sum = 0;
        switch (node.getNodeType()) {
            case ARRAY:
                for (JsonNode child : node) {
                    sum += count(child);
                }
                return sum;
            case OBJECT:
                if (!isRed(node)) {
                    for (JsonNode child : node) {
                        sum += count(child);
                    }
                    return sum;
                } else {
                    return 0;
                }
            case NUMBER:
                return node.asInt();
            default:
                return 0;
        }
    }

    private static boolean isRed(JsonNode node) {
        for (JsonNode child : node) {
            if (child.isTextual() && child.toString().equalsIgnoreCase("\"red\"")) {
                return true;
            }
        }
        return false;
    }
}
