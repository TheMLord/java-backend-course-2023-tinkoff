package edu.hw10.task2.models;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * Model of the method being called.
 *
 * @param method called method.
 * @param args   method arguments.
 */
public record MethodCache(Method method, Object[] args) {

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MethodCache that = (MethodCache) o;
        return Objects.equals(method, that.method) && Arrays.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(method);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
