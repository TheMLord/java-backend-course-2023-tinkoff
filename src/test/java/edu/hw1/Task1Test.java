package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Correct input format string")
    void correctInputFormatString() {
        String correctFromatString = "44:12";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(2652);
    }

    @Test
    @DisplayName("Correct input format string with large value of minutes")
    void correctInputFormatStringWithLargeValueOfMinutes() {
        String correctFromatString = "8898:12";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(533892);
    }

    @Test
    @DisplayName("Correct input format string with space №1")
    void correctInputFormatStringWithSpace1() {
        String correctFromatString = "14: 54";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(-1);

    }

    @Test
    @DisplayName("Correct input format string with space №2")
    void correctInputFormatStringWithSpace2() {
        String correctFromatString = "1 4:54";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(-1);

    }

    @Test
    @DisplayName("Correct input format string with space №3")
    void correctInputFormatStringWithSpace3() {
        String correctFromatString = " 14:54";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(-1);

    }

    @Test
    @DisplayName("Correct input format string with space №4")
    void correctInputFormatStringWithSpace4() {
        String correctFromatString = "14:54 ";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(-1);

    }

    @Test
    @DisplayName("Incorrect input format string with wrong seconds")
    void incorrectInputFormatStringWithWrongSeconds() {
        String correctFromatString = "44:88";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(-1);
    }

    @Test
    @DisplayName("Empty String")
    void emptyString() {
        String correctFromatString = "";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(-1);
    }

    @Test
    @DisplayName("Null value")
    void nullValue() {
        String correctFromatString = null;

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(-1);
    }

    @Test
    @DisplayName("Incorrect input format string with minus value №1")
    void incorrectInputFormatStringWithMinusValue1() {
        var correctFromatString = "-44:88";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(-1);
    }

    @Test
    @DisplayName("Incorrect input format string with minus value №2")
    void incorrectInputFormatStringWithMinusValue2() {
        var correctFromatString = "44:-88";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(-1);
    }

    @Test
    @DisplayName("Incorrect input format string with string characters")
    void incorrectInputFormatStringWithStringCharacters() {
        var correctFromatString = "ff44:ads88";

        int minutesToSeconds = Task1.minutesToSeconds(correctFromatString);

        assertThat(minutesToSeconds).isEqualTo(-1);
    }

}
