package edu.project3.analyzer;

import edu.project3.LogParser;
import edu.project3.markups.MarkupsLanguage;
import edu.project3.models.LogReport;
import edu.project3.models.ResponseStatus;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    protected final List<String> logPathsList;
    protected final Path pathToSave;
    protected final MarkupsLanguage markupsLanguage;
    protected final Optional<OffsetDateTime> from;
    protected final Optional<OffsetDateTime> to;

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

    public void analyzeLogs() {
        int countRequest = 0;

        long sizeResponse = 0;

        Map<String, Integer> requestedResourcesMap = new HashMap<>();

        Map<ResponseStatus, Integer> responseStatusIntegerMap = new HashMap<>();

        Map<String, Integer> remoteAddressActivityMap = new HashMap<>();

        Map<LocalDate, Integer> dayActivityMap = new HashMap<>();

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

        var logReportGeneralInfo = prepareGeneralInfo(countRequest, sizeResponse);
        var logReportRequestResource = prepareRequestResourceInfo(requestedResourcesMap);
        var logReportResponseStatus = prepareReportResponseStatusInfo(responseStatusIntegerMap);
        var logReportRemoteAddressActivity = prepareRemoteAddressActivityInfo(remoteAddressActivityMap);
        var logReportDayActivity = prepareDayActivityInfo(dayActivityMap);

        this.markupsLanguage.printLogMetric(this.pathToSave, logReportGeneralInfo);
        this.markupsLanguage.printLogMetric(this.pathToSave, logReportRequestResource);
        this.markupsLanguage.printLogMetric(this.pathToSave, logReportResponseStatus);
        this.markupsLanguage.printLogMetric(this.pathToSave, logReportRemoteAddressActivity);
        this.markupsLanguage.printLogMetric(this.pathToSave, logReportDayActivity);
    }

    private LogReport prepareGeneralInfo(int countRequest, long sizeResponse) {
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
                List.of(GENERAL_INFO_TABLE_VALUE_1_1, this.logPathsList.toString()),
                List.of(GENERAL_INFO_TABLE_VALUE_2_1, startData),
                List.of(GENERAL_INFO_TABLE_VALUE_3_1, endData),
                List.of(GENERAL_INFO_TABLE_VALUE_4_1, String.valueOf(countRequest)),
                List.of(GENERAL_INFO_TABLE_VALUE_5_1, String.valueOf(sizeResponse / countRequest))
            )
        );
    }

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

    private void updateRequestedResourcesMap(Map<String, Integer> map, String resource) {
        map.putIfAbsent(resource, 0);
        map.put(resource, map.get(resource) + 1);
    }

    private void updateResponseStatusMap(Map<ResponseStatus, Integer> map, ResponseStatus response) {
        map.putIfAbsent(response, 0);
        map.put(response, map.get(response) + 1);
    }

    private void updateRemoteAddressMap(Map<String, Integer> map, String remoteAddress) {
        map.putIfAbsent(remoteAddress, 0);
        map.put(remoteAddress, map.get(remoteAddress) + 1);
    }

    private void updateDayActivityMap(Map<LocalDate, Integer> map, OffsetDateTime time) {
        var day = LocalDate.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth());
        map.putIfAbsent(day, 0);
        map.put(day, map.get(day) + 1);
    }

    private boolean isInTimeRange(OffsetDateTime time) {
        return (this.from.isEmpty() || time.isEqual(this.from.get()) || time.isAfter(this.from.get()))
            && (this.to.isEmpty() || time.isEqual(this.to.get()) || time.isBefore(this.to.get()));
    }

}
