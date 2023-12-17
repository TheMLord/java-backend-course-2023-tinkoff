package edu.hw11;

import edu.hw11.task3.FibonacciBytecode;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    private static Method fibMethod;

    @BeforeAll
    public static void setFibMethod() throws NoSuchMethodException {
        fibMethod = FibonacciBytecode.createClassFibonacci().getDeclaredMethod("fib", int.class);
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
            Arguments.of(0, 0),
            Arguments.of(1, 1),
            Arguments.of(2, 1),
            Arguments.of(3, 2),
            Arguments.of(4, 3),
            Arguments.of(5, 5),
            Arguments.of(6, 8),
            Arguments.of(7, 13),
            Arguments.of(8, 21),
            Arguments.of(9, 34),
            Arguments.of(10, 55),
            Arguments.of(11, 89),
            Arguments.of(12, 144),
            Arguments.of(13, 233),
            Arguments.of(14, 377),
            Arguments.of(15, 610)
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    @DisplayName(
        "Test that the class built through ByteBuddy and ByteCodeAppender with the method of calculating the fibonacci number works correctly and returned the correct fibonacci number")
    void testThatTheClassBuiltThroughByteBuddyAndByteCodeAppenderWithTheMethodOfCalculatingTheFibonacciNumberWorksCorrectlyAndReturnedTheCorrectFibonacciNumber(
        int n,
        long exceptedFibNumber
    )
        throws InvocationTargetException, IllegalAccessException {
        long actualFibNumber = (long) fibMethod.invoke(null, n);

        assertThat(actualFibNumber).isEqualTo(exceptedFibNumber);
    }

}
