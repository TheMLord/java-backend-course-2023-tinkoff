package edu.hw11.task3;

import java.io.PrintWriter;

public final class Main {
    private Main() {
    }

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) throws Exception {
        try (var pw = new PrintWriter(System.out)) {
            var fibMethod = FibonacciBytecode.createClassFibonacci().getDeclaredMethod("fib", int.class);
            pw.println(fibMethod.invoke(null, 45));
        }
    }
}
