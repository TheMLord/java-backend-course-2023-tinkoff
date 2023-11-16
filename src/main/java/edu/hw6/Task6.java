package edu.hw6;

public final class Task6 {
    private static final String INDENTATION_RECORDS = "\t\t";
    private static final String INDENTATION_HEADER = "\t";
    private static final String LOCALHOST = "localhost";

    private static final String PROTOCOL_STRING = "Протокол";
    private static final String PORT_STRING = "Порт";
    private static final String SERVICE_STRING = "Сервис";
    private static final String IS_FREE = "порт свободен";
    private static final String TCP_STRING = "TCP";
    private static final String UDP_STRING = "UDP";

    private static final int START_PORT = 1;
    private static final int END_PORT = 49900;

    private Task6() {

    }

    public static void scanPort() {
//        try (PrintWriter pw = new PrintWriter(System.out)) {
//            pw.println(PROTOCOL_STRING + INDENTATION_HEADER + PORT_STRING + INDENTATION_HEADER + SERVICE_STRING);
//            for (int port = START_PORT; port <= END_PORT; port++) {
//                try (ServerSocket serverSocket = new ServerSocket(port)) {
//                } catch (IOException e) {
//                }
//                try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
//
//                } catch (SocketException e) {
//                }
//                try (Socket socket = new Socket(LOCALHOST, port)) {
//                    System.out.println(socket.getLocalAddress().getHostName());
//                } catch (IOException e) {
//                }
//            }
//        }

    }

    public static void scanPort(int limit) {

    }
}
