package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.AbstractFilter.globMatches;
import static edu.hw6.AbstractFilter.largerThan;
import static edu.hw6.AbstractFilter.magicNumber;
import static edu.hw6.AbstractFilter.regexContains;
import static org.assertj.core.api.Assertions.assertThat;

public class AbstractFilterTest {
    private static final Path DIR =
        Paths.get(String.valueOf(Paths.get("").toAbsolutePath()), "src/test/java/edu/hw6/testDir");
    public static final AbstractFilter REGULAR_FILE = Files::isRegularFile;

    @Test
    @DisplayName(
        "Test that the filter works correctly and correctly returns the paths to the files according to the specified parameters returned a png photo with parameters that fit it")
    void testThatTheFilterWorksCorrectlyAndCorrectlyReturnsThePathsToTheFilesAccordingToTheSpecifiedParametersReturnedAPngPhotoWithParametersThatFitIt() {
        DirectoryStream.Filter<Path> filter = REGULAR_FILE
            .and(Files::isReadable)
            .and(largerThan(2))
            .and(magicNumber((byte) 0x89, (byte) 'P', (byte) 'N', (byte) 'G'))
            .and(globMatches("*.png"))
            .and(regexContains("[_]"));

        var expectedPngFileName = "uporotij_lis.png";

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(DIR, filter)) {
            List<String> filesName = new ArrayList<>();
            ds.forEach(path -> filesName.add(path.getFileName().toString()));
            assertThat(filesName).containsOnly(expectedPngFileName);

        } catch (IOException ignored) {
        }
    }

    @Test
    @DisplayName(
        "Test that the filter works correctly and correctly returns the paths to the files according to the specified parameters returned a txt file with a suitable for the specified parameters")
    void testThatTheFilterWorksCorrectlyAndCorrectlyReturnsThePathsToTheFilesAccordingToTheSpecifiedParametersReturnedATxtFileWithASuitableForTheSpecifiedParameters() {
        DirectoryStream.Filter<Path> filter = REGULAR_FILE
            .and(Files::isReadable)
            .and(globMatches("*.txt"))
            .and(regexContains("a+"));

        var expectedPngFileName = "taaaa.txt";

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(DIR, filter)) {
            List<String> filesName = new ArrayList<>();
            ds.forEach(path -> filesName.add(path.getFileName().toString()));
            assertThat(filesName).containsOnly(expectedPngFileName);

        } catch (IOException ignored) {
        }
    }

}
