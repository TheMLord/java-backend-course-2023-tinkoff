package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class Task3Test {
    /**
     * Test data.
     */
    private static Stream<Arguments> task3TestData() {
        return Stream.of(
            Arguments.of(
                new String[] {"a", "bb", "a", "bb"},
                new HashMap<String, Integer>() {{
                    put("a", 2);
                    put("bb", 2);
                }}
            ),
            Arguments.of(
                new String[] {"this", "and", "that", "and"},
                new HashMap<String, Integer>() {{
                    put("this", 1);
                    put("and", 2);
                    put("that", 1);
                }}
            ),
            Arguments.of(
                new String[] {"код", "код", "код", "bug"},
                new HashMap<String, Integer>() {{
                    put("код", 3);
                    put("bug", 1);
                }}
            ),
            Arguments.of(
                new Integer[] {1, 1, 2, 2},
                new HashMap<Integer, Integer>() {{
                    put(1, 2);
                    put(2, 2);
                }}
            ),
            Arguments.of(
                new Double[] {1.5, 2.8, 0.0, 1.5},
                new HashMap<Double, Integer>() {{
                    put(1.5, 2);
                    put(2.8, 1);
                    put(0.0, 1);
                }}
            )
        );
    }

    @ParameterizedTest
    @MethodSource("task3TestData")
    @DisplayName(
        "Test that the freqDict method correctly processes an array of objects of different types and returned true if so")
    void testThatTheFreqDictMethodCorrectlyProcessesAnArrayOfObjectsOfDifferentTypesAndReturnedTrueIfSo(
        Object[] listWords, Map<Object, Integer> answerFreqWords
    ) {
        Map<Object, Integer> freqWords = Task3.freqDict(listWords);
        assertThat(freqWords).isEqualTo(answerFreqWords);
    }

}
