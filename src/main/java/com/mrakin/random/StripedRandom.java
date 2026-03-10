package com.mrakin.random;

import java.util.Random;
import java.util.random.RandomGenerator;

public class StripedRandom {
    private final RandomGenerator[] stripes;
    private final ThreadLocal<Integer> localIndex;

    public StripedRandom(int stripeCount, long seed) {
        if (Integer.bitCount(stripeCount) != 1) {
            throw new IllegalArgumentException("stripeCount must be power of two");
        }
        this.stripes = new RandomGenerator[stripeCount];
        for (int i = 0; i < stripeCount; i++) {
            stripes[i] = new Random(seed + i);
        }
        localIndex = ThreadLocal.withInitial(() -> {
            long threadId = Thread.currentThread().threadId();
            int index = Long.hashCode(threadId) & (stripes.length - 1);
            return index;
        });
    }

    public long nextLong() {
        long threadId = Thread.currentThread().threadId();
        int index = Math.floorMod( //not bad at all in performance!
                Long.hashCode(threadId)
                , stripes.length);
//        int index = (int) (threadId % stripes.length);
//        int index = localIndex.get();
        return stripes[index].nextLong();
    }
}