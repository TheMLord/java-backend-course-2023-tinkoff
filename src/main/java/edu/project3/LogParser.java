package edu.project3;

import edu.project3.models.LogRecord;
import edu.project3.models.RequestRecord;
import edu.project3.models.ResponseStatus;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static java.net.http.HttpClient.newHttpClient;

/**
 * Парсер NGINX логов.
 */
public final class LogParser {
    private static final String LOG_SEPARATOR = " ";

    private static final Pattern LOG_PATTERN =
        Pattern.compile("(.+) (\\S+) (\\S+) \\[([^]]+)] \"([^\"]+)\" (\\d+) (\\d+) \"([^\"]+)\" \"([^\"]+)\"");

    private static final int REMOTE_ADDRESS_INDEX = 0;
    private static final int USER_ID_INDEX = 1;
    private static final int AUTH_ID_INDEX = 2;
    private static final int TIME_INDEX = 3;
    private static final int REQUEST_INDEX = 4;
    private static final int STATUS_INDEX = 5;
    private static final int BODY_BYTES_SENT_SIZE_INDEX = 6;
    private static final int HTTP_REFER_INDEX = 7;
    private static final int USER_AGENT_INDEX = 8;

    private static final int HTTP_METHOD_INDEX = 0;
    private static final int HTTP_RESOURCES_PATH_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;

    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("[dd/MMM/yyyy:HH:mm:ss Z]", Locale.ENGLISH);

    private static final int SIZE_LINE_LOG = 9;

    /**
     * Конструктор класса.
     */
    private LogParser() {

    }

    /**
     * Метод, парсящий логи.
     *
     * @param pathToLog путь до локального файла с логом или http путь до файла с логом.
     */
    public static Stream<LogRecord> parseNGNIXLog(String pathToLog) {
        try {
            if (!pathToLog.startsWith("https")) {
                return Files.lines(Path.of(pathToLog), StandardCharsets.UTF_8).map(LogParser::prepareLogRecord);
            }
            return getHttpLog(pathToLog).map(LogParser::prepareLogRecord);
        } catch (IOException | InterruptedException e) {
            return Stream.empty();
        }
    }

    /**
     * Метод, делающий Get запрос на получение логов.
     * Вызывается в случае, если переданный путь до файла с логами получается по Http запросу.
     */
    private static Stream<String> getHttpLog(String pathToLog) throws IOException, InterruptedException {
        return newHttpClient().send(
            HttpRequest.newBuilder(URI.create(pathToLog)).GET().build(),
            HttpResponse.BodyHandlers.ofLines()
        ).body();
    }

    /**
     * Преобразование строки лога в модель с информацией о логе.
     */
    private static LogRecord prepareLogRecord(String logLine) {
        var logArg = parseLineLog(logLine);
        return new LogRecord(
            logArg[REMOTE_ADDRESS_INDEX],
            logArg[USER_ID_INDEX],
            logArg[AUTH_ID_INDEX],
            parseTime(logArg[TIME_INDEX]),
            parseRequest(logArg[REQUEST_INDEX]),
            parseResponseStatus(logArg[STATUS_INDEX]),
            Long.parseLong(logArg[BODY_BYTES_SENT_SIZE_INDEX]),
            logArg[HTTP_REFER_INDEX],
            logArg[USER_AGENT_INDEX]
        );
    }

    /**
     * Разбиение очередной строки из файла с логами по регулярному выражению,
     * для получения отдельных информационных компонентов лога.
     */
    private static String[] parseLineLog(String line) {
        String[] logParts = new String[SIZE_LINE_LOG];

        Matcher matcher = LOG_PATTERN.matcher(line);
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                logParts[i - 1] = matcher.group(i);
            }
        }
        return logParts;
    }

    /**
     * Преобразование даты в логе к OffsetDateTime
     */
    private static OffsetDateTime parseTime(String time) {
        return OffsetDateTime.parse(time, DATE_FORMATTER);
    }

    /**
     * Получение модели запроса из лога для большей информативности.
     * Выделяет из лога информацию о методе http, запрашиваемом ресурсе, версии http.
     */
    private static RequestRecord parseRequest(String request) {
        var httpRequest = request.split(LOG_SEPARATOR);

        return new RequestRecord(
            httpRequest[HTTP_METHOD_INDEX],
            httpRequest[HTTP_RESOURCES_PATH_INDEX],
            httpRequest[HTTP_VERSION_INDEX]
        );
    }

    /**
     * Возвращение информации о коде ответа.
     */
    private static ResponseStatus parseResponseStatus(String statusCode) {
        return ResponseStatus.responseCodeOf(Integer.parseInt(statusCode));
    }
}
