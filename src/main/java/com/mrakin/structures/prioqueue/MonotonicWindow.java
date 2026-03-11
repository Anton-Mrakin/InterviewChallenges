package com.mrakin.structures.prioqueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class MonotonicWindow {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] ans = new int[Math.max(0, n - k + 1)];
        Deque<Integer> dq = new ArrayDeque<>(); // храним индексы

        for (int i = 0; i < n; i++) {
            // 1. выкинуть элементы, которые вышли из окна
            if (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            // 2. выкинуть с хвоста все меньшие элементы
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) {
                dq.pollLast();
            }

            // 3. добавить текущий индекс
            dq.offerLast(i);

            // 4. когда окно уже набралось, максимум в голове
            if (i >= k - 1) {
                ans[i - k + 1] = nums[dq.peekFirst()];
            }
        }
        return ans;
    }
}
