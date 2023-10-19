package edu.hw2.Task3.Connections;

import java.util.logging.Logger;

/**
 * Class stable connection.
 */
public class StableConnection implements Connection {
    private static final Logger LOGGER = Logger.getLogger(StableConnection.class.toString());

    @Override
    public void execute(String command) {
        LOGGER.info(SUCCESSFUL_MESSAGE);
    }

    @Override
    public void close() {
        LOGGER.info(CONNECTION_CLOSE_MESSAGE);
    }
}
