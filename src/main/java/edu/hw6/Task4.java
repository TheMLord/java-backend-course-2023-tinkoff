package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public final class Task4 {
    private static final Logger TASK4_LOGGER = Logger.getLogger(Task4.class.getName());

    private Task4() {

    }

    /**
     * Writing to a file via composite OutputStream attachments.
     *
     * @param filePath the path to the file where the text will be written.
     * @param text     the text that will be written to the file.
     */
    public static void writeToFile(Path filePath, String text) {
        try (var pw = new OutputStreamWriter(
            new BufferedOutputStream(
                new CheckedOutputStream(
                    Files.newOutputStream(filePath), new Adler32())
            ), StandardCharsets.UTF_8)) {
            pw.write(text);
        } catch (IOException ex) {
            TASK4_LOGGER.info(ex.getMessage());
        }
    }
}
