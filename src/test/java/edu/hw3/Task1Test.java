package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class Task1Test {
    private static Stream<Arguments> testTask1Data() {
        return Stream.of(
            Arguments.of(
                "Hello world!",
                "Svool dliow!"
            ),
            Arguments.of(
                "The quick brown fox jumps over a lazy dog.",
                "Gsv jfrxp yildm ulc qfnkh levi z ozab wlt."
            ),
            Arguments.of(
                "Mary and John enJOy HIKING in!! the Rocky Mountains.",
                "Nzib zmw Qlsm vmQLb SRPRMT rm!! gsv Ilxpb Nlfmgzrmh."
            ),
            Arguments.of(
                "The big red apple fell***^&^^&# from the tree onto the green grass.",
                "Gsv yrt ivw zkkov uvoo***^&^^&# uiln gsv givv lmgl gsv tivvm tizhh."
            )
        );
    }

    @ParameterizedTest
    @MethodSource("testTask1Data")
    @DisplayName(
        "Test that the encoding in atbash was successful and the result returned coinciding with the correct answer")
    void testThatTheEncodingInAtbashWasSuccessfulAndTheResultReturnedCoincidingWithTheCorrectAnswer(
        String stringToEncode,
        String correctEncodeString
    ) {
        var encodeAtBashString = Task1.atBash(stringToEncode);

        assertThat(encodeAtBashString).isEqualTo(correctEncodeString);
    }

}
