package edu.hw11;

import edu.hw11.task1.Task1;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class Task1Test {
    @Test
    @DisplayName(
        "Test that the class was successfully created via Buddy and returned the expected result of the toString method")
    void testThatTheClassWasSuccessfullyCreatedViaBuddyAndReturnedTheExpectedResultOfTheToStringMethod()
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var exceptedToStringResult = "Hello, ByteBuddy!";
        var buddyClass = Task1.createLoadedBuddyClass();

        var actualToStringResult = buddyClass.toString();

        assertThat(actualToStringResult).isEqualTo(exceptedToStringResult);
    }
}
