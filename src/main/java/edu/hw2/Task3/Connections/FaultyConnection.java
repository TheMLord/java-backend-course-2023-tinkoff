package edu.hw2.Task3.Connections;

import edu.hw2.Task3.Exceptions.ConnectionException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Class Faulty connection.
 */
public class FaultyConnection implements Connection {
    private static final Logger LOGGER = Logger.getLogger(FaultyConnection.class.toString());

    /**
     * Hardcoded number starting from which the connection crashes with an error
     */
    private static final int PROBABILITY_FAILED_CONNECTION = 5;
    private static final int BOUNDARY_FAILED_CONNECTION = 11;
    private static final String ERROR_MESSAGE = "connection was interrupted";
    private static final Random RANDOM = new Random();

    @Override
    public void execute(String command) {
        if (RANDOM.nextInt(BOUNDARY_FAILED_CONNECTION) >= PROBABILITY_FAILED_CONNECTION) {
            throw new ConnectionException(ERROR_MESSAGE);
        } else {
            LOGGER.info(SUCCESSFUL_MESSAGE);
        }

    }

    @Override
    public void close() {
        LOGGER.info(CONNECTION_CLOSE_MESSAGE);
    }

}
