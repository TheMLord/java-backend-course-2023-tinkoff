package edu.hw7;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public final class Task1 {
    private static final Logger TASK_1_LOGGER = Logger.getLogger(Task1.class.getName());
    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Method count increment.
     */
    private void incrementCount() {
        this.counter.incrementAndGet();
    }

    /**
     * The method which of the three threads increments the counter by one.
     */
    public void increaseCounter() {
        var threadOne = new Thread(() -> {
            incrementCount();
            TASK_1_LOGGER.info("threadOne increased the counter by 1. Current value: " + counter);
        });
        var threadTwo = new Thread(() -> {
            incrementCount();
            TASK_1_LOGGER.info("threadTwo increased the counter by 1. Current value: " + counter);
        });
        var threadThree = new Thread(() -> {
            incrementCount();
            TASK_1_LOGGER.info("threadThree increased the counter by 1. Current value: " + counter);
        });

        threadOne.start();
        threadTwo.start();
        threadThree.start();

        try {
            threadOne.join();
            threadTwo.join();
            threadThree.join();
        } catch (InterruptedException e) {
            TASK_1_LOGGER.info("A thread interruption has occurred " + e.getMessage());
            TASK_1_LOGGER.info(Arrays.toString(e.getStackTrace()));
        }
        TASK_1_LOGGER.info("Current counter value: " + counter);
    }

    public AtomicInteger getCounter() {
        return counter;
    }

}
