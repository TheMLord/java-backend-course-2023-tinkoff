package edu.hw10.task2;

import edu.hw10.task2.fibonacci.FibCalculator;
import edu.hw10.task2.fibonacci.Fibonacci;
import edu.hw10.task2.proxy.CacheProxy;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    private static final double NANO_T0_SECONDS = 0.000000001;
    private static final String PATH_TO_LOG = "log.txt";

    @Test
    @DisplayName(
        "Test that calling a method with the same parameters, the result is taken from the cache and not calculated")
    void testThatCallingAMethodWithTheSameParametersTheResultIsTakenFromTheCacheAndNotCalculated(
        @TempDir Path testDir
    ) throws IOException {
        Path pathToLog = testDir.resolve(PATH_TO_LOG);
        Files.createFile(pathToLog);

        long exceptedFibonacciResult = 1134903170L;
        FibCalculator fibCalculator = new Fibonacci();
        var proxy = CacheProxy.create(fibCalculator, testDir.resolve(PATH_TO_LOG));

        var timeFirstCall = calculateFibonacciNumber(proxy, exceptedFibonacciResult);
        var timeSecondCall = calculateFibonacciNumber(proxy, exceptedFibonacciResult);

        assertThat(timeFirstCall > timeSecondCall).isTrue();
        assertThat(Files.readAllLines(pathToLog, StandardCharsets.UTF_8)).isNotEmpty();
    }

    private double calculateFibonacciNumber(FibCalculator proxy, long exceptedFibonacciResult) {
        var startTime = System.nanoTime();
        long actualResult = proxy.fib(45);
        var endTime = System.nanoTime();

        assertThat(actualResult).isEqualTo(exceptedFibonacciResult);

        return (endTime - startTime) * NANO_T0_SECONDS;
    }
}
