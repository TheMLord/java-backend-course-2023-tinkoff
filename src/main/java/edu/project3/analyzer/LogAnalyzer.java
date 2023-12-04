package edu.project3.analyzer;

import edu.project3.LogParser;
import edu.project3.markups.MarkupsLanguage;
import edu.project3.models.LogReport;
import edu.project3.models.ResponseStatus;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Класс анализатора лога.
 */
public class LogAnalyzer {
    private static final String GENERAL_INFO_METRIC_HEADER = "Общая информация";
    private static final String GENERAL_INFO_TABLE_HEADER_1 = "Метрика";
    private static final String GENERAL_INFO_TABLE_HEADER_2 = "Значение";
    private static final String GENERAL_INFO_TABLE_VALUE_1_1 = "Файл(-ы)";
    private static final String GENERAL_INFO_TABLE_VALUE_2_1 = "Начальная дата";
    private static final String GENERAL_INFO_TABLE_VALUE_3_1 = "Конечная дата";
    private static final String GENERAL_INFO_TABLE_VALUE_4_1 = "Количество запросов";
    private static final String GENERAL_INFO_TABLE_VALUE_5_1 = "Средний размер ответа";

    private static final String REQUESTED_RESOURCES_METRIC_HEADER = "Запрашиваемые ресурсы";
    private static final String REQUESTED_RESOURCES_TABLE_HEADER_1 = "Ресурс";
    private static final String REQUESTED_RESOURCES_TABLE_HEADER_2 = "Количество запросов ресурса";

    private static final String RESPONSE_CODES_METRIC_HEADER = "Коды ответа";
    private static final String RESPONSE_CODES_TABLE_HEADER_1 = "Код";
    private static final String RESPONSE_CODES_TABLE_HEADER_2 = "Имя";
    private static final String RESPONSE_CODES_TABLE_HEADER_3 = "Количество кодов";

    private static final String ACTIVITY_REMOTE_ADDRESS_METRIC_HEADER = "Статистика запросов от пользователей";
    private static final String ACTIVITY_REMOTE_ADDRESS_TABLE_HEADER_1 = "Удаленный пользователь";
    private static final String ACTIVITY_REMOTE_ADDRESS_TABLE_HEADER_2 = "Количество запросов пользователя";

    private static final String DAY_ACTIVITY_METRIC_HEADER = "Дневная активность";
    private static final String DAY_ACTIVITY_TABLE_HEADER_1 = "День";
    private static final String DAY_ACTIVITY_TABLE_HEADER_2 = "Количество запросов в день";

    private LogReport logReportGeneralInfo;
    private LogReport logReportRequestResource;
    private LogReport logReportResponseStatus;
    private LogReport logReportRemoteAddressActivity;
    private LogReport logReportDayActivity;

    protected final List<String> logPathsList;
    protected final Path pathToSave;
    protected final MarkupsLanguage markupsLanguage;
    protected final Optional<OffsetDateTime> from;
    protected final Optional<OffsetDateTime> to;

    /**
     * Конструктор класса.
     *
     * @param logPathsList    список путей в виде строк до файлов с логами.
     * @param pathToSave      путь сохранения файла.
     * @param markupsLanguage тип вывода.
     * @param from            опциональный временной параметр начала фильтрации логов.
     * @param to              опциональный временной параметр конца фильтрации логов.
     */
    public LogAnalyzer(
        List<String> logPathsList, Path pathToSave, MarkupsLanguage markupsLanguage,
        Optional<OffsetDateTime> from, Optional<OffsetDateTime> to
    ) {
        this.logPathsList = logPathsList;
        this.pathToSave = pathToSave;
        this.markupsLanguage = markupsLanguage;
        this.from = from;
        this.to = to;
    }

