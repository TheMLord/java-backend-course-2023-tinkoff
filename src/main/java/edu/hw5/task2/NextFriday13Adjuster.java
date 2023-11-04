package edu.hw5.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * TemporalAdjuster class task2.
 * Contains a redefined method to use TemporalAdjuster to search for the nearest Friday 13 for the nearest date.
 */
public class NextFriday13Adjuster implements TemporalAdjuster {
    private static final int DAY_13 = 13;

    @Override
    public Temporal adjustInto(Temporal temporal) {
        var currentDate = LocalDate.from(temporal);
        var nextFriday = currentDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));

        while (nextFriday.getDayOfMonth() != DAY_13) {
            nextFriday = nextFriday.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        }

        return nextFriday;
    }
}
