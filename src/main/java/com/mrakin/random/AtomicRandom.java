package com.mrakin.random;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicRandom {
    private final AtomicLong state;

    public AtomicRandom(long seed) {
        this.state = new AtomicLong(seed);
    }

    public long nextLong() {
        long x = state.getAndAdd(0x9E3779B97F4A7C15L);
        return mix64(x);
    }

    private static long mix64(long z) {
        z = (z ^ (z >>> 30)) * 0xbf58476d1ce4e5b9L;
        z = (z ^ (z >>> 27)) * 0x94d049bb133111ebL;
        return z ^ (z >>> 31);
    }
}
