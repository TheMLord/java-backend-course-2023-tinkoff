package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Fix string test №1")
    void fixStringTest1() {
        var brokenString = "123456";

        var fixString = Task4.fixString(brokenString);

        assertThat(fixString).isEqualTo("214365");
    }

    @Test
    @DisplayName("Fix string test №2")
    void fixStringTest2() {
        var brokenString = "hTsii  s aimex dpus rtni.g";

        var fixString = Task4.fixString(brokenString);

        assertThat(fixString).isEqualTo("This is a mixed up string.");
    }

    @Test
    @DisplayName("Fix string test №3")
    void fixStringTest3() {
        var brokenString = "badce";

        var fixString = Task4.fixString(brokenString);

        assertThat(fixString).isEqualTo("abcde");
    }

    @Test
    @DisplayName("Fix string of length 1")
    void fixStringOfLength1() {
        var brokenString = "g";

        var fixString = Task4.fixString(brokenString);

        assertThat(fixString).isEqualTo("g");
    }

    @Test
    @DisplayName("Fix empty string")
    void fixEmptyString() {
        var brokenString = "";

        var fixString = Task4.fixString(brokenString);

        assertThat(fixString).isEqualTo("");
    }

    @Test
    @DisplayName("Fix odd length string")
    void fixOddLengthString() {
        var brokenString = "abgfr";

        var fixString = Task4.fixString(brokenString);

        assertThat(fixString).isEqualTo("bafgr");
    }

}