    /**
     * Метод анализирующий лог. После анализа создает файл с метриками указанного типа и сохраняет по переданному пути.
     */
    public void analyzeLogs() {
        int countRequest = 0;

        long sizeResponse = 0;

        Map<String, Integer> requestedResourcesMap = new TreeMap<>();

        Map<ResponseStatus, Integer> responseStatusIntegerMap = new TreeMap<>();

        Map<String, Integer> remoteAddressActivityMap = new TreeMap<>();

        Map<LocalDate, Integer> dayActivityMap = new TreeMap<>();

        for (var path : this.logPathsList) {
            var logRecordList = LogParser.parseNGNIXLog(path).filter(log -> isInTimeRange(log.time())).toList();
            for (var logRecord : logRecordList) {
                countRequest++;

                sizeResponse += logRecord.bodyBytesSentSize();

                updateRequestedResourcesMap(requestedResourcesMap, logRecord.request().resource());
                updateResponseStatusMap(responseStatusIntegerMap, logRecord.status());
                updateRemoteAddressMap(remoteAddressActivityMap, logRecord.remoteAddress());
                updateDayActivityMap(dayActivityMap, logRecord.time());
            }
        }

        logReportGeneralInfo = prepareGeneralInfo(countRequest, sizeResponse);
        logReportRequestResource = prepareRequestResourceInfo(requestedResourcesMap);
        logReportResponseStatus = prepareReportResponseStatusInfo(responseStatusIntegerMap);
        logReportRemoteAddressActivity = prepareRemoteAddressActivityInfo(remoteAddressActivityMap);
        logReportDayActivity = prepareDayActivityInfo(dayActivityMap);

        this.markupsLanguage.printLogMetric(this.pathToSave, logReportGeneralInfo);
        this.markupsLanguage.printLogMetric(this.pathToSave, logReportRequestResource);
        this.markupsLanguage.printLogMetric(this.pathToSave, logReportResponseStatus);
        this.markupsLanguage.printLogMetric(this.pathToSave, logReportRemoteAddressActivity);
        this.markupsLanguage.printLogMetric(this.pathToSave, logReportDayActivity);
    }

