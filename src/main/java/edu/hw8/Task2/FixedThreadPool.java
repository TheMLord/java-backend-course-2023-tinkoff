package edu.hw8.Task2;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * Fixed-length thread pool implementation class.
 */
public final class FixedThreadPool implements ThreadPool {
    private static final Logger THREAD_POOL_LOGGER = Logger.getLogger(FixedThreadPool.class.getName());
    private final Thread[] threadPool;
    private final LinkedBlockingQueue<Runnable> taskQueue;
    private final AtomicBoolean isShutdown;

    /**
     * Class constructor.
     *
     * @param countThread count threads for pool.
     */
    private FixedThreadPool(int countThread) {
        isShutdown = new AtomicBoolean(false);
        threadPool = new Thread[countThread];
        taskQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Factory method for creating a fixed-length thread pool.
     *
     * @param countThread count threads for pool.
     * @return an object of the FixedThreadPool class with the specified number of threads in the pool.
     */
    public static FixedThreadPool create(int countThread) {
        return new FixedThreadPool(countThread);
    }

    @Override
    public void start() {
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i] = new Thread(() -> {
                while (!isShutdown.get()) {
                    var task = taskQueue.poll();
                    if (task != null) {
                        task.run();
                    }
                }
            });
            threadPool[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            if (!isShutdown.get()) {
                taskQueue.put(runnable);
            }
        } catch (InterruptedException e) {
            THREAD_POOL_LOGGER.info("Добавление задачи завершилось с ошибкой" + e.getMessage());
            THREAD_POOL_LOGGER.info(Arrays.toString(e.getStackTrace()));
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void close() {
        isShutdown.set(true);
        for (Thread thread : threadPool) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
