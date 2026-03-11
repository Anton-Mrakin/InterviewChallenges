package com.mrakin.structures.hashmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SubArraySum {
    public boolean hasSubarraySum(int[] nums, int k) {
        if (nums == null) {
            return false;
        }
        Set<Integer> seen = new HashSet<>();
        seen.add(0);
        int currentSum = 0;
        for (int num : nums) {
            currentSum += num;
            if (seen.contains(currentSum - k)) {
                return true;
            }
            seen.add(currentSum);
        }
        return false;
    }

    public int countSubarraysWithSum(int[] nums, int k) {
        if (nums == null) {
            return 0;
        }
        Map<Integer, Integer> freq = new HashMap<>();
        freq.put(0, 1);
        int currentSum = 0;
        int count = 0;
        for (int num : nums) {
            currentSum += num;
            count += freq.getOrDefault(currentSum - k, 0);
            freq.put(currentSum, freq.getOrDefault(currentSum, 0) + 1);
        }
        return count;
    }
}
