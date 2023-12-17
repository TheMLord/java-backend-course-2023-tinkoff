package edu.hw11.task2.utils;

import net.bytebuddy.implementation.bind.annotation.AllArguments;

public final class OtherArithmeticUtils {
    private OtherArithmeticUtils() {
    }

    public static int multiply(@AllArguments Object[] args) {
        return ((int) args[0]) * ((int) args[1]);
    }
}
