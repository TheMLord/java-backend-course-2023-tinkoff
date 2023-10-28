package edu.hw3;

import edu.hw3.task5.SortOrder;
import edu.hw3.task5.Task5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class Task5Test {
    private static Stream<Arguments> contactDataTask5Test() {
        return Stream.of(
            Arguments.of(
                new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"},
                SortOrder.ASC,
                new String[] {"Thomas Aquinas", "Rene Descartes", "David Hume", "John Locke"}
            ),
            Arguments.of(
                new String[] {"Paul Erdos", "Leonhard Euler", "Carl Gauss"},
                SortOrder.DESC,
                new String[] {"Carl Gauss", "Leonhard Euler", "Paul Erdos"}
            ),
            Arguments.of(
                new String[] {},
                SortOrder.ASC,
                new String[] {}
            ),
            Arguments.of(
                new String[] {"Paul", "Leonhard Euler", "Carl Gauss"},
                SortOrder.DESC,
                new String[] {"Paul", "Carl Gauss", "Leonhard Euler"}
            ),
            Arguments.of(
                new String[] {"Paul", "Leonhard Euler", "Carl Gauss"},
                SortOrder.ASC,
                new String[] {"Leonhard Euler", "Carl Gauss", "Paul"}
            ),
            Arguments.of(
                new String[] {"John Smith", "Emily Johnson", "David Brown", "Olivia Davis", "Michael Wilson",
                    "Sophia Martinez", "William Anderson", "George White"},
                SortOrder.DESC,
                new String[] {"Michael Wilson", "George White", "John Smith", "Sophia Martinez",
                    "Emily Johnson", "Olivia Davis", "David Brown", "William Anderson"}
            ),
            Arguments.of(
                new String[] {"Ab Johnson", "Ab Johnson", "Carl"},
                SortOrder.ASC,
                new String[] {"Carl", "Ab Johnson", "Ab Johnson"}
            )
        );
    }

    @ParameterizedTest
    @MethodSource("contactDataTask5Test")
    @DisplayName(
        "Test that the contacts are sorted in the specified order and the list of object contacts is returned in the desired order")
    void testThatTheContactsAreSortedInTheSpecifiedOrderAndTheListOfObjectContactsIsReturnedInTheDesiredOrder(
        String[] namesArray,
        SortOrder order,
        String[] correctSortedContactBook
    ) {
        var sortedContactList = Task5.parseContacts(namesArray, order);

        assertThat(sortedContactList.size()).isEqualTo(sortedContactList.size());

        for (int i = 0; i < correctSortedContactBook.length; i++) {
            assertThat(correctSortedContactBook[i]).isEqualTo(sortedContactList.get(i).toString());
        }
    }

    @Test
    @DisplayName("Test that passed null for processing the method returned null")
    void testThatPassedNullForProcessingTheMethodReturnedNull() {
        var sortedContactList1 = Task5.parseContacts(null, SortOrder.ASC);
        var sortedContactList2 = Task5.parseContacts(null, SortOrder.DESC);

        assertThat(sortedContactList1).isNull();
        assertThat(sortedContactList2).isNull();

    }

}
