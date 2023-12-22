package advent2018;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day14_1 {

    private static final int PRACTICE_RECIPES = 652601;

    public static void main(String[] args) throws IOException {
        System.out.println(solve());
    }

    private static List<Integer> solve() {
        List<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);

        int elf1 = 0;
        int elf2 = 1;
        while (recipes.size() < PRACTICE_RECIPES + 10) {
            int recipe1 = recipes.get(elf1);
            int recipe2 = recipes.get(elf2);
            int newRecipe = recipe1 + recipe2;
            if (newRecipe >= 10) {
                int newRecipe1 = newRecipe / 10;
                int newRecipe2 = newRecipe % 10;
                recipes.add(newRecipe1);
                recipes.add(newRecipe2);
            } else {
                recipes.add(newRecipe);
            }
            elf1 = (elf1 + recipe1 + 1) % recipes.size();
            elf2 = (elf2 + recipe2 + 1) % recipes.size();
        }
        return recipes.subList(PRACTICE_RECIPES, PRACTICE_RECIPES + 10);
    }
}
