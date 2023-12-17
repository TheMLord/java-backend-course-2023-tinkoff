package edu.hw11.task1;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public final class Task1 {
    private static final String CLASS_NAME = "HelloByteBuddy";
    private static final String TO_STRING_PHRASE = "Hello, ByteBuddy!";

    private Task1() {

    }

    public static Object createLoadedBuddyClass()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try (var byteBuddyClass = new ByteBuddy()
            .subclass(Object.class)
            .name(CLASS_NAME)
            .method(ElementMatchers.isToString())
            .intercept(FixedValue.value(TO_STRING_PHRASE))
            .make()) {

            return byteBuddyClass.load(
                    Main.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded().getDeclaredConstructor().newInstance();
        }
    }
}