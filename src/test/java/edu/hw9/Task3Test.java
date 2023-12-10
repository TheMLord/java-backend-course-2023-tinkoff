package edu.hw9;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;
import edu.hw9.Task3.RecursiveDFS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    private static Stream<Arguments> graphs() {
        return Stream.of(
            Arguments.of(
                List.of(
                    List.of(1),
                    List.of(0, 3, 2),
                    List.of(3, 2, 1, 0),
                    List.of(4, 1, 2, 3, 0),
                    List.of(5),
                    List.of(1, 2, 3)
                ),
                true,
                0,
                5
            ),
            Arguments.of(
                List.of(
                    List.of(1),
                    List.of(0, 3, 2),
                    List.of(3, 2, 1, 0),
                    List.of(4, 1, 2, 3, 0),
                    List.of(1),
                    List.of(1, 2, 3)
                ),
                false,
                0,
                5
            )
        );
    }

    @ParameterizedTest
    @MethodSource("graphs")
    @DisplayName("Test that the multithreaded dns is working correctly and returned the expected search result")
    void testThatTheMultithreadedDnsIsWorkingCorrectlyAndReturnedTheExpectedSearchResult(
        List<List<Integer>> graph,
        boolean exceptedSearchResult,
        int start,
        int end
    ) {
        var actualResultSearchPath = dfs(graph, start, end);

        assertThat(actualResultSearchPath).isEqualTo(exceptedSearchResult);
    }

    private static boolean dfs(List<List<Integer>> graph, int v, int end) {
        try (ForkJoinPool fjp = new ForkJoinPool()) {
            var dfsAlgorithm = new RecursiveDFS(graph, v, end, new boolean[graph.size()]);

            return fjp.invoke(dfsAlgorithm);
        }
    }

}
