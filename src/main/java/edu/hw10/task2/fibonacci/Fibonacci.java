package edu.hw10.task2.fibonacci;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Fibonacci implements FibCalculator {
    private static final Logger FIBONACCI_LOGGER = LogManager.getLogger(Fibonacci.class.getName());

    @Override
    public long fib(int number) {
        if (number <= 1) {
            return number;
        } else {
            return fib(number - 1) + fib(number - 2);
        }
    }

    @Override
    public String fibString() {
        return "finString";
    }
}
