package com.mrakin.random;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class PerformanceRandomTest {

    private final AtomicRandom atomic = new AtomicRandom(42L);
    private final StripedRandom striped = new StripedRandom(64, 42L);
    private final ThreadLocalRandom threadLocalRandom = new ThreadLocalRandom(42);

    @Benchmark
    @Threads(16)
    public long atomicRandom() {
        return atomic.nextLong();
    }

    @Benchmark
    @Threads(16)
    public long stripedRandom() {
        return striped.nextLong();
    }

    @Benchmark
    @Threads(16)
    public long threadLocalRandom() {
        return threadLocalRandom.nextLong();
    }

    @Benchmark
    @Threads(16)
    public int jdkThreadLocalRandom() {
        return java.util.concurrent.ThreadLocalRandom.current().nextInt();
    }
}