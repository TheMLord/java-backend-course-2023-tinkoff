package edu.project3.markups;

import java.util.List;

/**
 * Реализация вывода метрик по логам в виде ADOC.
 */
public final class ASCIIDoc extends MarkupsLanguage {
    private static final String ADOC_HEADER = "=== ";
    private static final String ADOC_TABLE_SEPARATOR = "|===";
    private static final String ADOC_TABLE_HEADER_VALUE_SEPARATOR = "|";

    private static final String SPACE = " ";

    @Override
    protected String prepareHeaderMetric(String header) {
        return ADOC_HEADER + header + System.lineSeparator();
    }

    @Override
    protected String prepareTableMetric(List<String> headersTable, List<List<String>> tableValues) {
        StringBuilder table = new StringBuilder();

        table.append(ADOC_TABLE_SEPARATOR).append(System.lineSeparator());
        for (var header : headersTable) {
            table.append(ADOC_TABLE_HEADER_VALUE_SEPARATOR).append(SPACE).append(header).append(SPACE);
        }
        table.append(System.lineSeparator());

        for (List<String> row : tableValues) {
            table.append(ADOC_TABLE_HEADER_VALUE_SEPARATOR).append(String.join(ADOC_TABLE_HEADER_VALUE_SEPARATOR, row))
                .append(System.lineSeparator());
        }

        table.append(ADOC_TABLE_SEPARATOR);
        table.append(System.lineSeparator());

        return table.toString();
    }
}
