package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Class date parser.
 */
public abstract class DateParser {
    DateParser nextParser;

    /**
     * Date parser constructor.
     *
     * @param dateParser next parser for current.
     */
    public DateParser(DateParser dateParser) {
        this.nextParser = dateParser;
    }

    /**
     * Method that parses the entered date
     *
     * @param string date in various forms.
     * @return the Optional LocalDate if it could recognize otherwise  Optional empty.
     */
    public abstract Optional<LocalDate> getParseDate(String string);
}
