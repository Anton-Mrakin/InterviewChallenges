package com.mrakin.algorythms;

public class LCS {
    public String lcs(char[] first, char[] second) {
        int[][] dp = new int[first.length + 1][second.length + 1];
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < second.length; j++) {
                if (first[i] == second[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = first.length;
        int j = second.length;
        while (i > 0 && j > 0) {
            if (first[i - 1] == second[j - 1]) {
                sb.append(first[i - 1]);
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        return sb.reverse().toString();
    }
}
