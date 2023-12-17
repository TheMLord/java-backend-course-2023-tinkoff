package edu.hw10.task1.generators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marking annotation for methods of the Random Object Generator class and its heirs that generate objects.
 * <p>
 * The method that will perform the function of generating a random object must be marked with this annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ObjectGenerator {
}
