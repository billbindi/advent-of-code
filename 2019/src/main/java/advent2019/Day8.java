package advent2019;

import com.google.common.base.Preconditions;
import util.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class Day8 {
    private static final String INPUT_FILENAME = "2019/day8_input.txt";
    private static final Path INPUT_PATH = Path.of(INPUT_FILENAME);

    private static final int WIDTH = 25;
    private static final int HEIGHT = 6;
    private static final int LAYER_SIZE = WIDTH * HEIGHT;

    public static void main(String[] args) throws IOException {
        System.out.println(part1(Files.lines(INPUT_PATH)));
        System.out.println(part2(Files.lines(INPUT_PATH)));
    }

    private static int part1(Stream<String> lines) {
        List<List<Integer>> layers = parseImage(lines.findFirst().orElseThrow());
        List<Integer> fewestZeroes = findFewestZeroes(layers);
        return countOnesByTwos(fewestZeroes);
    }

    private static int part2(Stream<String> lines) {
        List<List<Integer>> layers = parseImage(lines.findFirst().orElseThrow());
        int[][] image = decodeImage(layers);
        ArrayUtils.print2DArray(image);
        return 0;
    }

    private static List<List<Integer>> parseImage(String imageDigits) {
        Preconditions.checkArgument(imageDigits.length() % LAYER_SIZE == 0, "Invalid number of digits: %s", imageDigits.length());
        List<List<Integer>> layers = new ArrayList<>();
        int numLayers = imageDigits.length() / LAYER_SIZE;
        for (int layerIndex = 0; layerIndex < numLayers; layerIndex++) {
            String layerString = imageDigits.substring(layerIndex * LAYER_SIZE, (layerIndex + 1) * LAYER_SIZE);
            layers.add(layerString.chars()
                    .map(Character::getNumericValue)
                    .boxed()
                    .toList());
        }
        return layers;
    }

    private static List<Integer> findFewestZeroes(List<List<Integer>> layers) {
        List<Integer> fewestZeroes = null;
        int minZeroes = Integer.MAX_VALUE;
        for (List<Integer> layer : layers) {
            int numZeroes = countValue(layer, 0);
            if (numZeroes < minZeroes) {
                minZeroes = numZeroes;
                fewestZeroes = layer;
            }
        }
        return fewestZeroes;
    }

    private static int countOnesByTwos(List<Integer> layer) {
        return countValue(layer, 1) * countValue(layer, 2);
    }

    private static int countValue(List<Integer> list, int value){
        return (int) list.stream().filter(val -> val == value).count();
    }

    private static int[][] decodeImage(List<List<Integer>> layers) {
        int[][] imageArray = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                int pixel = findFirstPixel(layers, i, j);
                imageArray[i][j] = pixel;
            }
        }
        return imageArray;
    }

    private static int findFirstPixel(List<List<Integer>> layers, int height, int width) {
        int index = WIDTH * height + width;
        for (List<Integer> layer : layers) {
            if (layer.get(index) != 2) {
                return layer.get(index);
            }
        }
        throw new IllegalStateException("No non-transparent pixel at height " + height + ", width " + width);
    }
}
