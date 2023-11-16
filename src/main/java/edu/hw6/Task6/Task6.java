package edu.hw6.Task6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class Task6 {
    private static final Logger TASK6_LOGGER = Logger.getLogger(Task6.class.getName());

    private static final String INDENTATION = "\t";

    private static final String PROTOCOL_HEADER = "Протокол";
    private static final String PORT_HEADER = "Порт";
    private static final String SERVICE_HEADER = "Сервис";

    private static final String IS_FREE_MESSAGE = "Порт свободен";
    private static final String IS_NOT_FREE_MESSAGE = "Порт занят неизвестной службой";
    private static final String EMPTY_PROTOCOL = "";

    private static final String PATH_TO_KNOWN_PORT_MAP_FILE = "src/main/java/edu/hw6/Task6/knownPort.txt";
    private static final Map<Integer, List<String>> KNOWN_PORT_MAP = preparePortMap();
    private static final String KEY_VALUE_PORT_FILE_SEPARATOR = ":";
    private static final String VALUES_PORT_FILE_SEPARATOR = ",";

    private static final String PRINT_FORMAT = "%-12s%-8s%s";

    private static final int KEY_INDEX = 0;
    private static final int VALUES_INDEX = 1;
    private static final int PROTOCOL_INFO_VALUE_INDEX = 0;
    private static final int SERVICE_INFO_VALUE_INDEX = 1;

    private static final int START_PORT = 1;

    private Task6() {

    }

    /**
     * Method that scans ports up to a specified boundary. Determines whether the port is open or closed.
     * If the port is closed, the service that runs on it is returned, if such information is present in the database
     *
     * @param limitPort the upper limit to which the ports are scanned.
     */
    public static void scanPort(int limitPort) {
        try (PrintWriter pw = new PrintWriter(System.out)) {
            pw.println(PROTOCOL_HEADER + INDENTATION + PORT_HEADER + INDENTATION + SERVICE_HEADER);
            for (int port = START_PORT; port <= limitPort; port++) {
                try (ServerSocket serverSocket = new ServerSocket(port)) {
                    pw.println(preparePrintMessage(
                        EMPTY_PROTOCOL,
                        port,
                        IS_FREE_MESSAGE
                    ));
                } catch (IOException e) {
                    try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
                        pw.println(preparePrintMessage(
                            EMPTY_PROTOCOL,
                            port,
                            IS_FREE_MESSAGE
                        ));
                    } catch (SocketException ex) {
                        if (KNOWN_PORT_MAP.containsKey(port)) {
                            pw.println(preparePrintMessage(
                                KNOWN_PORT_MAP.get(port).get(0),
                                port,
                                KNOWN_PORT_MAP.get(port).get(1)
                            ));
                        } else {
                            pw.println(preparePrintMessage(
                                EMPTY_PROTOCOL,
                                port,
                                IS_NOT_FREE_MESSAGE
                            ));
                        }
                    }
                }
            }
        }
    }

    /**
     * Method of preparing the next line for printing in a readable form.
     */
    private static String preparePrintMessage(String protocol, int port, String service) {
        return String.format(
            PRINT_FORMAT,
            protocol,
            port,
            service
        );
    }

    /**
     * Loads data about ports and services running on them from a file.
     */
    private static Map<Integer, List<String>> preparePortMap() {
        Map<Integer, List<String>> portMap = new HashMap<>();

        Path portFilePath =
            Paths.get(
                String.valueOf(Paths.get("").toAbsolutePath()),
                PATH_TO_KNOWN_PORT_MAP_FILE
            );

        try {
            var lines = Files.readAllLines(portFilePath);
            for (var line : lines) {
                var keyValue = line.strip().split(KEY_VALUE_PORT_FILE_SEPARATOR);
                var values = keyValue[VALUES_INDEX].split(VALUES_PORT_FILE_SEPARATOR);

                portMap.put(
                    Integer.parseInt(keyValue[KEY_INDEX]),
                    List.of(values[PROTOCOL_INFO_VALUE_INDEX], values[SERVICE_INFO_VALUE_INDEX])
                );
            }
            return portMap;
        } catch (IOException e) {
            TASK6_LOGGER.info(e.getMessage());
            return portMap;
        }
    }
}
