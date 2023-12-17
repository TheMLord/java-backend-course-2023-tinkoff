package edu.hw11.task2;

import edu.hw11.task2.utils.ArithmeticUtils;
import edu.hw11.task2.utils.OtherArithmeticUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import static net.bytebuddy.matcher.ElementMatchers.named;

public final class Task2 {
    private Task2() {

    }

    public static void reloadArithmeticUtils() {
        ByteBuddyAgent.install();
        try (var reloadClass = new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(named("sum"))
            .intercept(MethodDelegation.to(OtherArithmeticUtils.class))
            .make()) {

            reloadClass.load(
                ArithmeticUtils.class.getClassLoader(),
                ClassReloadingStrategy.fromInstalledAgent()
            );
        }
    }
}
