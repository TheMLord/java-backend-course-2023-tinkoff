package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class Task4Test {
    private static Stream<Arguments> validPasswords() {
        return Stream.of(
            Arguments.of("1!a$2@b"),
            Arguments.of("3~4%^c"),
            Arguments.of("d5&6|e"),
            Arguments.of("!7f8g$h"),
            Arguments.of("9*i|j"),
            Arguments.of("k#l0~m"),
            Arguments.of("$1n2o3^p"),
            Arguments.of("4q5r@s"),
            Arguments.of("t&6u7v"),
            Arguments.of("8~9w#x"),
            Arguments.of("y$0z^"),
            Arguments.of("A!B#C*D"),
            Arguments.of("E&F$G|"),
            Arguments.of("H*I~J9"),
            Arguments.of("K!L%M#"),
            Arguments.of("N^O&1P"),
            Arguments.of("Q2R$S|"),
            Arguments.of("3T!U@V"),
            Arguments.of("W4X&Y"),
            Arguments.of("5Z6|7"),
            Arguments.of("a8~b9@c")
        );
    }

    private static Stream<Arguments> invalidPasswords() {
        return Stream.of(
            Arguments.of("11111b"),
            Arguments.of("sdfdsjjJJjhfd7778888wjjjF"),
            Arguments.of("a"),
            Arguments.of("")
        );
    }

    @ParameterizedTest
    @MethodSource("validPasswords")
    @DisplayName("Test that the method processes passwords correctly and returned true for valid passwords")
    void testThatTheMethodProcessesPasswordsCorrectlyAndReturnedTrueForValidPasswords(String password) {
        var isValid = Task4.isValidPassword(password);

        assertThat(isValid).isTrue();
    }

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    @DisplayName("Test that the method processes passwords correctly and returned false for invalid passwords")
    void testThatTheMethodProcessesPasswordsCorrectlyAndReturnedFalseForInvalidPasswords(String password) {
        var isValid = Task4.isValidPassword(password);

        assertThat(isValid).isFalse();
    }

}
