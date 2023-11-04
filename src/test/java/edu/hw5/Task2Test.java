package edu.hw5;

import edu.hw5.task2.Task2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task2Test {
    private static Stream<Arguments> dataGetListFriday13Test() {
        return Stream.of(
            Arguments.of(1925, List.of("1925-02-13", "1925-03-13", "1925-11-13")),
            Arguments.of(2000, List.of("2000-10-13")),
            Arguments.of(2024, List.of("2024-09-13", "2024-12-13")),
            Arguments.of(2023, List.of("2023-01-13", "2023-10-13"))
        );
    }

    @ParameterizedTest
    @MethodSource("dataGetListFriday13Test")
    @DisplayName(
        "Test that checks whether the method correctly returns a list of Friday the 13th in the specified year returns a list of dates that is checked with a template")
    void testThatChecksWhetherTheMethodCorrectlyReturnsAListOfFridayThe13ThInTheSpecifiedYearReturnsAListOfDatesThatIsCheckedWithATemplate(
        int year,
        List<String> exceptedListFriday13
    ) {
        var actualListFriday13 = Task2.getAllFriday13InYear(year);

        assertThat(actualListFriday13).isEqualTo(exceptedListFriday13);
    }

}
