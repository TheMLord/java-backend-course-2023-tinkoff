package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Task1 class.
 */
public final class Task1 {
    private static final DateTimeFormatter TIME_FORMATTER_TASK = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}, \\d{2}:\\d{2}";
    private static final Pattern DKA_DATE = Pattern.compile(DATE_REGEX);

    /**
     * Class constructor.
     */
    private Task1() {

    }

    /**
     * Method that calculates the average time spent in a user's computer club
     *
     * @param sessionTime array of user visits (time format - uuuu-MM-dd, hh:mm).
     * @return average user visit time.
     */
    public static String getSessionDuration(String[] sessionTime) {

        var visitorSessionDurationList = parseSessionTimeArray(sessionTime);

        var averageSessionDuration = getAverageSession(visitorSessionDurationList);

        return convertDurationToString(averageSessionDuration);

    }

    /**
     * Method that parses an array of time.
     *
     * @param timeArray array of user visits.
     * @return time array reduced to Duration.
     */
    private static List<Duration> parseSessionTimeArray(String[] timeArray) {
        return Arrays.stream(timeArray)
            .map(DKA_DATE::matcher)
            .map(matcher -> {
                if (matcher.find()) {
                    var dateOne = LocalDateTime.parse(matcher.group(), TIME_FORMATTER_TASK);
                    if (matcher.find()) {
                        var dateTwo = LocalDateTime.parse(matcher.group(), TIME_FORMATTER_TASK);
                        return Duration.between(dateOne, dateTwo);
                    }
                }
                throw new NumberFormatException("Input parameter format error");
            })
            .collect(Collectors.toList());
    }

    /**
     * Method calculates the average visit time.
     *
     * @param durationList array of time type Duration.
     * @return average user visit time.
     */
    private static Duration getAverageSession(List<Duration> durationList) {
        return Duration.ofSeconds(
            durationList
                .stream()
                .mapToLong(Duration::getSeconds)
                .sum()
                / durationList.size()
        );
    }

    /**
     * The method converts the average time spent by the user from Duration to a readable format ч м.
     *
     * @param duration calculated average time spent.
     * @return string format ч м
     */
    private static String convertDurationToString(Duration duration) {
        var hours = duration.toHours();
        var minutes = duration.minusHours(hours).toMinutes();

        return hours + "ч " + minutes + "м";
    }
}
