package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task7Test {
    @Test
    @DisplayName("Test the shift is smaller than the number size №1")
    void testTheShiftIsSmallerThanTheNumberSize1() {
        int n = 10;
        int shift = 1;

        int resultNRight = Task7.rotateRight(n, shift);
        int resultNLeft = Task7.rotateLeft(n, shift);

        assertThat(resultNRight).isEqualTo(5);
        assertThat(resultNLeft).isEqualTo(5);
    }

    @Test
    @DisplayName("Test the shift is smaller than the number size №2")
    void testTheShiftIsSmallerThanTheNumberSize2() {
        int n = 16;
        int shift = 3;

        int resultNRight = Task7.rotateRight(n, shift);
        int resultNLeft = Task7.rotateLeft(n, shift);

        assertThat(resultNRight).isEqualTo(2);
        assertThat(resultNLeft).isEqualTo(4);
    }

    @Test
    @DisplayName("Test negative number with small shift")
    void testNegativeNumberWithSmallShift() {
        int n = -16;
        int shift = 3;

        int resultNRight = Task7.rotateRight(n, shift);
        int resultNLeft = Task7.rotateLeft(n, shift);

        assertThat(resultNRight).isEqualTo(-2);
        assertThat(resultNLeft).isEqualTo(-4);
    }

    @Test
    @DisplayName("Test negative shift")
    void testNegativeShift() {
        int n = 445887;
        int shift = -5;

        assertThatThrownBy(() -> Task7.rotateRight(n, shift))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Shift can't be less than zero");

        assertThatThrownBy(() -> Task7.rotateLeft(n, shift))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Shift can't be less than zero");
    }

    @Test
    @DisplayName("Test the shift is larger than the number size №1")
    void testTheShiftIsLargerThanTheNumberSize() {
        int n = 8;
        int shift = 9;

        int resultNRight = Task7.rotateRight(n, shift);
        int resultNLeft = Task7.rotateLeft(n, shift);

        assertThat(resultNRight).isEqualTo(4);
        assertThat(resultNLeft).isEqualTo(1);
    }

    @Test
    @DisplayName("Test the shift is larger than the number size №2")
    void testTheShiftIsLargerThanTheNumberSize2() {
        int n = 47;
        int shift = 120;

        int resultNRight = Task7.rotateRight(n, shift);
        int resultNLeft = Task7.rotateLeft(n, shift);

        assertThat(resultNRight).isEqualTo(47);
        assertThat(resultNLeft).isEqualTo(47);
    }

    @Test
    @DisplayName("Test the shift is larger than the number size №3")
    void testTheShiftIsLargerThanTheNumberSize3() {
        int n = 888;
        int shift = 99;

        int resultNRight = Task7.rotateRight(n, shift);
        int resultNLeft = Task7.rotateLeft(n, shift);

        assertThat(resultNRight).isEqualTo(753);
        assertThat(resultNLeft).isEqualTo(444);
    }

}
