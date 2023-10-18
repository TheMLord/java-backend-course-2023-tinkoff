package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    @DisplayName("Correct test with standard value")
    void correctTestWithStandardValue() {
        int number = 894205;

        int countDigitsNumber = Task2.countDigits(number);

        assertThat(countDigitsNumber).isEqualTo(6);
    }

    @Test
    @DisplayName("Test number with insignificant zeros")
    void testNumberWithInsignificantZeros() {
        int number = 00000005;

        int countDigitsNumber = Task2.countDigits(number);

        assertThat(countDigitsNumber).isEqualTo(1);
    }

    @Test
    @DisplayName("Test number with negative numbers")
    void testNumberWithNegativeNumbers() {
        int number = -8887787;

        int countDigitsNumber = Task2.countDigits(number);

        assertThat(countDigitsNumber).isEqualTo(7);
    }

    @Test
    @DisplayName("Test with zero")
    void testWithZero() {
        int number = 0;

        int countDigitsNumber = Task2.countDigits(number);

        assertThat(countDigitsNumber).isEqualTo(1);
    }

}
