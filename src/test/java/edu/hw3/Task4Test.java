package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class Task4Test {
    private static Stream<Arguments> validNumbersAndAnswersForTask4Test() {
        return Stream.of(
            Arguments.of(1, "Ⅰ"),
            Arguments.of(2, "ⅠⅠ"),
            Arguments.of(4, "ⅠⅤ"),
            Arguments.of(5, "Ⅴ"),
            Arguments.of(6, "ⅤⅠ"),
            Arguments.of(12, "ⅩⅠⅠ"),
            Arguments.of(16, "ⅩⅤⅠ"),
            Arguments.of(16, "ⅩⅤⅠ"),
            Arguments.of(569, "DⅬⅩⅠⅩ"),
            Arguments.of(1986, "ⅯⅭⅯⅬⅩⅩⅩⅤⅠ"),
            Arguments.of(3999, "ⅯⅯⅯⅭⅯⅩⅭⅠⅩ")
        );
    }

    private static Stream<Arguments> invalidNumbersAndAnswersForTask4Test() {
        return Stream.of(
            Arguments.of(-1),
            Arguments.of(0),
            Arguments.of(4000)
        );
    }

    @ParameterizedTest
    @MethodSource("validNumbersAndAnswersForTask4Test")
    @DisplayName("Test that convertToRoman method returned the correct converted numbers")
    void testThatConvertToRomanMethodReturnedTheCorrectConvertedNumbers(
        int arabicNumber,
        String correctConvertResult
    ) {
        String arabicToRomanNumber = Task4.convertToRoman(arabicNumber);

        assertThat(arabicToRomanNumber).isEqualTo(correctConvertResult);

    }

    @ParameterizedTest
    @MethodSource("invalidNumbersAndAnswersForTask4Test")
    @DisplayName("Test that convertToRoman method returned null because numbers is invalid for this method")
    void testThatConvertToRomanMethodReturnedNullBecauseNumbersIsInvalidForThisMethod(int arabicNumber) {
        String arabicToRomanNumber = Task4.convertToRoman(arabicNumber);

        assertThat(arabicToRomanNumber).isNull();
    }

}
