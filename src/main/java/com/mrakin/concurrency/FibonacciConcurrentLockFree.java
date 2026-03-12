package com.mrakin.concurrency;

import java.util.concurrent.atomic.AtomicReference;

public class FibonacciConcurrentLockFree {

    private static final class State {
        final long current;
        final long next;

        State(long current, long next) {
            this.current = current;
            this.next = next;
        }
    }

    private final AtomicReference<State> ref;

    public FibonacciConcurrentLockFree(long first, long second) {
        this.ref = new AtomicReference<>(new State(first, second));
    }

    public long next() {
        while (true) {
            State oldState = ref.get();
            State newState = new State(oldState.next, oldState.current + oldState.next);
            if (ref.compareAndSet(oldState, newState)) {
                return oldState.current;
            }
        }
    }
}