package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    @Test
    @DisplayName("True test №1")
    void trueTest1() {
        var a1 = new int[] {1, 2, 3, 4};
        var a2 = new int[] {0, 6};

        var resultIsNestable = Task3.isNestable(a1, a2);

        assertThat(resultIsNestable).isEqualTo(true);
    }

    @Test
    @DisplayName("True test №2")
    void trueTest2() {
        var a1 = new int[] {3, 1};
        var a2 = new int[] {4, 0};

        var resultIsNestable = Task3.isNestable(a1, a2);

        assertThat(resultIsNestable).isEqualTo(true);
    }

    @Test
    @DisplayName("False test №1")
    void falseTest1() {
        var a1 = new int[] {9, 9, 8};
        var a2 = new int[] {8, 9};

        var resultIsNestable = Task3.isNestable(a1, a2);

        assertThat(resultIsNestable).isEqualTo(false);
    }

    @Test
    @DisplayName("False test №2")
    void falseTest2() {
        var a1 = new int[] {1, 2, 3, 4};
        var a2 = new int[] {2, 3};

        var resultIsNestable = Task3.isNestable(a1, a2);

        assertThat(resultIsNestable).isEqualTo(false);
    }

}
