package lqulyayqyk.cipher;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

import lqulyayqyk.util.Util;

public class SubstitutionCipher {
    private final ImmutableMap<Character, Character> translationTable;
    
    public SubstitutionCipher(Map<Character, Character> translationTable) {
        checkArgument(translationTable.size() == 26);
        checkArgument(translationTable.keySet().stream().allMatch(Util::isUpperCaseLetter));
        Set<Character> uniqueValues = new HashSet<>(translationTable.values());
        checkArgument(uniqueValues.size() == 26);
        checkArgument(uniqueValues.stream().allMatch(Util::isUpperCaseLetter));
        this.translationTable = ImmutableMap.copyOf(translationTable);
    }

    public static SubstitutionCipher generate() {
        return generate(new Random());
    }
    
    public static SubstitutionCipher generate(Random rnd) {
        List<Character> alphabet = new ArrayList<>();
        for (Character c = 'A'; c <= 'Z'; ++c) {
            alphabet.add(c);
        }
        Collections.shuffle(alphabet, rnd);
        Map<Character, Character> table = new HashMap<>();
        Iterator<Character> it = alphabet.iterator();
        for (Character c = 'A'; c <= 'Z'; ++c) {
            table.put(c, it.next());
        }
        return new SubstitutionCipher(table);
    }
    
    public String encrypt(String input) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            output.append(substitute(c));
        }
        return output.toString();
    }
    
    private char substitute(char c) {
        if (Util.isUpperCaseLetter(c)) {
            return translationTable.get(c);
        } else if (Util.isLowerCaseLetter(c)) {
            return translationTable.get(Character.toUpperCase(c));
        } else {
            return c;
        }
    }
    
    public SubstitutionCipher solution() {
        Map<Character, Character> solution = new HashMap<>();
        translationTable.entrySet().forEach(e -> solution.put(e.getValue(), e.getKey()));
        return new SubstitutionCipher(solution);
    }
    
    @Override
    public String toString() {
        return translationTable.toString();
    }
    
    public static void main(String[] args) {
        for (int n = 0; n <= 16; ++n) {
            SubstitutionCipher cipher = SubstitutionCipher.generate();
            System.out.println("          " + cipher);
            String encrypted = cipher.encrypt("substitute");
            System.out.println(encrypted.toLowerCase());
        }
    }
    
}