    /**
     * Подготовка метрики с основной информацией.
     * Делает метрику с информацией о проанализированных файлах, дате начала, дате конца,
     * количестве запросов, среднем размере принятых байтов.
     *
     * @param countRequest общее количество запросов.
     * @param sizeResponse суммарный размер принятых байтов.
     * @return возвращает подготовленный к печати LogReport по этой метрике.
     */
    private LogReport prepareGeneralInfo(int countRequest, long sizeResponse) {
        long averageSizeRequest = (sizeResponse == 0 || countRequest == 0) ? 0 : sizeResponse / countRequest;

        var startData =
            this.from.map(offsetDateTime -> offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)).orElse("-");
        var endData =
            this.to.map(offsetDateTime -> offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)).orElse("-");
        return new LogReport(
            GENERAL_INFO_METRIC_HEADER,
            List.of(
                GENERAL_INFO_TABLE_HEADER_1,
                GENERAL_INFO_TABLE_HEADER_2
            ),
            List.of(
                List.of(GENERAL_INFO_TABLE_VALUE_1_1, this.logPathsList.stream()
                    .map(Paths::get)
                    .toList().stream()
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toList().toString()),
                List.of(GENERAL_INFO_TABLE_VALUE_2_1, startData),
                List.of(GENERAL_INFO_TABLE_VALUE_3_1, endData),
                List.of(GENERAL_INFO_TABLE_VALUE_4_1, String.valueOf(countRequest)),
                List.of(GENERAL_INFO_TABLE_VALUE_5_1, String.valueOf(averageSizeRequest))
            )
        );
    }

    /**
     * Подготовка метрики с запрашиваемыми ресурсами.
     * Подготавливает метрику с информацией об запрошенных ресурсах и их количестве.
     *
     * @param requestResourceMap Map со статистикой по этой метрике
     * @return возвращает подготовленный к печати LogReport по этой метрике.
     */
    private LogReport prepareRequestResourceInfo(Map<String, Integer> requestResourceMap) {
        var resourceInfoList = requestResourceMap.entrySet().stream()
            .map(entry -> List.of(entry.getKey(), entry.getValue().toString()))
            .toList();

        return new LogReport(
            REQUESTED_RESOURCES_METRIC_HEADER,
            List.of(
                REQUESTED_RESOURCES_TABLE_HEADER_1,
                REQUESTED_RESOURCES_TABLE_HEADER_2
            ),
            resourceInfoList
        );
    }

    /**
     * Подготавливает метрику с кодами ответов.
     * Сделает метрику с информацией о кодах, их расшифровке и общем количестве.
     *
     * @param responseStatusMap Map со статистикой по этой метрике
     * @return возвращает подготовленный к печати LogReport по этой метрике.
     */
    private LogReport prepareReportResponseStatusInfo(Map<ResponseStatus, Integer> responseStatusMap) {
        var responseStatusList = responseStatusMap.entrySet().stream()
            .map(entry -> List.of(
                String.valueOf(entry.getKey().getResponseCode()),
                entry.getKey().toString(),
                entry.getValue().toString()
            ))
            .toList();

        return new LogReport(
            RESPONSE_CODES_METRIC_HEADER,
            List.of(RESPONSE_CODES_TABLE_HEADER_1, RESPONSE_CODES_TABLE_HEADER_2, RESPONSE_CODES_TABLE_HEADER_3),
            responseStatusList
        );
    }

    /**
     * Подготовка метрики с информацией об активности пользователей.
     * Возвращает метрику с активностями пользователей - кто запрашивал и сколько.
     *
     * @param remoteAddressMap Map со статистикой по этой метрике
     * @return возвращает подготовленный к печати LogReport по этой метрике.
     */
    private LogReport prepareRemoteAddressActivityInfo(Map<String, Integer> remoteAddressMap) {
        var addressActivityList = remoteAddressMap.entrySet().stream()
            .map(entry -> List.of(
                entry.getKey(),
                entry.getValue().toString()
            ))
            .toList();

        return new LogReport(
            ACTIVITY_REMOTE_ADDRESS_METRIC_HEADER,
            List.of(ACTIVITY_REMOTE_ADDRESS_TABLE_HEADER_1, ACTIVITY_REMOTE_ADDRESS_TABLE_HEADER_2),
            addressActivityList
        );
    }

    /**
     * Подготовка метрки с информацией о количестве запросов на каждый день.
     *
     * @param dayActivityMap Map со статистикой по этой метрике
     * @return возвращает подготовленный к печати LogReport по этой метрике.
     */
    private LogReport prepareDayActivityInfo(Map<LocalDate, Integer> dayActivityMap) {
        var activityDayList = dayActivityMap.entrySet().stream()
            .map(entry -> List.of(
                entry.getKey().toString(),
                entry.getValue().toString()
            ))
            .toList();

        return new LogReport(
            DAY_ACTIVITY_METRIC_HEADER,
            List.of(DAY_ACTIVITY_TABLE_HEADER_1, DAY_ACTIVITY_TABLE_HEADER_2),
            activityDayList
        );
    }

    /**
     * Изменения Map по метрике
     */
    private void updateRequestedResourcesMap(Map<String, Integer> map, String resource) {
        map.putIfAbsent(resource, 0);
        map.put(resource, map.get(resource) + 1);
    }

    /**
     * Изменения Map по метрике
     */
    private void updateResponseStatusMap(Map<ResponseStatus, Integer> map, ResponseStatus response) {
        map.putIfAbsent(response, 0);
        map.put(response, map.get(response) + 1);
    }

    /**
     * Изменения Map по метрике
     */
    private void updateRemoteAddressMap(Map<String, Integer> map, String remoteAddress) {
        map.putIfAbsent(remoteAddress, 0);
        map.put(remoteAddress, map.get(remoteAddress) + 1);
    }

    /**
     * Изменения Map по метрике
     */
    private void updateDayActivityMap(Map<LocalDate, Integer> map, OffsetDateTime time) {
        var day = LocalDate.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth());
        map.putIfAbsent(day, 0);
        map.put(day, map.get(day) + 1);
    }

    /**
     * Изменения Map по метрике
     */
    private boolean isInTimeRange(OffsetDateTime time) {
        return (this.from.isEmpty() || time.isEqual(this.from.get()) || time.isAfter(this.from.get()))
            && (this.to.isEmpty() || time.isEqual(this.to.get()) || time.isBefore(this.to.get()));
    }

    public LogReport getLogReportGeneralInfo() {
        return logReportGeneralInfo;
    }

    public LogReport getLogReportRequestResource() {
        return logReportRequestResource;
    }

    public LogReport getLogReportResponseStatus() {
        return logReportResponseStatus;
    }

    public LogReport getLogReportRemoteAddressActivity() {
        return logReportRemoteAddressActivity;
    }

    public LogReport getLogReportDayActivity() {
        return logReportDayActivity;
    }
}
