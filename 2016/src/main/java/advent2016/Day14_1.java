package advent2016;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import util.Hashing;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14_1 {
    private static final SetMultimap<Integer, Character> TRIPLES = HashMultimap.create();
    private static final SetMultimap<Integer, Character> QUINTUPLES = HashMultimap.create();

    private static final String TRIPLET_REGEX = "([a-f0-9])\\1{2}";
    private static final Pattern TRIPLET_PATTERN = Pattern.compile(TRIPLET_REGEX);

    private static final String QUINTUPLET_REGEX = "([a-f0-9])\\1{4}";
    private static final Pattern QUINTUPLET_PATTERN = Pattern.compile(QUINTUPLET_REGEX);

    // puzzle input
    private static final String KEY = "zpqevtbw";

    public static void main(String[] args) {
        System.out.println(solve());
    }

    private static int solve() {
        prepopulate();
        List<Integer> indices = new ArrayList<>(64);
        int index = 0;
        while (indices.size() < 64) {
            processIndex(index + 1000);
            if (indexMatches(index)) {
                indices.add(index);
            }
            index++;
        }
        return indices.get(63);
    }

    private static void processIndex(int index) {
        String hash = getHash(index);
        processHash(hash, index);
    }

    private static String getHash(int index) {
        try {
            return Hashing.md5(KEY + index).toLowerCase(Locale.ROOT);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 hash possible", e);
        }
    }

    private static void processHash(String hash, int index) {
        Matcher triplet = TRIPLET_PATTERN.matcher(hash);
        if (triplet.find()) {
            String match = triplet.group();
            TRIPLES.put(index, match.charAt(0));
        }

        Matcher quintuplet = QUINTUPLET_PATTERN.matcher(hash);
        while (quintuplet.find()) {
            String match = quintuplet.group();
            QUINTUPLES.put(index, match.charAt(0));
        }
    }

    private static boolean indexMatches(int index) {
        Set<Character> triples = TRIPLES.get(index);
        for (int i = index + 1; i <= index + 1000; i++) {
            Set<Character> quintuples = QUINTUPLES.get(i);
            if (!Sets.intersection(triples, quintuples).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static void prepopulate() {
        for (int index = 0; index < 1000; index++) {
            processIndex(index);
        }
    }
}
