package edu.hw11;

import edu.hw11.task2.Task2;
import edu.hw11.task2.utils.ArithmeticUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    @DisplayName(
        "Test that the proxy changes the behavior of Butt Buddy and returned the result corresponding to the delegated methodV2")
    void testThatTheProxyChangesTheBehaviorOfButtBuddyAndReturnedTheResultCorrespondingToTheDelegatedMethodV2() {
        int argA = 40;
        int argB = 5;
        int exceptedResultAfterChanges = 200;

        var au = new ArithmeticUtils();
        Task2.reloadArithmeticUtils();

        var actualResultAfterChanges = au.sum(argA, argB);
        assertThat(actualResultAfterChanges).isEqualTo(exceptedResultAfterChanges);

    }
// этот тест работает только локально из за ошибки на гитхабе
// UnsupportedOperation class redefinition failed: attempted to delete a method

//    @Test
//    @DisplayName(
//        "Test that the proxy changes the behavior of Butt Buddy and returned the result corresponding to the delegated method")
//    void testThatTheProxyChangesTheBehaviorOfButtBuddyAndReturnedTheResultCorrespondingToTheDelegatedMethod() {
//        int argA = 40;
//        int argB = 5;
//        int exceptedResultBeforeChanges = 45;
//        int exceptedResultAfterChanges = 200;
//
//        var au = new ArithmeticUtils();
//
//        var actualResultBeforeChanges = au.sum(argA, argB);
//        Task2.reloadArithmeticUtils();
//        var actualResultAfterChanges = au.sum(argA, argB);
//        assertThat(actualResultBeforeChanges).isEqualTo(exceptedResultBeforeChanges);
//        assertThat(actualResultAfterChanges).isEqualTo(exceptedResultAfterChanges);
//    }
}
