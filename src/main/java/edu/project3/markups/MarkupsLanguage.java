package edu.project3.markups;

import edu.project3.models.LogReport;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public abstract class MarkupsLanguage {
    private static final Logger MARKUP_LOGGER = Logger.getLogger(MarkupsLanguage.class.getName());
    private static final String NEW_LINE = "\n\n";

    public void printLogMetric(Path pathToSave, LogReport logReport) {
        var logMetric = new StringBuilder();

        var header = prepareHeaderMetric(logReport.metricHeader());
        var table = prepareTableMetric(logReport.tableHeaders(), logReport.tableValues());

        logMetric.append(header).append(NEW_LINE).append(table).append(NEW_LINE);

        try {
            Files.write(
                pathToSave,
                logMetric.toString().getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            MARKUP_LOGGER.info(e.getMessage());
            MARKUP_LOGGER.info(Arrays.toString(e.getStackTrace()));
        }
    }

    protected abstract String prepareHeaderMetric(String header);

    protected abstract String prepareTableMetric(List<String> headersTable, List<List<String>> tableValues);

}
