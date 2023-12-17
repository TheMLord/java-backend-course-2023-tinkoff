package edu.hw10.task2.fibonacci;

import edu.hw10.task2.proxy.Cache;

public interface FibCalculator {
    @Cache(persist = true)
    long fib(int number);

    String fibString();
}
