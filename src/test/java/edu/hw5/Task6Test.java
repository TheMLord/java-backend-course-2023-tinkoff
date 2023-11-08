package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task6Test {
    private static Stream<Arguments> validTForSData() {
        return Stream.of(
            Arguments.of("achfdbaabgabcaabg", "abc"),
            Arguments.of("ghfudjsikkdjghfdnjksfjghdjks", "fud"),
            Arguments.of("qwertyuioihgfdsdfghjklkjhgfxcvbnmnhgfdsasdfghjk", "kjhgfxcv"),
            Arguments.of("yturieopw[wpeorituyaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "uyaa")
        );
    }

    private static Stream<Arguments> invalidTForSData() {
        return Stream.of(
            Arguments.of("achfdbaabgabcaabg", "ggg"),
            Arguments.of("achfdbaabgabcaabg", "afhbаc"),
            Arguments.of("strigitiriri", "strr"),
            Arguments.of("abcdefg", "abcdfd"),
            Arguments.of("dsddsequence", "dsdfds"),
            Arguments.of("programming", "profoorope")
        );
    }

    @ParameterizedTest
    @MethodSource("validTForSData")
    @DisplayName(
        "Test the method correctly determines whether t is a subsequence of s and returned true for such data, where t is exactly a subsequence of s")
    void testTheMethodCorrectlyDeterminesWhetherTIsASubsequenceOfSAndReturnedTrueForSuchDataWhereTIsExactlyASubsequenceOfS(
        String s,
        String t
    ) {
        var isSubsequence = Task6.isSubsequence(s, t);

        assertThat(isSubsequence).isTrue();
    }

    @ParameterizedTest
    @MethodSource("invalidTForSData")
    @DisplayName(
        "Test the method correctly determines whether e is a subsequence of s and returned false for such data where t is not a subsequence of s")
    void testTheMethodCorrectlyDeterminesWhetherEIsASubsequenceOfSAndReturnedFalseForSuchDataWhereTIsNotASubsequenceOfS(
        String s,
        String t
    ) {
        var isSubsequence = Task6.isSubsequence(s, t);

        assertThat(isSubsequence).isFalse();
    }

}
