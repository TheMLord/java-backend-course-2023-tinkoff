package edu.hw8.Task1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    private final SocketChannel clientSocket;
    private ByteBuffer buffer;

    private static final int BUFFER_SIZE = 1024;

    /**
     * Class constructor.
     */
    private Client(String host, int port) throws IOException {
        clientSocket = SocketChannel.open(new InetSocketAddress(host, port));
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    /**
     * Method that sends a request from the client and receives a response from the server
     */
    public String sendMessage(String message) throws IOException {
        buffer = ByteBuffer.wrap(message.getBytes());
        clientSocket.write(buffer);
        buffer.clear();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bytesRead;

        do {
            bytesRead = clientSocket.read(buffer);
            buffer.flip();

            while (buffer.hasRemaining()) {
                byteArrayOutputStream.write(buffer.get());
            }

            buffer.clear();
        } while (bytesRead > 0);

        return byteArrayOutputStream.toString(StandardCharsets.UTF_8).trim();
    }

    /**
     * The factory method of creating a client specifying the server host and port.
     */
    public static Client createClient(String hostToConnect, int portToConnect) throws IOException {
        return new Client(hostToConnect, portToConnect);
    }

    /**
     * Method that closes the client socket.
     */
    public void closeClient() throws IOException {
        clientSocket.close();
        buffer = null;
    }

}
