package edu.hw2.Task3.Managers;

import edu.hw2.Task3.Connections.Connection;
import edu.hw2.Task3.Connections.FaultyConnection;
import edu.hw2.Task3.Connections.StableConnection;
import java.util.Random;

/**
 * CLass default connection manager which returns a connection with a certain probability.
 */
public class DefaultConnectionManager implements ConnectionManager {
    private static final int PROBABLY_RETURN_FAULTY_CONNECTION = 2;
    private static final int BOUNDARY_RETURN = 11;
    private static final Random RANDOM = new Random();

    @Override
    public Connection getConnection() {
        if (RANDOM.nextInt(BOUNDARY_RETURN) <= PROBABLY_RETURN_FAULTY_CONNECTION) {
            return new FaultyConnection();
        }
        return new StableConnection();
    }
}
