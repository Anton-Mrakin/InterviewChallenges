package com.mrakin.algorythms;

public class CostClimbingStair {
    public int cost(int[] costs) {
        int[] dp = new int[costs.length];
        dp[0] = costs[0];
        dp[1] = costs[1];
        for (int i = 2; i<costs.length; i++) {
            dp[i] = costs[i] + Math.min(dp[i-1], dp[i-2]);
        }
        return Math.min(dp[costs.length-1], dp[costs.length - 2]);
    }
}
