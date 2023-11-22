package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.List;

class Task4Test {
    private static final List<Integer> N_TEST_LIST = List.of(
        10_000,
        100_000,
        1_000_000,
        10_000_000
    );
    private static final int COUNT_TEST = 4;

    private static final double NANO_T0_SECONDS = 0.000000001;

    private static final String RESULT_TEMPLATE = "Результат вычисления: %f";
    private static final String COUNT_ITERATION_TEMPLATE = "Число итераций: %d";
    private static final String TEST_NUMBER_TEMPLATE = "Тест №%d";
    private static final String SINGLE_PI_TEMPLATE = "Время на выполнения в одном потоке %f секунд";
    private static final String MULTI_THREAD_PI_TEMPLATE = "Время многопоточного выполнения %f секунд";
    private static final String CALCULATING_ERROR = "Погрешность %f";

    @Test
    @DisplayName("Test of the multithreaded and single-threaded calculation of Pi returned the statistics of the work")
    void testOfTheMultithreadedAndSingleThreadedCalculationOfPiReturnedTheStatisticsOfTheWork() {
        double totalSingleThreadTime = 0;
        double totalMultiThreadTime = 0;

        double start;
        double end;

        int countTest = 0;
        try {
            var ps = new PrintStream(System.out);
            while (countTest <= COUNT_TEST - 1) {
                ps.println(new Formatter().format(TEST_NUMBER_TEMPLATE, countTest + 1));
                ps.println(new Formatter().format(COUNT_ITERATION_TEMPLATE, N_TEST_LIST.get(countTest)));
                ps.println();

                var currentCountIter = N_TEST_LIST.get(countTest);

                start = System.nanoTime();
                var resPi1 = ApproximatingPi.approximationPiSingleThread(currentCountIter);
                end = System.nanoTime();
                ps.println(new Formatter().format(SINGLE_PI_TEMPLATE, (end - start) * NANO_T0_SECONDS));
                ps.println(new Formatter().format(RESULT_TEMPLATE, resPi1));
                ps.println(new Formatter().format(CALCULATING_ERROR, Math.abs(Math.PI - resPi1)));

                totalSingleThreadTime += end - start;

                ps.println();

                start = System.nanoTime();
                var resPi2 = ApproximatingPi.approximationPiMultiThreading(currentCountIter);
                end = System.nanoTime();
                ps.println(new Formatter().format(MULTI_THREAD_PI_TEMPLATE, (end - start) * NANO_T0_SECONDS));
                ps.println(new Formatter().format(RESULT_TEMPLATE, resPi2));
                ps.println(new Formatter().format(CALCULATING_ERROR, Math.abs(Math.PI - resPi2)));

                totalMultiThreadTime += end - start;

                ps.println();

                countTest++;
            }

            ps.println("Среднее время выполнения однопоточного исполнения " + totalSingleThreadTime / COUNT_TEST);
            ps.println("Среднее время выполнения многопоточного исполнения " + totalMultiThreadTime / COUNT_TEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
