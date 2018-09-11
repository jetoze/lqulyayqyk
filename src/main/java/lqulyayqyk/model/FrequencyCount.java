package lqulyayqyk.model;

import static com.google.common.base.Preconditions.checkArgument;
import static tzeth.preconds.MorePreconditions.checkNotNegative;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import lqulyayqyk.util.Util;

public final class FrequencyCount {
    private final ImmutableMultiset<Character> counts;
    
    public FrequencyCount(String text) {
        Multiset<Character> counts = HashMultiset.create();
        for (char c : text.toCharArray()) {
            if (Util.isLetter(c)) {
                counts.add(Character.toUpperCase(c));
            }
        }
        this.counts = ImmutableMultiset.copyOf(Multisets.copyHighestCountFirst(counts));
    }

    public List<Entry> getEntriesInOrder() {
        List<Entry> entries = new ArrayList<>();
        for (Multiset.Entry<Character> e : counts.entrySet()) {
            entries.add(new Entry(e.getElement(), e.getCount()));
        }
        for (char c = 'A'; c <= 'Z'; ++c) {
            if (!counts.contains(c)) {
                entries.add(new Entry(c, 0));
            }
        }
        return entries;
    }
    
    public int getCount(char letter) {
        checkArgument(Util.isLetter(letter), "Not a letter: %s", letter);
        return counts.count(Character.toUpperCase(letter));
    }
    
    public static final class Entry {
        private final char letter;
        private final int count;
        
        public Entry(char letter, int count) {
            checkArgument(Util.isLetter(letter), "Not a letter: %s", letter);
            this.letter = letter;
            this.count = checkNotNegative(count);
        }
        
        public char getLetter() {
            return letter;
        }
        
        public int getCount() {
            return count;
        }
        
        @Override
        public String toString() {
            return letter + " - " + count;
        }
    }
    
}
