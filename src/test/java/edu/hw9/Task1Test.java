package edu.hw9;

import edu.hw9.Task1.StatsCollector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class Task1Test {

    @Nested
    @DisplayName("multithreading tests")
    class MultithreadingTests {

        @RepeatedTest(100)
        @DisplayName(
            "Test that the service correctly accepts metrics in multithreading and returned the correct number of accepted metric")
        void testThatTheServiceCorrectlyAcceptsMetricsInMultithreadingAndReturnedTheCorrectNumberOfAcceptedMetric()
            throws InterruptedException {
            var collector = new StatsCollector();
            var exceptedCountPushMetric = 10000;

            pushMetricAndShutDown(collector, exceptedCountPushMetric);

            var actualCountPushMetric = collector.getMetricQueueSize();
            assertThat(actualCountPushMetric).isEqualTo(exceptedCountPushMetric);
        }

        @RepeatedTest(100)
        @DisplayName(
            "Test that at a random time, the stat called and will not spoil the buffer in the collector will return the correct total number of metrics")
        void testThatAtARandomTimeTheStatCalledAndWillNotSpoilTheBufferInTheCollectorWillReturnTheCorrectTotalNumberOfMetrics()
            throws InterruptedException {
            var collector = new StatsCollector();
            var exceptedCountPushMetric = 1000;

            try (ExecutorService executorService = Executors.newFixedThreadPool(5)) {

                pushMetricAndShutDown(collector, exceptedCountPushMetric);

                var statistic = collector.stats();
                executorService.shutdown();
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

                var actualReceivedStatisticsInCollector = statistic.size();
                var actualRemainingStatisticsInCollector = collector.getMetricQueueSize();
                assertThat(actualReceivedStatisticsInCollector + actualRemainingStatisticsInCollector)
                    .isEqualTo(exceptedCountPushMetric);
            }
        }

        private void pushMetricAndShutDown(StatsCollector collector, int exceptedCountPushMetric)
            throws InterruptedException {
            try (ExecutorService es = Executors.newFixedThreadPool(5)) {
                for (int i = 0; i < exceptedCountPushMetric; i++) {
                    es.submit(() ->
                        collector.push(
                            generateMetricName(exceptedCountPushMetric + 1),
                            generateMetricData()
                        )
                    );
                }
                es.shutdown();
                es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            }
        }

    }

    @Nested
    @DisplayName("Tests for the correct processing of metrics")
    class CorrectProcessingMetricsTests {
        private static Stream<Arguments> dataMetric() {
            return Stream.of(
                Arguments.of(new double[] {1.0, 2.0, 3.0}, 6.0, 1.0, 3.0, 2.0),
                Arguments.of(new double[] {0.0, 0.0, 0.0, 0.0, 0.0}, 0.0, 0.0, 0.0, 0.0),
                Arguments.of(new double[] {}, 0.0, 0.0, 0.0, 0.0)
            );
        }

        @ParameterizedTest
        @MethodSource("dataMetric")
        @DisplayName("Test name")
        void testName(
            double[] dataMetric,
            double exceptedSum,
            double exceptedMin,
            double exceptedMax,
            double exceptedAvr
        ) throws InterruptedException {
            var exceptedNameStatistic = generateMetricName(1);
            var collector = new StatsCollector();
            var exceptedSize = 1;

            collector.push(exceptedNameStatistic, dataMetric);
            var stats = collector.stats();

            assertThat(stats.size()).isEqualTo(exceptedSize);

            var statistic = stats.get(0);
            assertThat(statistic.name()).isEqualTo(exceptedNameStatistic);
            assertThat(statistic.sum()).isEqualTo(exceptedSum);
            assertThat(statistic.min()).isEqualTo(exceptedMin);
            assertThat(statistic.max()).isEqualTo(exceptedMax);
            assertThat(statistic.average()).isEqualTo(exceptedAvr);
        }

    }

    private static String generateMetricName(int count) {
        return String.valueOf(ThreadLocalRandom.current().nextInt(count + 1));
    }

    private static double[] generateMetricData() {
        return DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble())
            .limit(5)
            .toArray();
    }
}
