package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class Task1Test {
    private static Stream<Arguments> testData() {
        return Stream.of(
            Arguments.of(
                new String[] {
                    "2022-03-12, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"
                }, "3ч 40м"),
            Arguments.of(
                new String[] {
                    "2023-03-12, 10:10 - 2023-03-12, 19:10",
                    "2023-03-13, 09:10 - 2023-03-13, 13:10",
                    "2023-03-14, 08:10 - 2023-03-14, 11:10",
                    "2023-03-15, 13:10 - 2023-03-15, 15:10",
                    "2023-03-16, 11:10 - 2023-03-16, 12:10",
                    "2023-03-17, 04:10 - 2023-03-17, 19:10",
                }, "5ч 40м"
            )
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName(
        "Test that the method correctly calculates the average time a user visits a computer club and returned the time that corresponds to the template")
    void testThatTheMethodCorrectlyCalculatesTheAverageTimeAUserVisitsAComputerClubAndReturnedTheTimeThatCorrespondsToTheTemplate(
        String[] timeArray,
        String exceptedAnswer
    ) {
        var actualAnswer = Task1.getSessionDuration(timeArray);

        assertThat(actualAnswer).isEqualTo(exceptedAnswer);
    }

}
