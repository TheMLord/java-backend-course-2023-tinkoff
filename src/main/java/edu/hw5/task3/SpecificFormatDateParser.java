package edu.hw5.task3;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Class of specific date formats in pattern dd/MM/uuuu or dd-MM-uuuu
 */
public class SpecificFormatDateParser extends DateParser {
    private static final DateTimeFormatter DATE_FORMAT_SLASH_DD_MM_UUUU = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private static final DateTimeFormatter DATE_FORMAT_SLASH_DD_MM_UU = DateTimeFormatter.ofPattern("dd/MM/uu");
    private static final DateTimeFormatter DATE_FORMAT_SLASH_DD_M_UUUU = DateTimeFormatter.ofPattern("dd/M/uuuu");
    private static final DateTimeFormatter DATE_FORMAT_SLASH_DD_M_UU = DateTimeFormatter.ofPattern("dd/M/uu");
    private static final DateTimeFormatter DATE_FORMAT_SLASH_D_MM_UUUU = DateTimeFormatter.ofPattern("d/MM/uuuu");
    private static final DateTimeFormatter DATE_FORMAT_SLASH_D_MM_UU = DateTimeFormatter.ofPattern("d/MM/uu");
    private static final DateTimeFormatter DATE_FORMAT_SLASH_D_M_UUUU = DateTimeFormatter.ofPattern("d/M/uuuu");
    private static final DateTimeFormatter DATE_FORMAT_SLASH_D_M_UU = DateTimeFormatter.ofPattern("d/M/uu");

    private static final DateTimeFormatter DATE_FORMAT_DASH_UUUU_MM_DD = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    private static final DateTimeFormatter DATE_FORMAT_DASH_UUUU_M_DD = DateTimeFormatter.ofPattern("uuuu-M-dd");
    private static final DateTimeFormatter DATE_FORMAT_DASH_UU_M_DD = DateTimeFormatter.ofPattern("uu-M-dd");
    private static final DateTimeFormatter DATE_FORMAT_DASH_UUUU_MM_D = DateTimeFormatter.ofPattern("uuuu-MM-d");
    private static final DateTimeFormatter DATE_FORMAT_DASH_UUU_MM_D = DateTimeFormatter.ofPattern("uu-MM-d");
    private static final DateTimeFormatter DATE_FORMAT_DASH_UUUU_M_D = DateTimeFormatter.ofPattern("uuuu-M-d");
    private static final DateTimeFormatter DATE_FORMAT_DASH_UU_M_D = DateTimeFormatter.ofPattern("uu-M-d");

    List<DateTimeFormatter> dateTimeFormatterList = List.of(
        DATE_FORMAT_SLASH_DD_MM_UUUU,
        DATE_FORMAT_SLASH_DD_MM_UU,
        DATE_FORMAT_SLASH_DD_M_UUUU,
        DATE_FORMAT_SLASH_DD_M_UU,
        DATE_FORMAT_SLASH_D_MM_UUUU,
        DATE_FORMAT_SLASH_D_MM_UU,
        DATE_FORMAT_SLASH_D_M_UUUU,
        DATE_FORMAT_SLASH_D_M_UU,
        DATE_FORMAT_DASH_UUUU_MM_DD,
        DATE_FORMAT_DASH_UUUU_M_DD,
        DATE_FORMAT_DASH_UU_M_DD,
        DATE_FORMAT_DASH_UUUU_MM_D,
        DATE_FORMAT_DASH_UUU_MM_D,
        DATE_FORMAT_DASH_UUUU_M_D,
        DATE_FORMAT_DASH_UU_M_D
    );

    public SpecificFormatDateParser(DateParser dateParser) {
        super(dateParser);
    }

    @Override
    public Optional<LocalDate> getParseDate(String string) {
        var date = tryParseDate(string);

        if (date != null) {
            return Optional.of(date);
        } else if (this.nextParser != null) {
            return nextParser.getParseDate(string);
        }

        return Optional.empty();
    }

    private LocalDate tryParseDate(String string) {
        for (var formatDate : dateTimeFormatterList) {
            try {
                var date = LocalDate.parse(string, formatDate);
                return date;
            } catch (DateTimeException ignored) {

            }
        }
        return null;
    }
}
