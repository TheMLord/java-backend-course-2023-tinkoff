package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Class word parser.
 */
public class WordDateParser extends DateParser {
    private static final Pattern RECENT_TIME_PATTERN = Pattern.compile("(yesterday|today|tomorrow)");
    private static final Pattern NEXT_PATTERN = Pattern.compile("next (day|week|month|year)");
    private static final Pattern AGO_PATTERN = Pattern.compile("\\d+ (day(s)?|week(s)?|month(s)?|year(s)?) ago");

    private static final String DAY = "day";
    private static final String DAYS = "days";
    private static final String WEEK = "week";
    private static final String WEEKS = "weeks";
    private static final String MONTH = "month";
    private static final String MONTHS = "months";
    private static final String YEAR = "year";
    private static final String YEARS = "years";
    private static final String YESTERDAY = "yesterday";
    private static final String TODAY = "today";
    private static final String TOMORROW = "tomorrow";

    public WordDateParser(DateParser dateParser) {
        super(dateParser);
    }

    @Override
    public Optional<LocalDate> getParseDate(String string) {
        var stringDate = string;
        LocalDate parseDate = null;

        if (RECENT_TIME_PATTERN.matcher(stringDate).matches()) {
            parseDate = parseRecentTime(stringDate);
        } else if (NEXT_PATTERN.matcher(stringDate).matches()) {
            parseDate = parseNextTime(stringDate);
        } else if (AGO_PATTERN.matcher(stringDate).matches()) {
            parseDate = parseAgoTime(stringDate);
        }

        if (parseDate != null) {
            return Optional.of(parseDate);
        }
        if (this.nextParser != null) {
            return nextParser.getParseDate(string);
        }
        return Optional.empty();
    }

    private LocalDate parseRecentTime(String dateString) {

        return switch (dateString) {
            case YESTERDAY -> LocalDate.now().minusDays(1);
            case TODAY -> LocalDate.now();
            case TOMORROW -> LocalDate.now().plusDays(1);
            default -> null;
        };

    }

    private LocalDate parseNextTime(String dateString) {
        var time = dateString.split(" ")[1];

        return switch (time) {
            case DAY -> LocalDate.now().plusDays(1);
            case WEEK -> LocalDate.now().plusWeeks(1);
            case MONTH -> LocalDate.now().plusMonths(1);
            case YEAR -> LocalDate.now().plusYears(1);

            default -> null;
        };
    }

    private LocalDate parseAgoTime(String dateString) {
        var dateStringArray = dateString.split(" ");

        int count = Integer.parseInt(dateStringArray[0]);
        var time = dateStringArray[1];

        return switch (time) {
            case DAY, DAYS -> LocalDate.now().plusDays(count);
            case WEEK, WEEKS -> LocalDate.now().plusWeeks(count);
            case MONTH, MONTHS -> LocalDate.now().plusMonths(count);
            case YEAR, YEARS -> LocalDate.now().plusYears(count);
            default -> null;
        };
    }
}
