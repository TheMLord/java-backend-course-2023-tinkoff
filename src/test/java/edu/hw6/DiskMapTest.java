package edu.hw6;

import edu.hw6.Task1.DiskMap;
import edu.hw6.Task1.DiskMapException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiskMapTest {
    private static final File EMPTY_FILE = new File("emptyFile.txt");
    private static final File INVALID_FILE = new File("invalidFile.txt");
    private static final File VALID_FILE = new File("validFile.txt");
    private static final File NOT_FILE = new File("directory");
    private static final File NOT_CREATED_FILE = new File("notCreatedFile.txt");
    private static final String VALID_KEY_STRING = "validKey";
    private static final String VALID_SEPPARETOR_STRING = ":";
    private static final String VALID_VALUE_STRING = "validValue";

    @BeforeAll static void createTestFileAndDirectory() {
        try {
            var isCreatedEmptyFile = EMPTY_FILE.createNewFile();
        } catch (IOException ignored) {
        }

        try (var bfwInvalid = new BufferedWriter(new FileWriter(INVALID_FILE))) {
            bfwInvalid.write("invalidMap");
        } catch (IOException ignored) {
        }

        try (var bfwValid = new BufferedWriter(new FileWriter(VALID_FILE))) {
            bfwValid.write(VALID_KEY_STRING + VALID_SEPPARETOR_STRING + VALID_VALUE_STRING);
        } catch (IOException ignored) {
        }

        var isCreatedDirectory = NOT_FILE.mkdir();

    }

    @AfterAll static void deleteTestFileAndDirectory() {
        var isDeleteEmptyFile = EMPTY_FILE.delete();
        var isDeleteInvalidFile = INVALID_FILE.delete();
        var isDeleteValidFile = VALID_FILE.delete();
        var isDeleteDirectory = NOT_FILE.delete();
        var isDeleteNotCreatedFile = NOT_CREATED_FILE.delete();
    }

    @Test
    @DisplayName("Test that DiskMap is correctly read from a valid file and returned the correct data")
    void testThatDiskMapIsCorrectlyReadFromAValidFileAndReturnedTheCorrectData() {
        var diskMap = new DiskMap(VALID_FILE.getPath());

        var keyMap = diskMap.keySet();
        var keyValue = diskMap.values();

        assertThat(keyMap).containsOnly(VALID_KEY_STRING);
        assertThat(keyValue).containsOnly(VALID_VALUE_STRING);
    }

    @Test
    @DisplayName(
        "Test that DiskMap successfully saves new data to a file and returned a message about successful saving and saved data")
    void testThatDiskMapSuccessfullySavesNewDataToAFileAndReturnedAMessageAboutSuccessfulSavingAndSavedData() {
        var emptyDiskMap = new DiskMap(EMPTY_FILE.getPath());
        var newKey = "newKey";
        var newValue = "newValue";
        var exceptedSaveMessage = "Data recorded successfully recorded in a file " + EMPTY_FILE.getPath();

        emptyDiskMap.put(newKey, newValue);
        assertThat(emptyDiskMap.saveDataOnDiskMap()).isEqualTo(exceptedSaveMessage);

        var afterSaveDiskMap = new DiskMap(EMPTY_FILE.getPath());

        var actualSaveKey = afterSaveDiskMap.keySet();
        var actualSaveValue = afterSaveDiskMap.values();

        assertThat(actualSaveKey).containsOnly(newKey);
        assertThat(actualSaveValue).containsOnly(newValue);
    }

    @Test
    @DisplayName(
        "Test that Disk Map creates a file on disk if it did not exist yet and writes data there returned a message about successful saving and contains the correct data in the saved file")
    void testThatDiskMapCreatesAFileOnDiskIfItDidNotExistYetAndWritesDataThereReturnedAMessageAboutSuccessfulSavingAndContainsTheCorrectDataInTheSavedFile() {
        var emptyDiskMap = new DiskMap(NOT_CREATED_FILE.getPath());
        var newKey = "newKey";
        var newValue = "newValue";
        var exceptedSaveMessage = "Data recorded successfully recorded in a file " + NOT_CREATED_FILE.getPath();

        emptyDiskMap.put(newKey, newValue);
        assertThat(emptyDiskMap.saveDataOnDiskMap()).isEqualTo(exceptedSaveMessage);

        var afterSaveDiskMap = new DiskMap(NOT_CREATED_FILE.getPath());

        var actualSaveKey = afterSaveDiskMap.keySet();
        var actualSaveValue = afterSaveDiskMap.values();

        assertThat(actualSaveKey).containsOnly(newKey);
        assertThat(actualSaveValue).containsOnly(newValue);
    }

    @Test
    @DisplayName(
        "Test that DiskMap does not work with invalid files and returns an exception with the corresponding message")
    void testThatDiskMapDoesNotWorkWithInvalidFilesAndReturnedAnExceptionWithTheCorrespondingMessage() {
        var exceptedExceptionMessage = "The data format in the file does not match the template - key:value";

        assertThatThrownBy(() -> new DiskMap(INVALID_FILE.getPath()))
            .isInstanceOf(DiskMapException.class)
            .hasMessage(exceptedExceptionMessage);
    }

    @Test
    @DisplayName(
        "Test that DiskMap does not work with paths that are not files and returned an exception with the correct message")
    void testThatDiskMapDoesNotWorkWithPathsThatAreNotFilesAndReturnedAnExceptionWithTheCorrectMessage() {
        var exceptedExceptionMessage = "The passed path is not the path to the file";

        assertThatThrownBy(() -> new DiskMap(NOT_FILE.getPath()))
            .isInstanceOf(DiskMapException.class)
            .hasMessage(exceptedExceptionMessage);
    }
}
