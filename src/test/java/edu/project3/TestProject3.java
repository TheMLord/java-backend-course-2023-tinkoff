package edu.project3;

import edu.project3.analyzer.LogAnalyzer;
import edu.project3.markups.ASCIIDoc;
import edu.project3.markups.MarkDown;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class TestProject3 {

    private static final List<String> PATH_TO_LOG = List.of(
        TestProject3.class.getResource("/test.log").getPath()
    );

    @Test
    @Order(1)
    @DisplayName(
        "Test that the data from the log files is taken correctly and filtered and will return the correct file MD with statistics")
    void testThatTheDataFromTheLogFilesIsTakenCorrectlyAndFilteredAndWillReturnTheCorrectFileMDWithStatistics(
        @TempDir
        Path testDirMD
    ) throws IOException {

        var exceptedResultFilePath = Path.of(TestProject3.class.getResource("/goldenMdResult.md").getPath());

        var actualresultFilePath = testDirMD.resolve("result.md");

        var logAnalyzer = new LogAnalyzer(
            PATH_TO_LOG,
            actualresultFilePath,
            new MarkDown(),
            Optional.empty(),
            Optional.empty()
        );

        logAnalyzer.analyzeLogs();

        var expectedContent = Files.readString(exceptedResultFilePath);
        var actualContent = Files.readString(actualresultFilePath);
        System.out.println(expectedContent);
        System.out.println(actualContent);
        var isEqualsResult = expectedContent.equals(actualContent);

        assertThat(isEqualsResult).isTrue();
    }

    @Test
    @Order(2)
    @DisplayName("Test that the log file is parsed correctly and filtered returned the correct DOC file")
    void testThatTheLogFileIsParsedCorrectlyAndFilteredReturnedTheCorrectDocFile(
        @TempDir Path testDirADOC
    ) throws IOException {
        var exceptedResultFilePath = Path.of(TestProject3.class.getResource("/goldenADOCResult.adoc").getPath());

        var actualresultFilePath = testDirADOC.resolve("result.adoc");

        var logAnalyzer = new LogAnalyzer(
            PATH_TO_LOG,
            actualresultFilePath,
            new ASCIIDoc(),
            Optional.empty(),
            Optional.empty()
        );
        logAnalyzer.analyzeLogs();

        var expectedContent = Files.readString(exceptedResultFilePath);
        var actualContent = Files.readString(actualresultFilePath);

        var isEqualsResult = expectedContent.equals(actualContent);

        assertThat(isEqualsResult).isTrue();
    }

}
