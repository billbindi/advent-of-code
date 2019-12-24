import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Day16 {

    private static final String FILENAME = "day16_input.txt";

    private static final AuntSue REAL_SUE = new AuntSue(
            -1,
            OptionalInt.of(3),
            OptionalInt.of(7),
            OptionalInt.of(2),
            OptionalInt.of(3),
            OptionalInt.of(0),
            OptionalInt.of(0),
            OptionalInt.of(5),
            OptionalInt.of(3),
            OptionalInt.of(2),
            OptionalInt.of(1));

    public static void main(String[] args) throws IOException {
        List<AuntSue> options = Files.readAllLines(Paths.get(FILENAME)).stream()
                .map(Day16::parse)
                .collect(Collectors.toList());
        for (AuntSue option : options) {
            if (isRealAuntSue(option)) {
                System.out.println(option);
            }
        }
    }

    private static boolean isRealAuntSue(AuntSue option) {
        if (option.children.isPresent() && option.children.getAsInt() != REAL_SUE.children.getAsInt()) {
            return false;
        }
        if (option.cats.isPresent() && option.cats.getAsInt() != REAL_SUE.cats.getAsInt()) {
            return false;
        }
        if (option.samoyeds.isPresent() && option.samoyeds.getAsInt() != REAL_SUE.samoyeds.getAsInt()) {
            return false;
        }
        if (option.pomeranians.isPresent() && option.pomeranians.getAsInt() != REAL_SUE.pomeranians.getAsInt()) {
            return false;
        }
        if (option.akitas.isPresent() && option.akitas.getAsInt() != REAL_SUE.akitas.getAsInt()) {
            return false;
        }
        if (option.vizslas.isPresent() && option.vizslas.getAsInt() != REAL_SUE.vizslas.getAsInt()) {
            return false;
        }
        if (option.goldfish.isPresent() && option.goldfish.getAsInt() != REAL_SUE.goldfish.getAsInt()) {
            return false;
        }
        if (option.trees.isPresent() && option.trees.getAsInt() != REAL_SUE.trees.getAsInt()) {
            return false;
        }
        if (option.cars.isPresent() && option.cars.getAsInt() != REAL_SUE.cars.getAsInt()) {
            return false;
        }
        if (option.perfumes.isPresent() && option.perfumes.getAsInt() != REAL_SUE.perfumes.getAsInt()) {
            return false;
        }
        return true;
    }

    private static AuntSue parse(String line) {
        int colon = line.indexOf(':');
        int index = Integer.parseInt(line.substring(0, colon).split(" ")[1]);

        String propertyString = line.substring(colon + 1);
        OptionalInt children = OptionalInt.empty();
        OptionalInt cats = OptionalInt.empty();
        OptionalInt samoyeds = OptionalInt.empty();
        OptionalInt pomeranians = OptionalInt.empty();
        OptionalInt akitas = OptionalInt.empty();
        OptionalInt vizslas = OptionalInt.empty();
        OptionalInt goldfish = OptionalInt.empty();
        OptionalInt trees = OptionalInt.empty();
        OptionalInt cars = OptionalInt.empty();
        OptionalInt perfumes = OptionalInt.empty();
        for (String property : propertyString.split(",")) {
            String[] parts = property.trim().split(" ");
            int val = Integer.parseInt(parts[1]);
            String type = parts[0].substring(0, parts[0].length() - 1);
            switch (Property.fromString(type)) {
                case CHILDREN:
                    children = OptionalInt.of(val);
                    break;
                case CATS:
                    cats = OptionalInt.of(val);
                    break;
                case SAMOYEDS:
                    samoyeds = OptionalInt.of(val);
                    break;
                case POMERANIANS:
                    pomeranians = OptionalInt.of(val);
                    break;
                case AKITAS:
                    akitas = OptionalInt.of(val);
                    break;
                case VIZSLAS:
                    vizslas = OptionalInt.of(val);
                    break;
                case GOLDFISH:
                    goldfish = OptionalInt.of(val);
                    break;
                case TREES:
                    trees = OptionalInt.of(val);
                    break;
                case CARS:
                    cars = OptionalInt.of(val);
                    break;
                case PERFUMES:
                    perfumes = OptionalInt.of(val);
                    break;
            }
        }
        return new AuntSue(index, children, cats, samoyeds, pomeranians, akitas, vizslas, goldfish, trees, cars, perfumes);
    }

    private static class AuntSue {
        final int index;
        final OptionalInt children;
        final OptionalInt cats;
        final OptionalInt samoyeds;
        final OptionalInt pomeranians;
        final OptionalInt akitas;
        final OptionalInt vizslas;
        final OptionalInt goldfish;
        final OptionalInt trees;
        final OptionalInt cars;
        final OptionalInt perfumes;

        private AuntSue(int index, OptionalInt children, OptionalInt cats, OptionalInt samoyeds, OptionalInt pomeranians, OptionalInt akitas, OptionalInt vizslas, OptionalInt goldfish, OptionalInt trees, OptionalInt cars, OptionalInt perfumes) {
            this.index = index;
            this.children = children;
            this.cats = cats;
            this.samoyeds = samoyeds;
            this.pomeranians = pomeranians;
            this.akitas = akitas;
            this.vizslas = vizslas;
            this.goldfish = goldfish;
            this.trees = trees;
            this.cars = cars;
            this.perfumes = perfumes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AuntSue auntSue = (AuntSue) o;
            return index == auntSue.index &&
                    children.equals(auntSue.children) &&
                    cats.equals(auntSue.cats) &&
                    samoyeds.equals(auntSue.samoyeds) &&
                    pomeranians.equals(auntSue.pomeranians) &&
                    akitas.equals(auntSue.akitas) &&
                    vizslas.equals(auntSue.vizslas) &&
                    goldfish.equals(auntSue.goldfish) &&
                    trees.equals(auntSue.trees) &&
                    cars.equals(auntSue.cars) &&
                    perfumes.equals(auntSue.perfumes);
        }

        @Override
        public int hashCode() {
            return Objects.hash(index, children, cats, samoyeds, pomeranians, akitas, vizslas, goldfish, trees, cars, perfumes);
        }

        @Override
        public String toString() {
            return "AuntSue{" +
                    "index=" + index +
                    ", children=" + children +
                    ", cats=" + cats +
                    ", samoyeds=" + samoyeds +
                    ", pomeranians=" + pomeranians +
                    ", akitas=" + akitas +
                    ", vizslas=" + vizslas +
                    ", goldfish=" + goldfish +
                    ", trees=" + trees +
                    ", cars=" + cars +
                    ", perfumes=" + perfumes +
                    '}';
        }
    }

    private static enum Property {
        CHILDREN, CATS, SAMOYEDS, POMERANIANS, AKITAS, VIZSLAS, GOLDFISH, TREES, CARS, PERFUMES;

        static Property fromString(String property) {
            for (Property option : values()) {
                if (option.name().equalsIgnoreCase(property)) {
                    return option;
                }
            }
            return null;
        }
    }
}
