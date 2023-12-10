package edu.hw9.Task1;

import edu.hw9.Task1.models.Metric;
import edu.hw9.Task1.models.Statistics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Statistics collection.
 */
public final class StatsCollector {
    private static final int COUNT_THREAD = 5;
    private static final double STATISTIC_VALUE_INITIALIZE = 0.0;
    private static final int EMPTY_METRIC_ARRAY_LENGTH = 0;
    private final ConcurrentLinkedQueue<Metric> blockingQueue;
    private final Lock lock = new ReentrantLock(true);

    public StatsCollector() {
        blockingQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * Method that adds another metric to the thread-safe collector queue
     */
    public void push(String nameMetric, double[] metricData) {
        var metric = new Metric(nameMetric, metricData);
        blockingQueue.add(metric);
    }

    /**
     * Method Aggregates the metric data available at the time of the request.
     * Metrics that were added after calling the method will not be returned,
     * they will be stored in the collector queue until the next call
     *
     * @return a list with calculated statistics
     */
    public List<Statistics> stats() throws InterruptedException {
        try (ExecutorService executors = Executors.newFixedThreadPool(COUNT_THREAD)) {
            List<Statistics> statisticsList = new ArrayList<>();
            int queueSize = blockingQueue.size();
            for (int i = 0; i < queueSize; i++) {
                executors.submit(() -> {
                    if (blockingQueue.peek() != null) {
                        var metric = blockingQueue.poll();
                        var statistics = processMetric(metric);
                        lock.lock();
                        try {
                            statisticsList.add(statistics);
                        } finally {
                            lock.unlock();
                        }
                    }
                });
            }

            executors.shutdown();
            executors.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            return new ArrayList<>(statisticsList);
        }
    }

    /**
     * Metric processing method
     *
     * @return statistics prepared according to the metric with the calculation of the sum,
     *     maximum, minimum element and average value
     */
    private Statistics processMetric(Metric metric) {
        double[] data = metric.data();

        double sum = Arrays.stream(data).sum();
        double min = Arrays.stream(data).min().orElse(STATISTIC_VALUE_INITIALIZE);
        double max = Arrays.stream(data).max().orElse(STATISTIC_VALUE_INITIALIZE);
        double average = data.length != EMPTY_METRIC_ARRAY_LENGTH ? sum / data.length : STATISTIC_VALUE_INITIALIZE;

        return new Statistics(metric.metricName(), sum, min, max, average);
    }

    /**
     * Debugging method that returns the number of metrics received
     */
    public int getMetricQueueSize() {
        return this.blockingQueue.size();
    }
}
