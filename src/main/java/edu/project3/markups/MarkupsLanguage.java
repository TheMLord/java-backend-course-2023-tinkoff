package edu.project3.markups;

import edu.project3.models.LogReport;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Общий контракт форматов вывода.
 */
public abstract class MarkupsLanguage {
    private static final Logger MARKUP_LOGGER = Logger.getLogger(MarkupsLanguage.class.getName());
    private static final String NEW_LINE = "\n\n";

    /**
     * Метод печати метрик в соответствующем формате в файл для сохранения.
     *
     * @param pathToSave путь до файла, куда записываются метрики по логам.
     * @param logReport  модель метрики.
     */
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

    /**
     * Метод преобразования заголовка к синтаксису формата вывода
     *
     * @param header заголовок
     */
    protected abstract String prepareHeaderMetric(String header);

    /**
     * Метод преобразования таблицы к синтаксису формата вывода
     *
     * @param headersTable заголовки таблицы
     * @param tableValues  значения в таблице
     */
    protected abstract String prepareTableMetric(List<String> headersTable, List<List<String>> tableValues);

}
