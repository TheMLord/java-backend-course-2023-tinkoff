package edu.hw2.Task3.Connections;

/**
 * Contract for connections.
 */
public interface Connection extends AutoCloseable {
    /**
     * close message.
     */
    String CONNECTION_CLOSE_MESSAGE = "Connection closed";
    String SUCCESSFUL_MESSAGE = "Server has received the message";

    /**
     * Method of extracting a command from the server.
     *
     * @param command command that is sent to the server
     */
    void execute(String command);
}

