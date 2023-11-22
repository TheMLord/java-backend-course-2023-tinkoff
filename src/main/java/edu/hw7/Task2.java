package edu.hw7;

import java.util.stream.LongStream;

/**
 * Factorial calculate class.
 */
public final class Task2 {
    private static final String INVALID_VALUE = "The number must not be negative";

    /**
     * Class constructor.
     */
    private Task2() {

    }

    /**
     * Method parallel calculate factorial.
     *
     * @param n number greater than or equal to zero.
     * @return factorial n.
     */
    public static long parallelFactorial(long n) {
        if (n < 0) {
            throw new IllegalArgumentException(INVALID_VALUE);
        }
        return LongStream.rangeClosed(1, n).boxed().toList().parallelStream().reduce(1L, (x, y) -> x * y);
    }

    /**
     * Method no-parallel calculate factorial.
     *
     * @param n number greater than or equal to zero.
     * @return factorial n.
     */
    public static long factorial(long n) {
        if (n < 0) {
            throw new IllegalArgumentException(INVALID_VALUE);
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

}
