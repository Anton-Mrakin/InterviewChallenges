package com.mrakin.algorythms;

public class Knapsack {
    public int max(int[] weights, int[] values, int cap) {
        int[][] dp = new int[cap][cap];
        for (int i = 0 ; i<cap;i++) {
            for (int j = 0; j<weights.length; j++) {
                int weight = weights[j];
                int value = values[j];
                dp[i][j] = dp [i-1][j];
                if (cap >= weight) {
                    dp[i][j] = Math.max(dp[i][j], value + dp[i][j-weight]);
                }
            }
        }
        return dp[weights.length][cap];
    }
}
