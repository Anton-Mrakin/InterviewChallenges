package com.mrakin.concurrency;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciConcurrentLockFreeTest {

    @Test
    void testSequential() {
        FibonacciConcurrentLockFree fib = new FibonacciConcurrentLockFree(0, 1);
        long[] expected = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34};
        for (long e : expected) {
            assertEquals(e, fib.next());
        }
    }

    @Test
    void testConcurrentUniquenessAndCompleteness() throws InterruptedException {
        // Start from 1, 2 to ensure all numbers in the sequence are unique (no 1, 1)
        int totalCalls = 80;
        FibonacciConcurrentLockFree fib = new FibonacciConcurrentLockFree(1, 2);
        Set<Long> results = ConcurrentHashMap.newKeySet();
        
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(totalCalls);
        
        for (int i = 0; i < totalCalls; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    results.add(fib.next());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }
        
        startLatch.countDown(); // Simultaneous start
        assertTrue(endLatch.await(10, TimeUnit.SECONDS), "Timed out waiting for threads");
        
        assertEquals(totalCalls, results.size(), "Should have exactly " + totalCalls + " unique values");
        
        // Verify sequence
        List<Long> sortedResults = new ArrayList<>(results);
        Collections.sort(sortedResults);
        
        List<Long> expected = generateFibonacci(totalCalls, 1, 2);
        assertEquals(expected, sortedResults, "Sequence mismatch after concurrent calls");
    }

    @Test
    void testHighContentionStress() throws InterruptedException {
        int numThreads = 16;
        int callsPerThread = 5000;
        int totalCalls = numThreads * callsPerThread;
        
        // We don't check sequence here, just uniqueness and no lost updates
        // Starting from 1, 2 and doing many calls will overflow long, but uniqueness should hold
        // because the sequence is strictly increasing even with overflow for many steps, 
        // until it cycles back (which takes a lot of time).
        FibonacciConcurrentLockFree fib = new FibonacciConcurrentLockFree(1, 2);
        Set<Long> results = ConcurrentHashMap.newKeySet();
        
        try (ExecutorService executor = Executors.newFixedThreadPool(numThreads)) {
            for (int i = 0; i < totalCalls; i++) {
                executor.submit(() -> {
                    results.add(fib.next());
                });
            }
        }

        assertEquals(totalCalls, results.size(), "Every call to next() must return a unique value");
    }

    @Test
    void testVirtualThreadsContention() {
        int totalCalls = 10000;
        FibonacciConcurrentLockFree fib = new FibonacciConcurrentLockFree(1, 2);
        Set<Long> results = ConcurrentHashMap.newKeySet();

        // Java 21 Virtual Threads
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < totalCalls; i++) {
                executor.submit(() -> {
                    results.add(fib.next());
                });
            }
        }

        assertEquals(totalCalls, results.size(), "Should be unique with virtual threads");
    }

    @Test
    void testExtremeContention() throws InterruptedException {
        // Many threads, each doing one call, starting exactly at the same time
        int numThreads = 1000;
        FibonacciConcurrentLockFree fib = new FibonacciConcurrentLockFree(1, 2);
        Set<Long> results = ConcurrentHashMap.newKeySet();
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        
        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                try {
                    latch.await();
                    results.add(fib.next());
                } catch (InterruptedException ignored) {}
            });
        }
        
        latch.countDown(); // Release all
        executor.shutdown();
        assertTrue(executor.awaitTermination(30, TimeUnit.SECONDS), "Timeout in extreme contention");
        
        assertEquals(numThreads, results.size(), "All threads should get unique values");
    }

    private List<Long> generateFibonacci(int n, long f, long s) {
        List<Long> res = new ArrayList<>();
        long a = f;
        long b = s;
        for (int i = 0; i < n; i++) {
            res.add(a);
            long next = Math.addExact(a, b); // Use addExact to catch overflow in test generator
            a = b;
            b = next;
        }
        return res;
    }
}
