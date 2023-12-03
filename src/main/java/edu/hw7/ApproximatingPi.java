package edu.hw7;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

/**
 * Class calculating the approximate value of Pi. Task4.
 */
public final class ApproximatingPi {

    private static final int TWO_NUMBER = 2;
    private static final int METHOD_NUMBER = 4;

    private static final double CIRCLE_RADIUS = 1.0;

    /**
     * Class constructor.
     */
    private ApproximatingPi() {

    }

    /**
     * Method for calculating the approximate value of Pi by the Monte Carlo method in one stream
     *
     * @param totalCount number of iterations of the algorithm
     * @return the approximate value of Pi
     */
    public static double approximationPiSingleThread(long totalCount) {
        long circleCount = 0;

        for (long i = 0; i < totalCount; i++) {
            double pointX = ThreadLocalRandom.current().nextDouble(CIRCLE_RADIUS);
            double pointY = ThreadLocalRandom.current().nextDouble(CIRCLE_RADIUS);

            circleCount += (Math.pow(pointX, TWO_NUMBER) + Math.pow(pointY, TWO_NUMBER) <= CIRCLE_RADIUS) ? 1 : 0;
        }
        return METHOD_NUMBER * (double) circleCount / totalCount;
    }

    /**
     * Method for calculating the approximate value of Pi by the Monte Carlo multithreaded method.
     *
     * @param totalCount number of iterations of the algorithm
     * @return the approximate value of Pi
     */
    public static double approximationPiMultiThreading(long totalCount) {
        long circleCount = LongStream.rangeClosed(1, totalCount).parallel()
            .filter(point -> {
                double pointX = ThreadLocalRandom.current().nextDouble(CIRCLE_RADIUS);
                double pointY = ThreadLocalRandom.current().nextDouble(CIRCLE_RADIUS);
                return Math.pow(pointX, TWO_NUMBER) + Math.pow(pointY, TWO_NUMBER) <= CIRCLE_RADIUS;
            })
            .count();

        return METHOD_NUMBER * (double) circleCount / totalCount;
    }

}
