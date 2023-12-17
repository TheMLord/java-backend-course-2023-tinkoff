package edu.hw10.task1.value_annorations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marking annotation for specifying the minimum boundary of the values of an argument of type int,
 * double and their object wrappers
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Min {
    int intValue() default Integer.MIN_VALUE;

    double doubleValue() default Double.MIN_VALUE;
}
