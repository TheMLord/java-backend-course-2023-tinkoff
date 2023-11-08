package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class Task8Test {

    @Nested
    @DisplayName(
        "Group of tests that the sequence is of odd length")
    class TestFirstTask {
        private static Stream<Arguments> validDataOddLengthTest() {
            return Stream.of(
                Arguments.of("10101"),
                Arguments.of("1"),
                Arguments.of("0"),
                Arguments.of("1011110"),
                Arguments.of("000"),
                Arguments.of("10100000011"),
                Arguments.of("10100000001"),
                Arguments.of("001")
            );
        }

        private static Stream<Arguments> invalidDataOddLengthTest() {
            return Stream.of(
                Arguments.of(""),
                Arguments.of("10"),
                Arguments.of("00"),
                Arguments.of("101100000011"),
                Arguments.of("10100000000010"),
                Arguments.of("01011111")
            );
        }

        @ParameterizedTest
        @MethodSource("validDataOddLengthTest")
        @DisplayName(
            "Test that the method correctly processes a sequence of zeros and ones is of odd length and returns true for sequences that fit this condition")
        void testThatTheMethodCorrectlyProcessesASequenceOfZerosAndOnesIsOfOddLengthAndReturnsTrueForSequencesThatFitThisCondition(
            String s
        ) {
            var isValid = Task8.isOddLengthSequence(s);

            assertThat(isValid).isTrue();
        }

        @ParameterizedTest
        @MethodSource("invalidDataOddLengthTest")
        @DisplayName(
            "Test that the method correctly processes a sequence of zeros and ones of odd length and returns false for sequences unsuitable for this condition")
        void testThatTheMethodCorrectlyProcessesASequenceOfZerosAndOnesOfOddLengthAndReturnsFalseForSequencesUnsuitableForThisCondition(
            String s
        ) {
            var isValid = Task8.isOddLengthSequence(s);

            assertThat(isValid).isFalse();
        }
    }

    @Nested
    @DisplayName(
        "Group tests to check a sequence of odd length if it starts from zero or even length if it starts from one")
    class TestSecondTask {

        private static Stream<Arguments> validDataOddLengthStartingFromZero() {
            return Stream.of(
                Arguments.of("0"),
                Arguments.of("0101101"),
                Arguments.of("011"),
                Arguments.of("010101101"),
                Arguments.of("01111"),
                Arguments.of("01010101101"),
                Arguments.of("0111110"),
                Arguments.of("0101010101101"),
                Arguments.of("0111111"),
                Arguments.of("010101010100101"),
                Arguments.of("011111111"),
                Arguments.of("01010101010100101"),
                Arguments.of("01111111011"),
                Arguments.of("0101010101010101001"),
                Arguments.of("01111111111"),
                Arguments.of("010101010101010101001"),
                Arguments.of("0111111110111"),
                Arguments.of("01010100101010101010101"),
                Arguments.of("0111110111111"),
                Arguments.of("0101010010101010101010101"),
                Arguments.of("0111111111111")
            );
        }

        private static Stream<Arguments> invalidDataOddLengthStartingFromZero() {
            return Stream.of(
                Arguments.of("01"),
                Arguments.of("010110"),
                Arguments.of("0111"),
                Arguments.of("01011101"),
                Arguments.of("0111"),
                Arguments.of("0101010110"),
                Arguments.of("011110"),
                Arguments.of("010100101101"),
                Arguments.of("011111"),
                Arguments.of("01010110100101"),
                Arguments.of("01111111"),
                Arguments.of("0101101010100101"),
                Arguments.of("0111111011"),
                Arguments.of("010101001010101001"),
                Arguments.of("0111111111"),
                Arguments.of("01010101010101001001"),
                Arguments.of("011111111011"),
                Arguments.of("0101010010101001010101"),
                Arguments.of("011111011111"),
                Arguments.of("010101001010101010010101"),
                Arguments.of("011111111111"),
                Arguments.of("11111111111"),
                Arguments.of("110101010101010101001"),
                Arguments.of("1111111110111"),
                Arguments.of("11010100101010101010101"),
                Arguments.of("1111110111111"),
                Arguments.of("1101010010101010101010101"),
                Arguments.of("1111111111111"),
                Arguments.of("")
            );
        }

        private static Stream<Arguments> validDataEvenLengthStartWithOne() {
            return Stream.of(
                Arguments.of("10"),
                Arguments.of("10101101"),
                Arguments.of("1011"),
                Arguments.of("1010101101"),
                Arguments.of("101111"),
                Arguments.of("101010101101"),
                Arguments.of("10111110"),
                Arguments.of("10101010101101"),
                Arguments.of("10111111"),
                Arguments.of("1010101010100101"),
                Arguments.of("1011111111"),
                Arguments.of("101010101010100101"),
                Arguments.of("101111111011"),
                Arguments.of("10101010101010101001"),
                Arguments.of("101111111111"),
                Arguments.of("1010101010101010101001"),
                Arguments.of("10111111110111"),
                Arguments.of("101010100101010101010101"),
                Arguments.of("10111110111111"),
                Arguments.of("10101010010101010101010101"),
                Arguments.of("10111111111111")
            );
        }

        private static Stream<Arguments> invalidDataEvenLengthStartWithOne() {
            return Stream.of(
                Arguments.of("110"),
                Arguments.of("110101101"),
                Arguments.of("11011"),
                Arguments.of("11010101101"),
                Arguments.of("1101111"),
                Arguments.of("1101010101101"),
                Arguments.of("110111110"),
                Arguments.of("110101010101101"),
                Arguments.of("110111111"),
                Arguments.of("11010101010100101"),
                Arguments.of("11011111111"),
                Arguments.of("001010101010100101"),
                Arguments.of("001111111011"),
                Arguments.of("00101010101010101001"),
                Arguments.of("001111111111"),
                Arguments.of(""),
                Arguments.of("110111111110111"),
                Arguments.of("1101010100101010101010101"),
                Arguments.of("110111110111111"),
                Arguments.of("110101010010101010101010101"),
                Arguments.of("110111111111111")
            );
        }

        @ParameterizedTest
        @MethodSource("validDataOddLengthStartingFromZero")
        @DisplayName(
            "Test that the method correctly handles sequences of odd length starting from zero and returned true for valid sequences")
        void testThatTheMethodCorrectlyHandlesSequencesOfOddLengthStartingFromZeroAndReturnedTrueForValidSequences(
            String s
        ) {
            var isValid = Task8.isOddLengthIfSequenceStartWithZeroOrEvenInOther(s);

            assertThat(isValid).isTrue();
        }

        @ParameterizedTest
        @MethodSource("invalidDataOddLengthStartingFromZero")
        @DisplayName(
            "Test that the method correctly handles sequences of odd length starting from zero and returned false for invalid sequences")
        void testThatTheMethodCorrectlyHandlesSequencesOfOddLengthStartingFromZeroAndReturnedFalseForInvalidSequences(
            String s
        ) {
            var isValid = Task8.isOddLengthIfSequenceStartWithZeroOrEvenInOther(s);

            assertThat(isValid).isFalse();
        }

        @ParameterizedTest
        @MethodSource("validDataEvenLengthStartWithOne")
        @DisplayName(
            "Test that the method correctly processes an even-length sequence starting with one and returns true for valid sequences")
        void testThatTheMethodCorrectlyProcessesAnEvenLengthSequenceStartingWithOneAndReturnsTrueForValidSequences(
            String s
        ) {
            var isValid = Task8.isOddLengthIfSequenceStartWithZeroOrEvenInOther(s);

            assertThat(isValid).isTrue();
        }

        @ParameterizedTest
        @MethodSource("invalidDataEvenLengthStartWithOne")
        @DisplayName(
            "Test that the method correctly processes an even-length sequence starting with one and returns false for invalid sequences")
        void testThatTheMethodCorrectlyProcessesAnEvenLengthSequenceStartingWithOneAndReturnsFalseForInvalidSequences(
            String s
        ) {
            var isValid = Task8.isOddLengthIfSequenceStartWithZeroOrEvenInOther(s);

            assertThat(isValid).isFalse();

        }
    }

    @Nested
    @DisplayName(
        "Group of tests that verify that a sequence of zeros and ones contains a multiple of three zeros")
    class TestThirdTask {
        private static Stream<Arguments> validData() {
            return Stream.of(
                Arguments.of("000"),
                Arguments.of("10101011111111"),
                Arguments.of("10101111110111"),
                Arguments.of("10101111111110"),
                Arguments.of("001111111110"),
                Arguments.of("001101101011110"),
                Arguments.of("001101101011100000010")
            );
        }

        private static Stream<Arguments> invalidData() {
            return Stream.of(
                Arguments.of("01000"),
                Arguments.of(""),
                Arguments.of("100101111110111"),
                Arguments.of("101011001111111110"),
                Arguments.of("0011111101110"),
                Arguments.of("00110110110011110"),
                Arguments.of("0011011010110100000010")
            );
        }

        @ParameterizedTest
        @MethodSource("validData")
        @DisplayName(
            "Test that the method correctly processes a sequence of zeros and ones that it contains a number of zeros multiple of three returned true for valid sequences")
        void testThatTheMethodCorrectlyProcessesASequenceOfZerosAndOnesThatItContainsANumberOfZerosMultipleOfThreeReturnedTrueForValidSequences(
            String s
        ) {
            var isValid = Task8.isCountZeroMultipleThree(s);
            assertThat(isValid).isTrue();
        }

        @ParameterizedTest
        @MethodSource("invalidData")
        @DisplayName(
            "Test that the method correctly processes a sequence of zeros and ones that it contains a number of zeros multiple of three returned false for invalid sequences")
        void testThatTheMethodCorrectlyProcessesASequenceOfZerosAndOnesThatItContainsANumberOfZerosMultipleOfThreeReturnedFalseForInvalidSequences(
            String s
        ) {
            var isValid = Task8.isCountZeroMultipleThree(s);
            assertThat(isValid).isFalse();
        }

    }

    @Nested
    @DisplayName("Group of tests that checks whether the sequence is 11 or 111")
    class TestFourthTask {
        private static Stream<Arguments> validData() {
            return Stream.of(
                Arguments.of("1111"),
                Arguments.of("10101"),
                Arguments.of("1101011"),
                Arguments.of("1111101"),
                Arguments.of("110011"),
                Arguments.of("101010"),
                Arguments.of("1111111"),
                Arguments.of("101001"),
                Arguments.of("10101010"),
                Arguments.of("100010101"),
                Arguments.of("111001"),
                Arguments.of("1110000"),
                Arguments.of("1100000"),
                Arguments.of("101"),
                Arguments.of("001010"),
                Arguments.of("0010101001"),
                Arguments.of("001"),
                Arguments.of("00"),
                Arguments.of("110"),
                Arguments.of("00110011"),
                Arguments.of("001100")
            );
        }

        private static Stream<Arguments> invalidData() {
            return Stream.of(
                Arguments.of("11"),
                Arguments.of("111"),
                Arguments.of("")
            );
        }

        @ParameterizedTest
        @MethodSource("validData")
        @DisplayName(
            "Test that the method correctly processes a sequence of zeros and ones and does not allow everything except 11 or 111 will returned true if the sequence is valid")
        void testThatTheMethodCorrectlyProcessesASequenceOfZerosAndOnesAndDoesNotAllowEverythingExcept11Or111WillReturnedTrueIfTheSequenceIsValid(
            String s
        ) {
            var isValid = Task8.isAnySequenceOther11Or111(s);
            assertThat(isValid).isTrue();
        }

        @ParameterizedTest
        @MethodSource("invalidData")
        @DisplayName(
            "Test that the method correctly processes a sequence of zeros and ones and does not allow everything except 11 or 111 will returned false if the sequence is 11 or 111")
        void testThatTheMethodCorrectlyProcessesASequenceOfZerosAndOnesAndDoesNotAllowEverythingExcept11Or111WillReturnedFalseIfTheSequenceIs11Or111(
            String s
        ) {
            var isValid = Task8.isAnySequenceOther11Or111(s);
            assertThat(isValid).isFalse();

        }

    }

    @Nested
    @DisplayName("Group of tests that checks that every odd number is 1")
    class TestFifthTask {
        private static Stream<Arguments> validData() {
            return Stream.of(
                Arguments.of("1"),
                Arguments.of("10"),
                Arguments.of("10101"),
                Arguments.of("11111"),
                Arguments.of("10001"),
                Arguments.of("1011"),
                Arguments.of("1111"),
                Arguments.of("11001"),
                Arguments.of("1010011"),
                Arguments.of("1000001"),
                Arguments.of("101"),
                Arguments.of("110001"),
                Arguments.of("1111"),
                Arguments.of("1010101"),
                Arguments.of("111001"),
                Arguments.of("1011"),
                Arguments.of("101111"),
                Arguments.of("1000001"),
                Arguments.of("10101"),
                Arguments.of("1111111"),
                Arguments.of("111"),
                Arguments.of("101"),
                Arguments.of("1010")
            );
        }

        private static Stream<Arguments> invalidData() {
            return Stream.of(
                Arguments.of(""),
                Arguments.of("0"),
                Arguments.of("010"),
                Arguments.of("000"),
                Arguments.of("010101010"),
                Arguments.of("01000"),
                Arguments.of("00000101"),
                Arguments.of("0101010010"),
                Arguments.of("010001")
            );
        }

        @ParameterizedTest
        @MethodSource("validData")
        @DisplayName(
            "Test that correctly handles a sequence of zeros and ones where each odd character is 1 returned true if the sequence is valid")
        void testThatCorrectlyHandlesASequenceOfZerosAndOnesWhereEachOddCharacterIs1ReturnedTrueIfTheSequenceIsValid(
            String s
        ) {
            var isValid = Task8.isAnyOddNumberIs1(s);

            assertThat(isValid).isTrue();

        }

        @ParameterizedTest
        @MethodSource("invalidData")
        @DisplayName(
            "Test that correctly handles a sequence of zeros and ones where each odd character is 1 returned false if the sequence is invalid")
        void testThatCorrectlyHandlesASequenceOfZerosAndOnesWhereEachOddCharacterIs1ReturnedFalseIfTheSequenceIsInvalid(
            String s
        ) {
            var isValid = Task8.isAnyOddNumberIs1(s);

            assertThat(isValid).isFalse();
        }

    }


}
