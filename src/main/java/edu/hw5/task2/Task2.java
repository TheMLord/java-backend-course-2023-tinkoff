package edu.hw5.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class task2.
 */
public final class Task2 {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final int DAY_13 = 13;
    private static final int NEXT_MONTH_STEP = 1;
    private static final int NEXT_YEAR_STEP = 1;

    /**
     * Class constructor.
     */
    private Task2() {

    }

    /**
     * Method returns a list of all Friday the 13th in the specified year.
     *
     * @param year the year in which you need to find all Fridays 13 (year format uuuu).
     * @return list of all Friday the 13th in the specified year (return list data in the format [uuuu-MM-dd, ...]).
     */
    public static List<String> getAllFriday13InYear(int year) {
        var startDay = LocalDate.ofYearDay(year, DAY_13);

        return Stream
            .iterate(
                startDay,
                currentDay -> currentDay.getYear() != year + NEXT_YEAR_STEP,
                date -> date.plusMonths(NEXT_MONTH_STEP)
            )
            .filter(date -> date.getDayOfWeek().equals(DayOfWeek.FRIDAY))
            .map(date -> date.format(DATE_FORMATTER))
            .collect(Collectors.toList());
    }

    /**
     * Method that searches for the nearest Friday 13 to the specified date
     *
     * @param date date in the format uuuu-MM-dd
     * @return nearest Friday 13 to the specified date (returns date in the format uuuu-MM-dd)
     */
    public static String getNextFriday13(LocalDate date) {
        return date.with(new NextFriday13Adjuster()).format(DATE_FORMATTER);
    }
}
