package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Class task3.
 */
public final class Task3 {
    /**
     * Class constructor.
     */
    private Task3() {

    }

    /**
     * Method parses the date in various forms of representation. Works based on the Chain of
     * Responsibility Design template
     *
     * @param string date in any format:
     *               - standard format;
     *               - all variants dd/MM/uuuu or uuuu-MM-dd;
     *               - words format:
     *               - yesterday, today, tomorrow;
     *               - next day/week/mount/year;
     *               - n day(s) or week(s) or month(s) or year(s) ago;
     * @return the Optional LocalDate if it could recognize otherwise  Optional empty.
     */
    public static Optional<LocalDate> parseDate(String string) {

        DateParser wordParser = new WordDateParser(null);

        DateParser specificParser = new SpecificFormatDateParser(wordParser);

        DateParser standardParser = new StandardDateParser(specificParser);

        return standardParser.getParseDate(string);
    }

}
