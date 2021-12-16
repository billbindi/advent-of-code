package advent2015;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;

public class Day15 {

    public static void main(String[] args) {
        List<Ingrediant> ingrediants = ImmutableList.of(
                new Ingrediant("Sprinkles", 5, -1, 0, 0, 5),
                new Ingrediant("Peanut Butter", -1, 3, 0, 0, 1),
                new Ingrediant("Frosting", 0, -1, 4, 0, 6),
                new Ingrediant("Sugar", -1, 0, 0, 2, 8)
        );

        System.out.println(optimize(ingrediants));
    }

    private static int optimize(List<Ingrediant> ingrediants) {
        int max = 0;
        for (int first = 0; first <= 100; first++) {
            for (int second = 0; first + second <= 100; second++) {
                for (int third = 0; first + second + third <= 100; third++) {
                    int fourth = 100 - (first + second + third);
                    int val = value(ingrediants, first, second, third, fourth);
                    if (val > max) {
                        max = val;
                    }
                }
            }
        }
        return max;
    }

    private static int value(List<Ingrediant> ingrediants, int first, int second, int third, int fourth) {
        int capacity = (ingrediants.get(0).getCapacity() * first) +
                (ingrediants.get(1).getCapacity() * second) +
                (ingrediants.get(2).getCapacity() * third) +
                (ingrediants.get(3).getCapacity() * fourth);
        if (capacity < 0) {
            return 0;
        }
        int durability = (ingrediants.get(0).getDurability() * first) +
                (ingrediants.get(1).getDurability() * second) +
                (ingrediants.get(2).getDurability() * third) +
                (ingrediants.get(3).getDurability() * fourth);
        if (durability < 0) {
            return 0;
        }
        int flavor = (ingrediants.get(0).getFlavor() * first) +
                (ingrediants.get(1).getFlavor() * second) +
                (ingrediants.get(2).getFlavor() * third) +
                (ingrediants.get(3).getFlavor() * fourth);
        if (flavor < 0) {
            return 0;
        }
        int texture = (ingrediants.get(0).getTexture() * first) +
                (ingrediants.get(1).getTexture() * second) +
                (ingrediants.get(2).getTexture() * third) +
                (ingrediants.get(3).getTexture() * fourth);
        if (texture < 0) {
            return 0;
        }
        return capacity * durability * flavor * texture;
    }

    private static class Ingrediant {
        final String name;
        final int capacity;
        final int durability;
        final int flavor;
        final int texture;
        final int calories;

        private Ingrediant(String name, int capacity, int durability, int flavor, int texture, int calories) {
            this.name = name;
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }

        public String getName() {
            return name;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getDurability() {
            return durability;
        }

        public int getFlavor() {
            return flavor;
        }

        public int getTexture() {
            return texture;
        }

        public int getCalories() {
            return calories;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Ingrediant that = (Ingrediant) o;
            return capacity == that.capacity &&
                    durability == that.durability &&
                    flavor == that.flavor &&
                    texture == that.texture &&
                    calories == that.calories &&
                    name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, capacity, durability, flavor, texture, calories);
        }

        @Override
        public String toString() {
            return "Ingrediant{" +
                    "name='" + name + '\'' +
                    ", capacity=" + capacity +
                    ", durability=" + durability +
                    ", flavor=" + flavor +
                    ", texture=" + texture +
                    ", calories=" + calories +
                    '}';
        }
    }
}
