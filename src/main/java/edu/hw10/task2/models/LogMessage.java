package edu.hw10.task2.models;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Models LogMessage with format: time | nameMethod | argsMethod | result | take in cache
 */
public record LogMessage(LocalDateTime dateTime, Method nameMethod, Object[] argsMethod, Object result,
                         boolean takeCache) {
    private static final String LOG_MESSAGE_SEPARATOR = " | ";

    @Override
    public String toString() {
        return dateTime + LOG_MESSAGE_SEPARATOR
            + nameMethod + LOG_MESSAGE_SEPARATOR
            + Arrays.toString(argsMethod) + LOG_MESSAGE_SEPARATOR
            + result + LOG_MESSAGE_SEPARATOR
            + takeCache + '\n';
    }
}
