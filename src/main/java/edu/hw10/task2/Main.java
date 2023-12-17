package edu.hw10.task2;

import edu.hw10.task2.fibonacci.FibCalculator;
import edu.hw10.task2.fibonacci.Fibonacci;
import edu.hw10.task2.proxy.CacheProxy;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final Path PATH_TO_LOGGING = Path.of(
        String.valueOf(Paths.get("").toAbsolutePath()), "src/main/java/edu/hw10/task2/logging.txt"
    );

    private Main() {

    }

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) {
        try (var pw = new PrintWriter(System.out)) {
            FibCalculator c = new Fibonacci();
            FibCalculator proxy = CacheProxy.create(c, PATH_TO_LOGGING);

            pw.println(proxy.fib(45));
            pw.println(proxy.fibString());
            pw.println(proxy.fib(4));
            pw.println(proxy.fib(4));
            pw.println(proxy.fib(5));
            pw.println(proxy.fibString());
            pw.println(proxy.fib(5));
        }
    }
}
