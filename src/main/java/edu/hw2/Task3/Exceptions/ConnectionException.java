package edu.hw2.Task3.Exceptions;

/**
 * Class with custom RuntimeException.
 */
public class ConnectionException extends RuntimeException {

    /**
     * Constrictor with standard error message.
     *
     * @param message error message.
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and cause exception.
     *
     * @param message error message.
     * @param e       cause exception.
     */
    public ConnectionException(String message, Exception e) {
        super(message, e);
    }

}
