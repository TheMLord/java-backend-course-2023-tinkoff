package edu.project3;

import edu.project3.analyzer.LogAnalyzer;
import edu.project3.markups.ASCIIDoc;
import edu.project3.markups.MarkDown;
import edu.project3.markups.MarkupsLanguage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class TestProject3 {
    private static final String TIME_ZONE = "T00:00:00Z";

    private static final Path GOLDEN_ADOC_FILE =
        Paths.get(
            String.valueOf(Paths.get("").toAbsolutePath()),
            "src/test/java/edu/project3/goldenFiles/goldenADOCResult.adoc"
        );
    private static final Path GOLDEN_MD_FILE =
        Paths.get(
            String.valueOf(Paths.get("").toAbsolutePath()),
            "src/test/java/edu/project3/goldenFiles/goldenMDResult.md"
        );
    private static final Path GOLDEN_EMPTY_FILE =
        Paths.get(
            String.valueOf(Paths.get("").toAbsolutePath()),
            "src/test/java/edu/project3/goldenFiles/goldenEmpty.adoc"
        );

    private static final List<String> PATH_TO_LOG_ONE_FILE = List.of(
        Paths.get(String.valueOf(Paths.get("").toAbsolutePath()), "src/test/java/edu/project3/test.log").toString()
    );

    private static final List<String> PATH_TO_LOG_SOME_FILE = List.of(
        Paths.get(String.valueOf(Paths.get("").toAbsolutePath()), "src/test/java/edu/project3/test.log").toString(),
        Paths.get(String.valueOf(Paths.get("").toAbsolutePath()), "src/test/java/edu/project3/test2.log").toString()
    );

    @Nested
    @DisplayName("Tests comparing the result file with logs from golden files")
    class ComparingGoldenFilesTest {
        @Test
        @DisplayName(
            "Test that the data from the log files is taken correctly and filtered and will return the correct file MD with statistics")
        void testThatTheDataFromTheLogFilesIsTakenCorrectlyAndFilteredAndWillReturnTheCorrectFileMDWithStatistics(
            @TempDir
            Path testDirMD
        ) throws IOException {
            var expectedLogFile = Files.readString(GOLDEN_MD_FILE);
            var pathToSaveLogAnalyze = testDirMD.resolve("result.md");

            startAnalyzeLog(
                PATH_TO_LOG_ONE_FILE,
                pathToSaveLogAnalyze,
                new MarkDown(),
                Optional.empty(),
                Optional.empty()
            );
            var actualLogFile = Files.readString(pathToSaveLogAnalyze);

            assertThat(actualLogFile).isEqualTo(expectedLogFile);
        }

        @Test
        @DisplayName("Test that the log file is parsed correctly and filtered returned the correct DOC file")
        void testThatTheLogFileIsParsedCorrectlyAndFilteredReturnedTheCorrectDocFile(
            @TempDir Path testDirADOC
        ) throws IOException {
            var expectedLogFile = Files.readString(GOLDEN_ADOC_FILE);
            var pathToSaveLogAnalyze = testDirADOC.resolve("result.adoc");

            startAnalyzeLog(PATH_TO_LOG_ONE_FILE, pathToSaveLogAnalyze, new ASCIIDoc(),
                Optional.empty(),
                Optional.empty()
            );
            var actualLogFile = Files.readString(pathToSaveLogAnalyze);

            assertThat(actualLogFile).isEqualTo(expectedLogFile);
        }

        @Test
        @DisplayName(
            "Test that statistics are being collected correctly from the log file and returned the correct file for empty statistics")
        void testThatStatisticsAreBeingCollectedCorrectlyFromTheLogFileAndReturnedTheCorrectFileForEmptyStatistics(
            @TempDir Path testDir
        ) throws IOException {
            var expectedLogFile = Files.readString(GOLDEN_EMPTY_FILE);
            var pathToSaveLogAnalyze = testDir.resolve("result.adoc");

            startAnalyzeLog(PATH_TO_LOG_ONE_FILE, pathToSaveLogAnalyze, new ASCIIDoc(),
                Optional.of(parseData("2015-05-15")),
                Optional.of(parseData("2015-05-16"))
            );
            var actualLogFile = Files.readString(pathToSaveLogAnalyze);

            assertThat(actualLogFile).isEqualTo(expectedLogFile);
        }

    }

    @Nested
    @DisplayName("Tests to verify the correct data of the log metrics")
    class CorrectDataLogMetricTests {
        private static LogAnalyzer logAnalyzer;

        @BeforeAll
        private static void analyzeLog(@TempDir Path testDir) {

            logAnalyzer = new LogAnalyzer(
                PATH_TO_LOG_SOME_FILE,
                testDir.resolve("result.adoc"),
                new ASCIIDoc(),
                Optional.empty(),
                Optional.empty()
            );
            logAnalyzer.analyzeLogs();
        }

        @Test
        @DisplayName(
            "Test that the general information was collected correctly returned the correct content for each metric")
        void testThatTheGeneralInformationWasCollectedCorrectlyReturnedTheCorrectContentForEachMetric() {
            var exceptedFilesToAnalyze = "[test.log, test2.log]";

            var exceptedTimeStart = "-";
            var exceptedTimeEnd = "-";

            var exceptedCountRequest = "65";
            var exceptedAverageSizeResponse = "405183";

            assertThat(logAnalyzer.getLogReportGeneralInfo().tableValues().get(0).get(1)).isEqualTo(
                exceptedFilesToAnalyze);
            assertThat(logAnalyzer.getLogReportGeneralInfo().tableValues().get(1).get(1)).isEqualTo(exceptedTimeStart);
            assertThat(logAnalyzer.getLogReportGeneralInfo().tableValues().get(2).get(1)).isEqualTo(exceptedTimeEnd);
            assertThat(logAnalyzer.getLogReportGeneralInfo().tableValues().get(3)
                .get(1)).isEqualTo(exceptedCountRequest);
            assertThat(logAnalyzer.getLogReportGeneralInfo().tableValues().get(4).get(1)).isEqualTo(
                exceptedAverageSizeResponse);
        }

        @Test
        @DisplayName(
            "Test that the information about the requested resources was correctly collected and returned the correct content for each metric")
        void testThatTheInformationAboutTheRequestedResourcesWasCorrectlyCollectedAndReturnedTheCorrectContentForEachMetric() {
            var exceptedResource1 = List.of("/downloads/product_1", "34");
            var exceptedResource2 = List.of("/downloads/product_2", "31");

            assertThat(logAnalyzer.getLogReportRequestResource().tableValues().get(0)).isEqualTo(exceptedResource1);
            assertThat(logAnalyzer.getLogReportRequestResource().tableValues().get(1)).isEqualTo(exceptedResource2);
        }

        @Test
        @DisplayName(
            "Test that the information about the response codes was correctly collected and returned the correct content for each metric")
        void testThatTheInformationAboutTheResponseCodesWasCorrectlyCollectedAndReturnedTheCorrectContentForEachMetric() {
            var exceptedMetric = List.of(
                List.of("200", "OK", "5"),
                List.of("304", "NOT_MODIFIED", "17"),
                List.of("404", "NOT_FOUND", "43")
            );

            assertThat(logAnalyzer.getLogReportResponseStatus().tableValues()).isEqualTo(exceptedMetric);
        }

        @Test
        @DisplayName(
            "Test that information about the statistics of requests from users was correctly collected and returned the correct content for each metric")
        void testThatInformationAboutTheStatisticsOfRequestsFromUsersWasCorrectlyCollectedAndReturnedTheCorrectContentForEachMetric() {
            var exceptedStatistic = List.of(
                List.of("114.80.245.62", "1"),
                List.of("137.117.180.241", "3"),
                List.of("137.117.184.211", "12"),
                List.of("194.177.215.120", "3"),
                List.of("204.77.168.241", "2"),
                List.of("217.168.17.5", "4"),
                List.of("23.101.139.146", "2"),
                List.of("23.22.161.98", "2"),
                List.of("23.23.76.161", "2"),
                List.of("54.77.119.190", "4"),
                List.of("54.86.8.9", "9"),
                List.of("78.109.87.141", "17"),
                List.of("80.91.33.133", "1"),
                List.of("93.180.71.3", "3")
            );
            assertThat(logAnalyzer.getLogReportRemoteAddressActivity().tableValues()).isEqualTo(exceptedStatistic);
        }

        @Test
        @DisplayName(
            "Test that information about daily statistics was collected correctly and returned the correct content for each metric")
        void testThatInformationAboutDailyStatisticsWasCollectedCorrectlyAndReturnedTheCorrectContentForEachMetric() {
            var exceptedDayActivity = List.of(
                List.of("2015-05-17", "8"),
                List.of("2015-05-26", "57")
            );

            assertThat(logAnalyzer.getLogReportDayActivity().tableValues()).isEqualTo(exceptedDayActivity);
        }

    }

    private void startAnalyzeLog(
        List<String> pathToLogFilesTOAnalyze,
        Path pathToSaveLog,
        MarkupsLanguage markupsLanguage,
        Optional<OffsetDateTime> from,
        Optional<OffsetDateTime> to
    ) {
        var logAnalyzer = new LogAnalyzer(
            pathToLogFilesTOAnalyze,
            pathToSaveLog,
            markupsLanguage,
            from,
            to
        );
        logAnalyzer.analyzeLogs();
    }

    private static OffsetDateTime parseData(String date) {
        return OffsetDateTime.parse(date + TIME_ZONE);
    }

}
