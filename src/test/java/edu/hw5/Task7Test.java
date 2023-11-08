package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class Task7Test {
    private static Stream<Arguments> validArgumentsForThreeLengthThirdZeroTask() {
        return Stream.of(
            Arguments.of("1101010101001010"),
            Arguments.of("000"),
            Arguments.of("01010100111"),
            Arguments.of("1001010100101010101")
        );
    }

    private static Stream<Arguments> invalidArgumentsForThreeLengthThirdZeroTask() {
        return Stream.of(
            Arguments.of(""),
            Arguments.of("00"),
            Arguments.of("111"),
            Arguments.of("1"),
            Arguments.of("0"),
            Arguments.of("00110101"),
            Arguments.of("001111110101")
        );
    }

    private static Stream<Arguments> validArgumentsForStartAndEndSameNumberTask() {
        return Stream.of(
            Arguments.of("1010000011001010010101"),
            Arguments.of("11"),
            Arguments.of("00"),
            Arguments.of("1"),
            Arguments.of("0"),
            Arguments.of("001101101010101000"),
            Arguments.of("1111111111111111111"),
            Arguments.of("00000000000000000000000")
        );
    }

    private static Stream<Arguments> invalidArgumentsForStartAndEndSameNumberTask() {
        return Stream.of(
            Arguments.of("10"),
            Arguments.of("01"),
            Arguments.of(""),
            Arguments.of("101001011110010"),
            Arguments.of("010010101"),
            Arguments.of("101101101010101000"),
            Arguments.of("1111111111111111110"),
            Arguments.of("00000000000000000000001")
        );
    }

    private static Stream<Arguments> validArgumentsLengthOfAtLeastOneAndNoMoreThanThreeNumber() {
        return Stream.of(
            Arguments.of("1"),
            Arguments.of("0"),
            Arguments.of("10"),
            Arguments.of("11"),
            Arguments.of("01"),
            Arguments.of("011"),
            Arguments.of("111"),
            Arguments.of("000"),
            Arguments.of("010")
        );
    }

    private static Stream<Arguments> invalidArgumentsLengthOfAtLeastOneAndNoMoreThanThreeNumber() {
        return Stream.of(
            Arguments.of("111111"),
            Arguments.of("0110101"),
            Arguments.of(""),
            Arguments.of("1010"),
            Arguments.of("1111"),
            Arguments.of("0000"),
            Arguments.of("001010"),
            Arguments.of("0110"),
            Arguments.of("101010010000000000000000")
        );
    }

    @ParameterizedTest
    @MethodSource("validArgumentsForThreeLengthThirdZeroTask")
    @DisplayName(
        "Test that processes a sequence of 0 and 1 of length at least with the third zero returned true for valid sequences of such a condition")
    void testThatProcessesASequenceOf0And1OfLengthAtLeastWithTheThirdZeroReturnsTrueForValidSequencesOfSuchACondition(
        String s
    ) {
        var isValid = Task7.isStringHaveLengthNotLessThenThreeAndThirdIsZero(s);
        assertThat(isValid).isTrue();
    }

    @ParameterizedTest
    @MethodSource("invalidArgumentsForThreeLengthThirdZeroTask")
    @DisplayName(
        "Test that processes a sequence of 0 and 1 of length at least with the third zero returned false for invalid sequences of such a condition")
    void testThatProcessesASequenceOf0And1OfLengthAtLeastWithTheThirdZeroReturnsFalseForInvalidSequencesOfSuchACondition(
        String s
    ) {
        var isValid = Task7.isStringHaveLengthNotLessThenThreeAndThirdIsZero(s);
        assertThat(isValid).isFalse();
    }

    @ParameterizedTest
    @MethodSource("validArgumentsForStartAndEndSameNumberTask")
    @DisplayName(
        "Test that processes a sequence of 0 and 1 which start and end same number returned true for valid sequences of such a condition")
    void
    testThatProcessesASequenceOf0And1WhichStartAndEndSameNumberReturnedTrueForValidSequencesOfSuchACondition(
        String s
    ) {
        var isValid = Task7.isStringStartAndEntSameNumber(s);
        assertThat(isValid).isTrue();
    }

    @ParameterizedTest
    @MethodSource("invalidArgumentsForStartAndEndSameNumberTask")
    @DisplayName(
        "Test that processes a sequence of 0 and 1 which start and end same number returned false for invalid sequences of such a condition")
    void testThatProcessesASequenceOf0And1WhichStartAndEndSameNumberReturnedFalseForInvalidSequencesOfSuchACondition(
        String s
    ) {
        var isValid = Task7.isStringStartAndEntSameNumber(s);
        assertThat(isValid).isFalse();
    }

    @ParameterizedTest
    @MethodSource("validArgumentsLengthOfAtLeastOneAndNoMoreThanThreeNumber")
    @DisplayName(
        "Test that processes a sequence of 0 and 1 with length of at least one and no more than three number returned true for valid sequences of such a condition")
    void testThatProcessesASequenceOf0And1WithLengthOfAtLeastOneAndNoMoreThanThreeNumberReturnedTrueForValidSequencesOfSuchACondition(
        String s
    ) {
        var isValid = Task7.isStringWithLengthOfLeastOneAndNoMoreThreeNumber(s);

        assertThat(isValid).isTrue();
    }

    @ParameterizedTest
    @MethodSource("invalidArgumentsLengthOfAtLeastOneAndNoMoreThanThreeNumber")
    @DisplayName(
        "Test that processes a sequence of 0 and 1 with length of at least one and no more than three number returned false for invalid sequences of such a condition")
    void testThatProcessesASequenceOf0And1WithLengthOfAtLeastOneAndNoMoreThanThreeNumberReturnedFalseForInvalidSequencesOfSuchACondition(
        String s
    ) {
        var isValid = Task7.isStringWithLengthOfLeastOneAndNoMoreThreeNumber(s);

        assertThat(isValid).isFalse();
    }

}
