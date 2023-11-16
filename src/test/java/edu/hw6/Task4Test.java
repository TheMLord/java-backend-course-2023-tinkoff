package edu.hw6;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

class Task4Test {
    @Test
    @DisplayName(
        "Test that the composition from OutputStream is working and the specified text is being successfully written to the file will return the recorded text from the newly recorded file")
    void testThatTheCompositionFromOutputStreamIsWorkingAndTheSpecifiedTextIsBeingSuccessfullyWrittenToTheFileWillReturnTheRecordedTextFromTheNewlyRecordedFile(
        @TempDir
        Path testDir
    ) {
        var testFilePath = Path.of(String.valueOf(testDir.resolve("testFile.txt")));
        var exceptedRecordedText = "Programming is learned by writing programs. ― Brian Kernighan";

        Task4.writeToFile(testFilePath, exceptedRecordedText);

        var actualRecordedText = Files.contentOf(new File(String.valueOf(testFilePath)), StandardCharsets.UTF_8);

        assertThat(actualRecordedText).isEqualTo(exceptedRecordedText);

    }

}
