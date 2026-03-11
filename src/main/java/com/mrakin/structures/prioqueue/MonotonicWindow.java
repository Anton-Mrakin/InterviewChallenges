package com.mrakin.structures.prioqueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class MonotonicWindow {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> windowIndices = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int windowStart = i - k + 1;
            if (!windowIndices.isEmpty() && windowIndices.peekFirst() < windowStart) {
                windowIndices.pollFirst();
            }
            while (!windowIndices.isEmpty() && nums[windowIndices.peekLast()] <= nums[i]) {
                windowIndices.pollLast();
            }
            windowIndices.offerLast(i);
            if (i >= k - 1) {
                result[i - k + 1] = nums[windowIndices.peekFirst()];
            }
        }
        return result;
    }
}
