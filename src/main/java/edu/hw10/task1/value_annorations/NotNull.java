package edu.hw10.task1.value_annorations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marking annotation indicating that the object cannot be null.
 * If the object does not have such an annotation, then it is guaranteed to be null
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NotNull {
}
