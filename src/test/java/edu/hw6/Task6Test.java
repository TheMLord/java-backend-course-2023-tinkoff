package edu.hw6;

import edu.hw6.Task6.Task6;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class Task6Test {
    @Test
    @DisplayName("Test that the port scan is performed correctly and returns the correct states for known ports")
    void testThatThePortScanIsPerformedCorrectlyAndReturnsTheCorrectStatesForKnownPorts() {
        var expectedScanPortPrint = List.of(
            "Протокол\tПорт\tСервис",
            "tcp         1       TCP Port Service Multiplexer",
            "tcp         2       Management Utility",
            "tcp         3       Compression Process"
        );

        var actualScanPortPrint = getScanPortPrint(3);

        assertThat(actualScanPortPrint).isEqualTo(expectedScanPortPrint);
    }

    private static List<String> getScanPortPrint(int limit) {

        var baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        System.setOut(ps);

        Task6.scanPort(limit);

        try {
            ps.close();
            baos.close();
        } catch (IOException ignored) {
        }

        List<String> scanPortPrint = new ArrayList<>();
        String[] lines = baos.toString().split(System.lineSeparator());
        Collections.addAll(scanPortPrint, lines);

        return scanPortPrint;
    }

}
