package com.mrakin.structures.two_pointer_array;

import java.util.HashSet;
import java.util.Set;

public class LongestUniqueCharSubstr {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int left = 0;
        int best = 0;

        for (int right = 0; right < s.length(); right++) {
            while (set.contains(s.charAt(right))) {
                set.remove(s.charAt(left++));
            }
            set.add(s.charAt(right));
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
