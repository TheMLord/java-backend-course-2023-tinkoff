package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {
    @Test
    @DisplayName("Test numbers less than 1000")
    void testNumbersLessThan1000() {
        int n = 899;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(-1);
    }

    @Test
    @DisplayName("Test negative numbers less than 1000")
    void testNegativeNumbersLessThan1000() {
        int n = -900;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(-1);
    }

    @Test
    @DisplayName("Test number equal to 1000")
    void testNumberEqualTo1000() {
        int n = 1000;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(-1);
    }

    @Test
    @DisplayName("Test negative number equal to 1000")
    void testNegativeNumberEqualTo1000() {
        int n = -1000;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(-1);
    }

    @Test
    @DisplayName("Test five-digit number №1")
    void testFiveDigitNumber1() {
        int n = 10000;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(-1);
    }

    @Test
    @DisplayName("Test five-digit number №2")
    void testFiveDigitNumber2() {
        int n = 10897;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(-1);
    }

    @Test
    @DisplayName("Test more five-digit number")
    void testMoreFiveDigitNumber() {
        int n = 1770897;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(-1);
    }

    @Test
    @DisplayName("Test four-digit number №1")
    void testFourDigitNumber1() {
        int n = 6621;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(5);
    }

    @Test
    @DisplayName("Test four-digit number №2")
    void testFourDigitNumber2() {
        int n = 6554;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(4);
    }

    @Test
    @DisplayName("Test four-digit negative number №3")
    void testFourDigitNumber3() {
        int n = -1234;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(3);
    }

    @Test
    @DisplayName("Test number K")
    void testNumberK() {
        int n = 6174;

        int steps = Task6.k(n);

        assertThat(steps).isEqualTo(0);
    }

}
