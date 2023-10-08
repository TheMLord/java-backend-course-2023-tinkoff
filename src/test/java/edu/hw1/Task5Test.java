package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task5Test {
    @Test
    @DisplayName("True test with big number №1")
    void trueTestWithBigNumber1() {
        int n = 11211230;

        var resultIsPalindrome = Task5.isPalindromeDescendant(n);

        assertThat(resultIsPalindrome).isEqualTo(true);
    }

    @Test
    @DisplayName("True test with big number №2")
    void trueTestWithBigNumber2() {
        int n = 13001120;

        var resultIsPalindrome = Task5.isPalindromeDescendant(n);

        assertThat(resultIsPalindrome).isEqualTo(true);
    }

    @Test
    @DisplayName("True test with big number №3")
    void trueTestWithBigNumber3() {
        int n = 23336014;

        var resultIsPalindrome = Task5.isPalindromeDescendant(n);

        assertThat(resultIsPalindrome).isEqualTo(true);
    }

    @Test
    @DisplayName("Test with originally palindrome number")
    void testOriginallyPalindrome() {
        int n = 11;

        var resultIsPalindrome = Task5.isPalindromeDescendant(n);

        assertThat(resultIsPalindrome).isEqualTo(true);
    }

    @Test
    @DisplayName("Test number of length less than 2")
    void testNumbersOfLengthLessThan2() {
        int n = 9;

        var resultIsPalindrome = Task5.isPalindromeDescendant(n);

        assertThat(resultIsPalindrome).isEqualTo(false);
    }

    @Test
    @DisplayName("Test negative number of length less than 2")
    void testNegativeNumbersOfLengthLessThan2() {
        int n = -7;

        var resultIsPalindrome = Task5.isPalindromeDescendant(n);

        assertThat(resultIsPalindrome).isEqualTo(false);
    }

    @Test
    @DisplayName("True test with negative number")
    void trueTestWithNegativeNumber() {
        int n = -23336014;

        var resultIsPalindrome = Task5.isPalindromeDescendant(n);

        assertThat(resultIsPalindrome).isEqualTo(true);
    }

}
