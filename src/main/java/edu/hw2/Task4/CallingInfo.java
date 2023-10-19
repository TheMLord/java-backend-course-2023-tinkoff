package edu.hw2.Task4;

/**
 * Model with call data from the stack
 *
 * @param className  called class name.
 * @param methodName called class method.
 */
public record CallingInfo(String className, String methodName) {
}
