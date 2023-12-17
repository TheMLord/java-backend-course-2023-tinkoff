package edu.hw10.task1.value_annorations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marking annotation for specifying the maximum boundary of the values of an argument of type int,
 * double and their object wrappers.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Max {
    int intValue() default Integer.MAX_VALUE;

    double doubleValue() default Double.MAX_VALUE;
}
