package edu.hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class Task8Test {
    /**
     * Test data.
     */
    private static Stream<Arguments> task8TestData() {
        return Stream.of(
            Arguments.of(List.of(1, 2, 3), List.of(3, 2, 1)),
            Arguments.of(List.of("a", "c", "b"), List.of("b", "c", "a"))
        );
    }

    @ParameterizedTest
    @MethodSource("task8TestData")
    @DisplayName("Test that iterator returned the collection in reverse order")
    void testThatIteratorReturnedTheCollectionInReverseOrder(
        List<Object> inputCollection,
        List<Object> resultCollection
    ) {
        var iteratedCollection = getIteratedCollection(inputCollection);
        assertThat(iteratedCollection).isEqualTo(resultCollection);
    }

    /**
     * Method that performs collection processing by iterator.
     *
     * @param inputCollection collection type of T.
     * @param <T>             type collection.
     * @return iterated collection.
     */
    private static <T> List<T> getIteratedCollection(List<T> inputCollection) {
        var collectionIterator = new Task8<>(inputCollection);

        List<T> processedCollection = new ArrayList<>();
        while (collectionIterator.hasNext()) {
            processedCollection.add(collectionIterator.next());
        }
        return processedCollection;

    }

}
