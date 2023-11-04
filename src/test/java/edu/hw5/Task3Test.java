package edu.hw5;

import edu.hw5.task3.Task3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class Task3Test {
    private static Stream<Arguments> validDateTask3Test() {
        return Stream.of(
            Arguments.of("10/11/2023"),
            Arguments.of("10/11/23"),
            Arguments.of("1/11/2023"),
            Arguments.of("1/11/23"),
            Arguments.of("10/11/2023"),
            Arguments.of("10/11/23"),
            Arguments.of("1/11/2023"),
            Arguments.of("1/11/23"),

            Arguments.of("2023-11-10"),
            Arguments.of("2023-1-1"),
            Arguments.of("23-11-10"),
            Arguments.of("2023-11-1"),
            Arguments.of("23-11-1"),
            Arguments.of("2023-11-1"),
            Arguments.of("23-11-1"),

            Arguments.of("yesterday"),
            Arguments.of("today"),
            Arguments.of("tomorrow"),
            Arguments.of("next day"),
            Arguments.of("next week"),
            Arguments.of("next month"),
            Arguments.of("next year"),

            Arguments.of("1 day ago"),
            Arguments.of("1 week ago"),
            Arguments.of("1 month ago"),
            Arguments.of("1 year ago"),

            Arguments.of("2 days ago"),
            Arguments.of("5 weeks ago"),
            Arguments.of("6 months ago"),
            Arguments.of("7 years ago")
        );
    }

    private static Stream<Arguments> invalidDateTask3Test() {
        return Stream.of(
            Arguments.of("1011/2023"),
            Arguments.of("10//11/23"),

            Arguments.of("2023-111-10"),
            Arguments.of("2023-11"),
            Arguments.of("2311-10"),
            Arguments.of("2023-/11-1"),
            Arguments.of("23-*11-1"),
            Arguments.of("202/3-11-1"),
            Arguments.of("23-1/1-1"),

            Arguments.of("yesterdays"),
            Arguments.of("todays"),
            Arguments.of("tomorrows"),

            Arguments.of("next fdf day"),
            Arguments.of("next ds week"),
            Arguments.of("next months"),
            Arguments.of("nextt year"),

            Arguments.of("a day ago"),
            Arguments.of("1 week agos"),
            Arguments.of("1 month agos"),
            Arguments.of("1 yeafr ago"),

            Arguments.of("2 dayz ago"),
            Arguments.of("5 weks ago"),
            Arguments.of("6 monhs ago"),
            Arguments.of("a years ago")
        );
    }

    @ParameterizedTest
    @MethodSource("validDateTask3Test")
    @DisplayName("Test that the method was able to recognize valid data values and returned a non-empty Optional")
    void testThatTheMethodWasAbleToRecognizeValidDataValuesAndReturnedANonEmptyOptional(String date) {
        Optional<LocalDate> parsedDate = Task3.parseDate(date);
        assertThat(parsedDate).isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidDateTask3Test")
    @DisplayName("Test that the method was able to recognize invalid data values and returned a empty Optional")
    void testThatTheMethodWasAbleToRecognizeInvalidDataValuesAndReturnedAEmptyOptional(String date) {
        Optional<LocalDate>  parsedDate = Task3.parseDate(date);

        assertThat(parsedDate).isEmpty();
    }

}
