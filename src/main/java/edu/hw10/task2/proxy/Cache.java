package edu.hw10.task2.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marking annotation for the Cache Proxy.
 * <p>
 * If the method is marked with the Cache annotation,
 * then the call to this method with arguments will be saved in the temporary cache,
 * regardless of specifying the persist parameter
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    /**
     * Parameter that specifies whether to save the logger's message about the cache to a file
     */
    boolean persist() default false;
}
