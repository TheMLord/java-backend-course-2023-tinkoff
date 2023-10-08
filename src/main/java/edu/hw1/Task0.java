package edu.hw1;

import java.util.logging.Logger;

/**
 * Class with a static method that prints HI
 */
public final class Task0 {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(Task0.class));

    /**
     * Class constructor.
     */
    private Task0() {

    }

    /**
     * Methods prints HI via Logger
     */
    public static void printHelloWorld() {
        LOGGER.info("Привет, мир!");
    }

}
