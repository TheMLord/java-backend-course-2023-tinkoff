package edu.hw7;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class Task1Test {
    @Test
    @DisplayName(
        "Test that the counter increment is performed correctly in single-threaded mode with a single method call and returned the expected value")
    void testThatTheCounterIncrementIsPerformedCorrectlyInSingleThreadedModeWithASingleMethodCallAndReturnedTheExpectedValue() {
        int exceptedCounterValue = 3;

        var task1 = new Task1();
        task1.increaseCounter();

        int actualCountValue = task1.getCounter().get();

        assertThat(actualCountValue).isEqualTo(exceptedCounterValue);
    }

    @Test
    @DisplayName(
        "Test that the counter increment is performed correctly in single-threaded mode with multiple method calls and returned the expected value")
    void testThatTheCounterIncrementIsPerformedCorrectlyInSingleThreadedModeWithMultipleMethodCallsAndReturnedTheExpectedValue() {
        var random = new Random();
        int n = random.nextInt(5, 100);
        int exceptedCounterValue = n * 3;

        var task1 = new Task1();
        for (int i = 0; i < n; i++) {
            task1.increaseCounter();
        }
        var actualCountValue = task1.getCounter().get();

        assertThat(actualCountValue).isEqualTo(exceptedCounterValue);
    }

    @Test
    @DisplayName(
        "Test that the counter increment is performed correctly in multithreaded mode returned the expected value")
    void testThatTheCounterIncrementIsPerformedCorrectlyInMultithreadedModeReturnedTheExpectedValue()
        throws InterruptedException {
        int exceptedCounterValue = 12;

        var task1 = new Task1();

        var testThreadOne = new Thread(task1::increaseCounter);
        var testThreadTwo = new Thread(task1::increaseCounter);
        var testThreadThree = new Thread(task1::increaseCounter);
        var testThreadFour = new Thread(task1::increaseCounter);

        testThreadOne.start();
        testThreadTwo.start();
        testThreadThree.start();
        testThreadFour.start();

        testThreadOne.join();
        testThreadOne.join();
        testThreadOne.join();
        testThreadOne.join();

        int actualCounterValue = task1.getCounter().get();

        assertThat(actualCounterValue).isEqualTo(exceptedCounterValue);
    }

}

