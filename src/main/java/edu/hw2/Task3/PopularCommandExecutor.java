package edu.hw2.Task3;

import edu.hw2.Task3.Connections.Connection;
import edu.hw2.Task3.Exceptions.ConnectionException;
import edu.hw2.Task3.Managers.ConnectionManager;

/**
 * Command extraction class from the server.
 */
public class PopularCommandExecutor {
    private static final String EXCEED_CONNECTION_MESSAGE = "Exceeded the number of connection attempts";
    private final ConnectionManager connectionManager;
    private final int maxAttempts;

    /**
     * Class constructor.
     *
     * @param connectionManager default or faulty connection manager.
     * @param maxAttempts       number of attempts to establish a connection.
     */
    public PopularCommandExecutor(ConnectionManager connectionManager, int maxAttempts) {
        this.connectionManager = connectionManager;
        this.maxAttempts = maxAttempts;
        checkValidAttempts();
    }

    /**
     * Method that extracts data from the server by sending it a command.
     *
     * @throws ConnectionException if after several maxAttempts attempts it was not possible to
     *                             establish a connection and successfully send a command to the server,
     *                             throws an ConnectionException exception.
     */
    public void updatePackages() throws ConnectionException {
        tryExecute("apt update && apt upgrade -y");
    }

    /**
     * Method that executes the command.
     *
     * @param command command for server.
     */
    private void tryExecute(String command) {
        int attemptConnect = 0;

        try (Connection connection = connectionManager.getConnection()) {
            while (maxAttempts != attemptConnect) {
                try {
                    connection.execute(command);
                    break;
                } catch (ConnectionException e) {
                    attemptConnect += 1;
                    if (maxAttempts == attemptConnect) {
                        throw new ConnectionException(e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e) {
            throw new ConnectionException(EXCEED_CONNECTION_MESSAGE, e);
        }
    }

    private void checkValidAttempts() {
        if (this.maxAttempts <= 0) {
            throw new NumberFormatException("maxAttempts cannot be less than or equal to zero");
        }
    }
}
