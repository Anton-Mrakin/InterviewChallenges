package com.mrakin.threads.examples;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runners {
    public void simple() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("Hello from thread");
        });
        t.start();
        t.join();
    }

    public void executorService() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            executor.submit(() -> {
                System.out.println("running");
            });
        } finally {
            executor.shutdown();
        }
    }

    public void withResult() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        try {
            Callable<Integer> task = () -> {
                Thread.sleep(500);
                return 42;
            };

            Future<Integer> future = executor.submit(task);

            Integer result = future.get(); // блокируется, пока не готово
            System.out.println(result);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }
    }

    public void modernAsync() {
        CompletableFuture<Integer> future =
                CompletableFuture.supplyAsync(() -> 42)
                        .thenApply(x -> x * 2)
                        .thenApply(x -> x + 1);

        Integer result = future.join();
        System.out.println(result);
    }

    public void virtualThreads() throws ExecutionException, InterruptedException {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Integer> future = executor.submit(() -> {
                Thread.sleep(500);
                return 42;
            });

            System.out.println(future.get());
        }
    }
}
