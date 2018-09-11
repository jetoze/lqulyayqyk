package lqulyayqyk.util;

import com.google.common.collect.ImmutableSet;

public final class Util {
    public static final ImmutableSet<Character> ALL_UPPER_CASE_LETTERS = allUpperCaseLetters();
    
    private static final ImmutableSet<Character> allUpperCaseLetters() {
        ImmutableSet.Builder<Character> builder = ImmutableSet.builder();
        for (char ch = 'A'; ch <= 'Z'; ++ch) {
            builder.add(ch);
        }
        return builder.build();
    }
    
    public static boolean isLetter(char c) {
        return isUpperCaseLetter(c) || isLowerCaseLetter(c);
    }
    
    public static boolean isUpperCaseLetter(Character c) {
        char ch = c.charValue();
        return ch >= 'A' && ch <= 'Z';
    }
    
    public static boolean isLowerCaseLetter(Character c) {
        char ch = c.charValue();
        return ch >= 'a' && ch <= 'z';
    }
    
    private Util() { /**/}

}
