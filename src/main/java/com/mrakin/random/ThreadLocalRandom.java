package com.mrakin.random;

import java.util.SplittableRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.random.RandomGenerator;

public class ThreadLocalRandom {
    private final AtomicLong seedSequencer;
    private final ThreadLocal<RandomGenerator> local;

    public ThreadLocalRandom(long rootSeed) {
        this.seedSequencer = new AtomicLong(mix64(rootSeed));
        this.local = ThreadLocal.withInitial(() -> {
            long seed = seedSequencer.getAndAdd(0x9E3779B97F4A7C15L);
            return new SplittableRandom(seed ^ Thread.currentThread().threadId());
        });
    }

    public long nextLong() {
        return local.get().nextLong();
    }

    private static long mix64(long z) {
        z = (z ^ (z >>> 33)) * 0xff51afd7ed558ccdL;
        z = (z ^ (z >>> 33)) * 0xc4ceb9fe1a85ec53L;
        return z ^ (z >>> 33);
    }
}