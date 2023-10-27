package edu.hw2.Task3.Managers;

import edu.hw2.Task3.Connections.Connection;

/**
 * Contract managers connection
 */
public interface ConnectionManager {
    /**
     * Method that returns the connection.
     *
     * @return stable or faulty connection.
     */
    Connection getConnection();
}
