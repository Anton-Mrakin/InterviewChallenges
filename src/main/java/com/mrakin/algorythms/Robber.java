package com.mrakin.algorythms;

public class Robber {
    public int decide(int[] prices) {
        int[] dp = new int[prices.length];
        dp[0] = prices[0];
        dp[1] = Math.max(dp[0], prices[1]);
        for (int i = 2; i < prices.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + prices[i]);
        }
        return dp[prices.length - 1];
    }
}
