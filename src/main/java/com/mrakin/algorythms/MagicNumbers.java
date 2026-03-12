package com.mrakin.algorythms;

public class MagicNumbers {
    public int getMagicElementsCount(int[] nums) {
        long evenSuffixSum = 0;
        long oddSuffixSum = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i % 2 == 0) {
                evenSuffixSum += nums[i];
            } else {
                oddSuffixSum += nums[i];
            }
        }

        long evenPrefixSum = 0;
        long oddPrefixSum = 0;
        int magicCount = 0;

        for (int i = 0; i < nums.length; i++) {
            // "Remove" the current element from suffix sums to represent elements *after* index i
            if (i % 2 == 0) {
                evenSuffixSum -= nums[i];
            } else {
                oddSuffixSum -= nums[i];
            }

            // If we remove nums[i]:
            // - Prefix elements (indices < i) keep their parity.
            // - Suffix elements (indices > i) swap their parity.
            long totalEvenAfterRemoval = evenPrefixSum + oddSuffixSum;
            long totalOddAfterRemoval = oddPrefixSum + evenSuffixSum;

            if (totalEvenAfterRemoval == totalOddAfterRemoval) {
                magicCount++;
            }

            // Add the current element to prefix sums for the next element
            if (i % 2 == 0) {
                evenPrefixSum += nums[i];
            } else {
                oddPrefixSum += nums[i];
            }
        }

        return magicCount;
    }
}
