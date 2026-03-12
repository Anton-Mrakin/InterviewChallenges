package com.mrakin.algorythms;

import java.util.Arrays;

public class LIS {
    public int lis(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);;
        int res = 1;
        for (int i = 0; i<nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i]<nums[j]) {
                    dp[i] = Math.max(dp[j]+1, dp[i]);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }
}
