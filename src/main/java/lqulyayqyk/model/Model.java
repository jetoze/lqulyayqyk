package lqulyayqyk.model;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;

import lqulyayqyk.util.Util;

public final class Model {
    private final String encryptedText;
    private String clearText;
    private final FrequencyCount frequencyCount;
    private final BiMap<Character, Character> substitutions = HashBiMap.create();
    
    public Model(String encryptedText) {
        for (char c : encryptedText.toCharArray()) {
            checkArgument(isAllowed(c), "Not a supported character: " + c);
        }
        this.encryptedText = encryptedText.toUpperCase();
        this.frequencyCount = new FrequencyCount(this.encryptedText);
    }

    private static boolean isAllowed(char c) {
        return c == ' ' || isPunctuation(c) || Util.isLowerCaseLetter(c) || Util.isUpperCaseLetter(c);
    }
    
    private static boolean isPunctuation(char c) {
        return c == '.' || c == '?' || c == '!' || c == ',' || c == '-';
    }
    
    public String getEncryptedText() {
        return encryptedText;
    }
    
    public FrequencyCount getFrequencyCount() {
        return frequencyCount;
    }

    public String getClearText() {
        return clearText;
    }
    
    public ImmutableSet<Character> getRemainingCandidates() {
        Set<Character> chars = new HashSet<>(Util.ALL_UPPER_CASE_LETTERS);
        chars.removeAll(this.substitutions.values());
        return ImmutableSet.copyOf(chars);
    }

    public void substitute(char from, char to) {
        checkArgument(Util.isLetter(from), "Cannot substitute " + from);
        checkArgument(Util.isLetter(to), "Cannot substitute with " + to);
        char fromUpper = Character.toUpperCase(from);
        char toUpper = Character.toUpperCase(to);
        checkArgument(!this.substitutions.containsValue(toUpper), toUpper + " is already used");
        this.substitutions.put(fromUpper, toUpper);
        generateClearText();
    }
    
    public void remove(char from) {
        checkArgument(Util.isLetter(from), "Cannot substitute " + from);
        if (substitutions.remove(Character.toUpperCase(from)) != null) {
            generateClearText();
        }
    }
    
    private void generateClearText() {
        if (substitutions.isEmpty()) {
            clearText = encryptedText;
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : encryptedText.toCharArray()) {
            char t = substitutions.containsKey(c)
                    ? substitutions.get(c)
                    : c;
            sb.append(t);
        }
        clearText = sb.toString();
    }
}
