package edu.hw6;

import java.nio.file.Path;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

class Task2Test {
    private static final String FILE_1 = "1.txt";

    private static final String FILE_2 = "2.txt";
    private static final String COPY_FILE_2 = "2 - копия.txt";

    private static final String NOT_EXIST_FILE = "notExist.txt";

    @Test
    @DisplayName(
        "Test that the first copy of the file is being made successfully and returned the correct message about the operation of the method")
    void testThatTheFirstCopyOfTheFileIsBeingMadeSuccessfullyAndReturnedTheCorrectMessageAboutTheOperationOfTheMethod
        (@TempDir Path testDirPath) {
        var exceptedStatusCopy = "Successful copying. New file name 1.txt - 1 - копия.txt";
        var file1Path = Path.of(String.valueOf(testDirPath.resolve(FILE_1)));
        Files.newFile(String.valueOf(file1Path));

        var actualStatusCopy = Task2.clonePath(file1Path);

        assertThat(actualStatusCopy).isEqualTo(exceptedStatusCopy);
    }

    @Test
    @DisplayName(
        "Test that an arbitrary copy of the file is being successfully made and returned the correct message about the operation of the method")
    void testThatAnArbitraryCopyOfTheFileIsBeingSuccessfullyMadeAndReturnedTheCorrectMessageAboutTheOperationOfTheMethod
        (@TempDir Path testDirPath) {
        var exceptedStatusCopy = "Successful copying. New file name 2.txt - 2 - копия (1).txt";

        var file2ToCopyPath = Path.of(String.valueOf(testDirPath.resolve(FILE_2)));
        var file2ExistCopy = Path.of(String.valueOf(testDirPath.resolve(COPY_FILE_2)));
        Files.newFile(String.valueOf(file2ToCopyPath));
        Files.newFile(String.valueOf(file2ExistCopy));

        var actualStatusCopy = Task2.clonePath(file2ToCopyPath);

        assertThat(actualStatusCopy).isEqualTo(exceptedStatusCopy);
    }

    @Test
    @DisplayName("Test that the directory is not being copied returned a message that says this")
    void testThatTheDirectoryIsNotBeingCopiedReturnedAMessageThatSaysThis
        (@TempDir Path testDirPath) {
        var exceptedStatusCopy = "Copying failed. To copy, you must pass a file to the method";

        var actualStatusCopy = Task2.clonePath(testDirPath);

        assertThat(actualStatusCopy).isEqualTo(exceptedStatusCopy);
    }

    @Test
    @DisplayName("Test that copying of a non-existent file does not occur returned a message with this error")
    void testThatCopyingOfANonExistentFileDoesNotOccurReturnedAMessageWithThisError
        (@TempDir Path testDirPath) {
        var exceptedStatusCopy = "Copying failed.  You cannot copy a non-existent file";
        var notExistFilePath = Path.of(String.valueOf(testDirPath.resolve(NOT_EXIST_FILE)));

        var actualStatusCopy = Task2.clonePath(notExistFilePath);

        assertThat(actualStatusCopy).isEqualTo(exceptedStatusCopy);
    }
}
