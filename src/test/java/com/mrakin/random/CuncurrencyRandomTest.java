package com.mrakin.random;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CuncurrencyRandomTest {
    @Test
    void worksUnderConcurrency() throws Exception {
        StripedRandom random = new StripedRandom(64, 42L);
        int threads = 32;
        int perThread = 100_000;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        LongAdder counter = new LongAdder();
        try {
            CompletableFuture<?>[] futures = new CompletableFuture[threads];
            for (int t = 0; t < threads; t++) {
                futures[t] = CompletableFuture.runAsync(() -> {
                    for (int i = 0; i < perThread; i++) {
                        random.nextLong();
                        counter.increment();
                    }
                }, pool);
            }
            CompletableFuture.allOf(futures).join();
            assertEquals((long) threads * perThread, counter.sum());
        } finally {
            pool.shutdownNow();
        }
    }
}
