package com.mrakin.algorythms;

import java.util.ArrayDeque;
import java.util.Deque;

public class FibonacciStack {
    public long get(int n) {
        long c0 = 0L;
        long c1 = 1L;
        for (int i = 0; i<n ;i++) {
            long next = c0 + c1;
            c0 = c1;
            c1 = next;
        }
        return c1;
    }
}
