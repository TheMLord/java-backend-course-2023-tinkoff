package edu.hw5.task3;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * CLass standard date parser.
 */
public class StandardDateParser extends DateParser {
    public StandardDateParser(DateParser nextParser) {
        super(nextParser);
    }

    @Override
    public Optional<LocalDate> getParseDate(String string) {
        try {
            var date = LocalDate.parse(string);
            return Optional.of(date);
        } catch (DateTimeException e) {
            if (this.nextParser != null) {
                return nextParser.getParseDate(string);
            }
            return Optional.empty();
        }
    }
}
