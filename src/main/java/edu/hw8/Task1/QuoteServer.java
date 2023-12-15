package edu.hw8.Task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Server quote class.
 */
public final class QuoteServer {
    private static final Logger SERVER_LOGGER = Logger.getLogger(QuoteServer.class.getName());

    private final Lock lock = new ReentrantLock(true);

    private static final int MAX_CONNECTION = 5;

    private static final String DEFAULT_SERVER_HOST = "localhost";
    private static final int DEFAULT_SERVER_PORT = 10101;
    private final String serverHost;
    private final int serverPort;

    private static final int BYTE_BUFFER_SIZE = 1024;
    private static final int EMPTY_FLAG_BUFFER = -1;

    private static final Map<String, String> QUOTE_MAP = new HashMap<>() {
        {
            put("личности", "Не переходи на личности там, где их нет");
            put(
                "оскорбления",
                "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами"
            );
            put(
                "глупый",
                "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."
            );
            put("интеллект", "Чем ниже интеллект, тем громче оскорбления");
        }
    };

    private static final String DEFAULT_QUOTE =
        "Поскольку мои-то программы – ясное дело – всегда идеальны, я понял, что тут дело в другом. "
            + "Пришлось пойти дальше и дизассемблировать операционную систему.";
    private static final String EMPTY_CLIENT_MESSAGE = "";

    /**
     * Class constructor.
     */
    private QuoteServer(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    /**
     * Factory method for creating a quote server.
     *
     * @param serverHost host
     * @param serverPort port
     */
    public static QuoteServer buildQuoteServer(String serverHost, int serverPort) throws IOException {
        return new QuoteServer(serverHost, serverPort);
    }

    /**
     * Factory method for creating a quote server.
     */
    public static QuoteServer buildQuoteServer() throws IOException {
        return new QuoteServer(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);
    }

    /**
     * The method that starts the server operation.
     */
    public void startServer() throws IOException {
        try (var selector = Selector.open()) {
            try (var serverSocket = ServerSocketChannel.open()) {

                configureServerSocket(serverSocket, selector);
                processConnection(selector, serverSocket);

            } catch (IOException e) {
                SERVER_LOGGER.info("Ошибка настройки конфигурации " + e.getMessage());
                throw new IOException(e);
            }
        }
    }

    /**
     * Method configuration server socket and selector.
     */
    private void configureServerSocket(ServerSocketChannel serverSocket, Selector selector) throws IOException {
        serverSocket.configureBlocking(false);
        serverSocket.bind(new InetSocketAddress(this.serverHost, this.serverPort));
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * Method that accepts keys from a selector
     */
    private void processConnection(Selector selector, ServerSocketChannel serverSocket) {
        try (var executorService = Executors.newFixedThreadPool(MAX_CONNECTION)) {
            while (true) {
                try {
                    int count = selector.select();
                    if (count == 0) {
                        continue;
                    }

                    var keySet = selector.selectedKeys();
                    var iterator = keySet.iterator();
                    while (iterator.hasNext()) {
                        var selectionKey = (SelectionKey) iterator.next();
                        iterator.remove();

                        checkKey(selectionKey, serverSocket, selector, executorService);
                    }
                } catch (IOException e) {
                    SERVER_LOGGER.info("Ошибка обработке ключа " + e.getMessage());
                }
            }
        }
    }

    /**
     * Method that checks the state of the SelectionKey.
     * The SelectionKey will either be accepted or sent for processing to respond to the user
     */
    private void checkKey(
        SelectionKey selectionKey,
        ServerSocketChannel serverSocket,
        Selector selector,
        ExecutorService executorService
    )
        throws IOException {
        if (selectionKey.isValid()) {
            if (selectionKey.isAcceptable()) {
                registerConnection(serverSocket, selector);
            } else {
                executorService.submit(() -> {
                    try {
                        processingKey(selectionKey);
                    } catch (IOException e) {
                        SERVER_LOGGER.info("ошибка обработки ключа " + e.getMessage());
                    }
                });
            }
        }
    }

    /**
     * The method of processing a ready-to-read key.
     */
    private void processingKey(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isReadable() && selectionKey.isValid()) {
            lock.lock();
            try {
                if (selectionKey.isReadable() && selectionKey.isValid()) {
                    var client = (SocketChannel) selectionKey.channel();
                    var clientMessage = readKey(client);
                    answerClient(client, clientMessage);
                }
            } finally {
                lock.unlock();
            }
            selectionKey.cancel();
        }
    }

    /**
     * Method registration key.
     */
    private void registerConnection(ServerSocketChannel serverSocket, Selector selector) throws IOException {
        try {
            var clientSocket = serverSocket.accept();
            clientSocket.configureBlocking(false);
            clientSocket.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            SERVER_LOGGER.info("accept error " + e.getMessage());
            throw new IOException(e);
        } catch (CancelledKeyException e) {
            SERVER_LOGGER.info("попытка зарегистрировать отработанный ключ " + e.getMessage());
        }
    }

    /**
     * Method that reads the client's request.
     */
    private String readKey(SocketChannel client) throws IOException {
        ByteBuffer readBuffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE);

        int readBytes = client.read(readBuffer);

        if (readBytes == EMPTY_FLAG_BUFFER) {
            readBuffer.clear();
            return EMPTY_CLIENT_MESSAGE;
        }

        readBuffer.flip();
        byte[] bytes = new byte[readBuffer.remaining()];
        readBuffer.get(bytes);
        readBuffer.clear();

        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Method that reads the client's request
     */
    private void answerClient(SocketChannel client, String message) throws IOException {
        var answer = getQuote(message.trim().toLowerCase());
        ByteBuffer writeBuffer = ByteBuffer.wrap(answer.getBytes());

        client.write(writeBuffer);
        writeBuffer.clear();
        client.close();
    }

    /**
     * Method that returns a quote at the request of the client.
     *
     * @param message client request.
     * @return a quote with the word in the client's message, if there is one, or a default message otherwise.
     */
    private String getQuote(String message) {
        if (QUOTE_MAP.containsKey(message)) {
            return QUOTE_MAP.get(message);
        }
        return DEFAULT_QUOTE;
    }
}
