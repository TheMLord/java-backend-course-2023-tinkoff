package edu.hw8;

import edu.hw8.Task2.Fibonacci;
import edu.hw8.Task2.FixedThreadPool;
import edu.hw8.Task2.ThreadPool;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    private static final double NANO_T0_SECONDS = 0.000000001;

    @Nested
    @DisplayName("thread pool health tests")
    class EfficiencyPool {
        private static Stream<Arguments> countThreadAndTaskData() {
            return Stream.of(
                Arguments.of(5, 1),
                Arguments.of(5, 2),
                Arguments.of(5, 3),
                Arguments.of(5, 4),
                Arguments.of(5, 5),
                Arguments.of(5, 10),
                Arguments.of(5, 100),
                Arguments.of(5, 1000),
                Arguments.of(5, 5000)
            );
        }

        @ParameterizedTest
        @MethodSource("countThreadAndTaskData")
        @DisplayName(
            "Test that the thread pool performs the correct number of tasks and returned the correct number of completed tasks")
        void testThatTheThreadPoolPerformsTheCorrectNumberOfTasksAndReturnedTheCorrectNumberOfCompletedTasks(
            int countThreads,
            int countTask
        ) throws Exception {
            var tasksCompleted = new CountDownLatch(countTask);
            ThreadPool threadPool = FixedThreadPool.create(countThreads);

            var actualCompleteTaskCount = new AtomicInteger(0);
            for (int i = 0; i < countTask; i++) {
                threadPool.execute(() -> {
                    actualCompleteTaskCount.incrementAndGet();
                    tasksCompleted.countDown();
                });
            }
            threadPool.start();

            tasksCompleted.await();
            threadPool.close();

            assertThat(actualCompleteTaskCount.get()).isEqualTo(countTask);
        }
    }

    @Nested
    @DisplayName("Fibonacci time test")
    class FibonacciTests {
        @RepeatedTest(10)
        @DisplayName("Test time of parallel calculation of Fibonacci numbers in comparison with sequential")
        void testTimeOfParallelCalculationOfFibonacciNumbersInComparisonWithSequential() throws Exception {
            ThreadPool threadPool = FixedThreadPool.create(5);
            int countNumber = 10;
            var tasksCompleted = new CountDownLatch(countNumber);
            int shiftFib = 10;

            //to display the results
            var ps = new PrintStream(System.out);

            // parallel initializer task
            for (int i = 0; i < countNumber; i++) {
                int n1 = i + shiftFib;
                threadPool.execute(() -> {
                    Fibonacci.calculateFibonacci(n1);
                    tasksCompleted.countDown();
                });
            }

            // start parallel calculate
            var timeStartParallel = System.nanoTime();
            threadPool.start();
            tasksCompleted.await();
            threadPool.close();
            var timeEndParallel = System.nanoTime();
            ps.println("10 fibonacci numbers were calculated in parallel in " +
                (timeEndParallel - timeStartParallel) * NANO_T0_SECONDS + " seconds");

            //start sequential calculations
            var timeStartSequential = System.nanoTime();

            for (int i = 0; i < countNumber; i++) {
                int n2 = i + shiftFib;
                long fib = Fibonacci.calculateFibonacci(n2);
            }
            var timeEndSequential = System.nanoTime();
            ps.println("10 fibonacci numbers were calculated in sequential in " +
                (timeEndSequential - timeStartSequential) * NANO_T0_SECONDS + " seconds");

        }

    }

}
