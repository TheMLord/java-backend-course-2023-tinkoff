package edu.hw2;

import edu.hw2.Task3.Connections.Connection;
import edu.hw2.Task3.Connections.FaultyConnection;
import edu.hw2.Task3.Connections.StableConnection;
import edu.hw2.Task3.Exceptions.ConnectionException;
import edu.hw2.Task3.Managers.ConnectionManager;
import edu.hw2.Task3.Managers.DefaultConnectionManager;
import edu.hw2.Task3.Managers.FaultyConnectionManager;
import edu.hw2.Task3.PopularCommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Task3Test {
    private final ConnectionManager defaultConnectionManager = mock(DefaultConnectionManager.class);
    private final ConnectionManager faultyConnectionManager = mock(FaultyConnectionManager.class);
    private final Connection faultyConnection = mock(FaultyConnection.class);

    @Test
    @DisplayName("Test failed session with DefaultConnection with FaultyConnection")
    void testFailedSessionWithDefaultConnectionWithFaultyConnection() {
        when(defaultConnectionManager.getConnection()).thenReturn(faultyConnection);
        doThrow(new ConnectionException("connection was interrupted"))
            .when(faultyConnection)
            .execute(anyString());

        PopularCommandExecutor popularCommandExecutor = new PopularCommandExecutor(
            defaultConnectionManager,
            5
        );

        assertThatThrownBy(popularCommandExecutor::updatePackages)
            .isInstanceOf(ConnectionException.class)
            .hasMessage("Exceeded the number of connection attempts")
            .hasCauseInstanceOf(Exception.class)
            .cause().hasMessage("connection was interrupted");
    }

    @Test
    @DisplayName("Test successful session with DefaultConnection with FaultyConnection")
    void testSuccessfulSessionWithDefaultConnectionWithFaultyConnection() {
        when(defaultConnectionManager.getConnection()).thenReturn(faultyConnection);
        doNothing()
            .when(faultyConnection)
            .execute(anyString());

        PopularCommandExecutor popularCommandExecutor = new PopularCommandExecutor(
            defaultConnectionManager,
            8
        );

        assertDoesNotThrow(popularCommandExecutor::updatePackages);
    }

    @Test
    @DisplayName("Test invalid maxAttempts")
    void testInvalidMaxAttempts() {
        ConnectionManager connectionManager = new DefaultConnectionManager();
        int maxAttemptsZero = 0;
        int maxAttemptsNegative = -7;

        assertThatThrownBy(() -> new PopularCommandExecutor(connectionManager, maxAttemptsZero))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("maxAttempts cannot be less than or equal to zero");

        assertThatThrownBy(() -> new PopularCommandExecutor(connectionManager, maxAttemptsNegative))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("maxAttempts cannot be less than or equal to zero");
    }

    @Test
    @DisplayName("Test work stable connection with defaultConnectionManager")
    void testWorkStableConnectionWithDefaultConnectionManager() {
        Connection connection = new StableConnection();
        when(defaultConnectionManager.getConnection()).thenReturn(connection);

        PopularCommandExecutor popularCommandExecutor = new PopularCommandExecutor(
            defaultConnectionManager,
            9
        );

        assertDoesNotThrow(popularCommandExecutor::updatePackages);
    }

}
