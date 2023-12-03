package edu.hw8.Task2;

import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

public final class Main {
    private Main() {

    }

    private static final int COUNT_N = 10;
    private static final int COUNT_THREAD = 5;

    public static void main(String[] args) throws Exception {
        try (ThreadPool threadPool = FixedThreadPool.create(COUNT_THREAD)) {
            var fib = new LinkedBlockingQueue<Long>();
            for (int i = 0; i < COUNT_N; i++) {
                int n = i + 1;
                threadPool.execute(() -> {
                    try {
                        fib.put(Fibonacci.calculateFibonacci(n));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            threadPool.start();
            try (var ps = new PrintStream(System.out)) {
                ps.println(fib);
            }
        }

    }
}
