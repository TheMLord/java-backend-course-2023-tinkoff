package edu.hw8.Task2;

/**
 * Class Fibonacci.
 */
public final class Fibonacci {

    /**
     * Class constructor.
     */
    private Fibonacci() {

    }

    /**
     * Method that calculates the n Fibonacci digit.
     *
     * @param n the number of the Fibonacci digit.
     * @return calculated Fibonacci digit.
     */
    public static long calculateFibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
        }
    }

}
