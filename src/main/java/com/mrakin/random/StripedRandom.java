package com.mrakin.random;

import java.util.Random;
import java.util.random.RandomGenerator;

public class StripedRandom {
    private final RandomGenerator[] stripes;
    private final int mask;

    public StripedRandom(int stripeCount, long seed) {
        if (Integer.bitCount(stripeCount) != 1) {
            throw new IllegalArgumentException("stripeCount must be power of two");
        }
        this.stripes = new RandomGenerator[stripeCount];
        this.mask = stripeCount - 1;
        for (int i = 0; i < stripeCount; i++) {
            stripes[i] = new Random(seed + i);
        }
    }

    public long nextLong() {
        return stripes[((int) Thread.currentThread().threadId()) & mask].nextLong();
    }
}