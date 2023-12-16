package edu.hw10.task2;

public class Main {
    public static void main(String[] args) {
        FibCalculator c = new Fibonacci();
        FibCalculator proxy = CacheProxy.create(c, c.getClass());

        System.out.println(proxy.fib(4));
        System.out.println(proxy.fib(4));
        System.out.println(proxy.fib(4));
        System.out.println(proxy.fib(5));
        System.out.println(proxy.fib(5));
    }
}
