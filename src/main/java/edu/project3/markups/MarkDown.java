package edu.project3.markups;

import java.util.List;

public final class MarkDown extends MarkupsLanguage {
    private static final String MD_HEADER = "### ";
    private static final String MD_TABLE_SEPARATOR = "|";
    private static final String MD_TABLE_HEADER_VALUE_SEPARATOR = "-";

    @Override
    protected String prepareHeaderMetric(String header) {
        return MD_HEADER + header + System.lineSeparator();
    }

    @Override
    protected String prepareTableMetric(List<String> headersTable, List<List<String>> tableValues) {
        var table = new StringBuilder();
        int maxLengthWord = getMaxLengthWord(headersTable, tableValues);

        table.append(String.join(MD_TABLE_SEPARATOR, headersTable))
            .append(System.lineSeparator());

        for (String header : headersTable) {
            table.append(MD_TABLE_HEADER_VALUE_SEPARATOR.repeat(maxLengthWord))
                .append(MD_TABLE_SEPARATOR);
        }
        table.append(System.lineSeparator());

        for (List<String> row : tableValues) {
            table.append(String.join(MD_TABLE_SEPARATOR, row))
                .append(System.lineSeparator());
        }

        return table.toString();
    }

    private int getMaxLengthWord(List<String> headers, List<List<String>> tableValues) {
        int maxLength = 0;

        for (var header : headers) {
            maxLength = Math.max(maxLength, header.length());
        }

        for (var lineValues : tableValues) {
            for (var value : lineValues) {
                maxLength = Math.max(maxLength, value.length());
            }
        }

        return maxLength;
    }

}
