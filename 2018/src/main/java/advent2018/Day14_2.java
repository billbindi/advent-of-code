package advent2018;

import util.ArrayUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14_2 {

    private static final int[] ENDING = new int[] {6, 5, 2, 6, 0, 1};

    public static void main(String[] args) throws IOException {
        System.out.println(solve());
    }

    private static int solve() {
        List<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);
        int elf1 = 0;
        int elf2 = 1;

        int recipeCount = 2;
        while (true) {
            int recipe1 = recipes.get(elf1);
            int recipe2 = recipes.get(elf2);
            int newRecipe = recipe1 + recipe2;
            boolean doubleRecipe = false;
            if (newRecipe >= 10) {
                int newRecipe1 = newRecipe / 10;
                int newRecipe2 = newRecipe % 10;
                recipes.add(newRecipe1);
                recipes.add(newRecipe2);
                doubleRecipe = true;
            } else {
                recipes.add(newRecipe);
            }
            if (newRecipe == 1 || newRecipe >= 10) {
                if (checkEnding(recipes)) {
                    return recipeCount - 5;
                }
            }
            elf1 = (elf1 + recipe1 + 1) % recipes.size();
            elf2 = (elf2 + recipe2 + 1) % recipes.size();
            recipeCount++;
            if (doubleRecipe) {
                recipeCount++;
            }
        }
    }

    private static boolean checkEnding(List<Integer> recipes) {
        if (recipes.size() <= 7) {
            return false;
        } else {
            int[] lastSix = ArrayUtils.fromList(recipes.subList(recipes.size() - 6, recipes.size()));
            int[] penultimateSix = ArrayUtils.fromList(recipes.subList(recipes.size() - 7, recipes.size() - 1));
            return Arrays.equals(lastSix, ENDING) || Arrays.equals(penultimateSix, ENDING);
        }
    }
}
