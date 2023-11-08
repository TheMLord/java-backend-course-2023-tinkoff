package edu.hw3;

import edu.hw1.Task7;
import edu.hw3.task2.ParenthesisClusteringException;
import edu.hw3.task2.Task2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Task2Test {
    private static Stream<Arguments> correctTask2TestData() {
        return Stream.of(
            Arguments.of(new String[] {"()()()"}, new String[] {"()", "()", "()"}),
            Arguments.of(new String[] {"((()))"}, new String[] {"((()))"}),
            Arguments.of(new String[] {"((()))(())()()(()())"}, new String[] {"((()))", "(())", "()", "()", "(()())"}),
            Arguments.of(new String[] {"((())())(()(()()))"}, new String[] {"((())())", "(()(()()))"})
        );
    }

    private static Stream<Arguments> erroneousTask2TestData() {
        return Stream.of(
            Arguments.of(
                new String[] {"()))()"},
                "It is impossible to cluster, the closing bracket cannot be earlier than the opening one"
            ),
            Arguments.of(
                new String[] {"(&())"},
                "The characters in the array must be enclosed in parentheses only"
            ),
            Arguments.of(
                new String[] {"()", "()()()"},
                "The array must have 1 element"
            ),
            Arguments.of(
                new String[] {"()()((())"},
                "This parenthesis array cannot be balanced"
            )
        );
    }

    @ParameterizedTest
    @MethodSource("correctTask2TestData")
    @DisplayName("Test that the clustering process was successful and returned true if correctly clustered parenthesis")
    void testThatTheClusteringProcessWasSuccessfulAndReturnedTrueIfCorrectlyClusteredParenthesis(
        String[] parenthesisArray, String[] correctCluster
    ) {

        var resultCluster = Task2.clustering(parenthesisArray);

        assertThat(resultCluster).isEqualTo(correctCluster);

    }

    @ParameterizedTest
    @MethodSource("erroneousTask2TestData")
    @DisplayName("Test the clustering method, taking invalid input data, returned the corresponding error")
    void testTheClusteringMethodTakingInvalidInputDataReturnedTheCorrespondingError(
        String[] parenthesisArray, String exceptionMessage
    ) {
        assertThatThrownBy(() -> Task2.clustering(parenthesisArray))
            .isInstanceOf(ParenthesisClusteringException.class)
            .hasMessage(exceptionMessage);

    }

}
