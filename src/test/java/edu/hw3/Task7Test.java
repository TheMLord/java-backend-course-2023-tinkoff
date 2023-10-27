package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;

class Task7Test {
    @Test
    @DisplayName(
        "Test that comparator processes a TreeMap with a null key and returned true for the presence of such a key")
    void testThatComparatorProcessesATreeMapWithANullKeyAndReturnedTrueForThePresenceOfSuchAKey() {
        var nullTreeMapComparator = new Task7();
        TreeMap<String, String> tree = new TreeMap<>(nullTreeMapComparator) {{
            put(null, "test");
            put("one", "test");
        }};

        assertThat(tree.containsKey(null)).isTrue();
        assertThat(tree.containsKey("two")).isFalse();
        assertThat(tree.containsKey("one")).isTrue();
    }

}
