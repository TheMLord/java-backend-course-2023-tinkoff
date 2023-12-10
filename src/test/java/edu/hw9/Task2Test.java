package edu.hw9;

import edu.hw9.Task2.AlgorithmSearchDirectoryForLength;
import edu.hw9.Task2.AlgorithmSearchForPredicate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    private static Path root;
    private static Path pathDir1;
    private static Path pathDir2;
    private static Path pathDir3;

    @BeforeAll
    public static void prepareDirectoryAndFiles(@TempDir Path testDir) throws IOException {
        root = testDir;

        pathDir1 = testDir.resolve("dir1");
        pathDir2 = pathDir1.resolve("dir2");
        pathDir3 = pathDir2.resolve("dir3");

        Files.createDirectories(pathDir1);
        Files.createDirectories(pathDir2);
        Files.createDirectories(pathDir3);

        for (int i = 1; i <= 10; i++) {
            String fileName = "File" + i + ".txt";
            Path filePath = pathDir1.resolve(fileName);

            Files.createFile(filePath);

        }

        for (int i = 1; i <= 10; i++) {
            String fileName = "File" + i + ".log";
            Path filePath = pathDir2.resolve(fileName);

            Files.createFile(filePath);

        }

        for (int i = 1; i <= 400; i++) {
            String fileName = "File" + i + ".pptx";
            Path filePath = pathDir3.resolve(fileName);

            Files.createFile(filePath);
        }
    }

    @Test
    @DisplayName(
        "Test that the recursive algorithm for searching directories with the specified length works correctly and returned the expected number of directories found")
    void testThatTheRecursiveAlgorithmForSearchingDirectoriesWithTheSpecifiedLengthWorksCorrectlyAndReturnedTheExpectedNumberOfDirectoriesFound() {
        var exceptedCountDirectory = 1;
        var directoryLength = 11;

        var foundDirectory = searchDirectory(directoryLength);

        assertThat(foundDirectory.size()).isEqualTo(exceptedCountDirectory);
        assertThat(foundDirectory).containsOnly(pathDir3);
    }

    @Test
    @DisplayName(
        "Test that the recursive predicate directory search algorithm works correctly and returned the expected number of files found")
    void testThatTheRecursivePredicateDirectorySearchAlgorithmWorksCorrectlyAndReturnedTheExpectedNumberOfFilesFound() {
        var exceptedCountFiles = 410;

        var foundFiles = searchFiles();

        assertThat(foundFiles.size()).isEqualTo(exceptedCountFiles);

        for (var file : foundFiles) {
            assertThat(file.getFileName().toString().endsWith(".txt")
                | file.getFileName().toString().endsWith(".pptx")).isTrue();
        }
    }

    private List<Path> searchDirectory(int length) {
        try (ForkJoinPool fjp = new ForkJoinPool()) {
            var algorithmSearch = new AlgorithmSearchDirectoryForLength(root, length);

            return fjp.invoke(algorithmSearch);
        }
    }

    private List<Path> searchFiles() {
        try (ForkJoinPool fjp = new ForkJoinPool()) {
            var algorithmSearch = new AlgorithmSearchForPredicate(
                root,
                path -> Files.isRegularFile(path) &&
                    (path.toString().endsWith(".txt") | path.toString().endsWith(".pptx"))
            );

            return fjp.invoke(algorithmSearch);
        }
    }

}
