package edu.hw8.Task2;

/**
 * Thread Pool Contract
 */
public interface ThreadPool extends AutoCloseable {
    /**
     * Method that starts the thread pool.
     */
    void start();

    /**
     * The method that accepts the next task for the thread pool.
     * Puts the task in the execution queue.
     *
     * @param runnable task for the thread pool.
     */
    void execute(Runnable runnable);
}
