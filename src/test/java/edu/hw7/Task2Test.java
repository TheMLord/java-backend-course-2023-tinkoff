package edu.hw7;

import java.io.PrintStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class Task2Test {
    private static Stream<Arguments> testData() {
        return Stream.of(
            Arguments.of(0, 1),
            Arguments.of(1, 1),
            Arguments.of(2, 2),
            Arguments.of(3, 6),
            Arguments.of(4, 24),
            Arguments.of(5, 120),
            Arguments.of(6, 720),
            Arguments.of(7, 5040),
            Arguments.of(8, 40320),
            Arguments.of(9, 362880),
            Arguments.of(10, 3628800),
            Arguments.of(11, 39916800),
            Arguments.of(12, 479001600),
            Arguments.of(13, 6227020800L),
            Arguments.of(14, 87178291200L),
            Arguments.of(15, 1307674368000L),
            Arguments.of(16, 20922789888000L),
            Arguments.of(17, 355687428096000L),
            Arguments.of(18, 6402373705728000L),
            Arguments.of(19, 121645100408832000L)

        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName(
        "Test that the parallel calculation of the factorial of the number is performed correctly and will return the correct value")
    void testThatTheParallelCalculationOfTheFactorialOfTheNumberIsPerformedCorrectlyAndWillReturnTheCorrectValue(
        long n,
        long exceptedFactorial
    ) {
        long actualFactorial = Task2.parallelFactorial(n);
        assertThat(actualFactorial).isEqualTo(exceptedFactorial);
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName("Test time of factorial calculation in parallel and not parallel")
    void testTimeOfFactorialCalculationInParallelAndNotParallel(
        long n,
        long exceptedFactorial
    ) {
        double startTime;
        double endTime;

        try {
            var ps = new PrintStream(System.out);

            startTime = System.nanoTime();
            long actualFactorialParallel = Task2.parallelFactorial(n);
            endTime = System.nanoTime();
            ps.println("Время выполнения параллельного вычисления " + (endTime - startTime) * 0.000000001 + " секунд");

            startTime = System.nanoTime();
            long actualFactorialNoParallel = Task2.factorial(n);
            endTime = System.nanoTime();
            ps.println(
                "Время выполнения не параллельного вычисления " + (endTime - startTime) * 0.000000001 + " секунд");

            assertThat(actualFactorialParallel).isEqualTo(exceptedFactorial);
            assertThat(actualFactorialNoParallel).isEqualTo(exceptedFactorial);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
