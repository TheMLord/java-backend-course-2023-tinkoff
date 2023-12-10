package edu.hw9.Task2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlgorithmSearchDirectoryForLength extends RecursiveTask<List<Path>> {
    private static final Logger SEARCH_FOR_LENGTH_LOGGER =
        LogManager.getLogger(AlgorithmSearchDirectoryForLength.class.getName());
    private static final String FILE_ERROR = "error working with files";
    private final Path rootPath;
    private final int directoryLength;

    public AlgorithmSearchDirectoryForLength(Path rootPath, int directoryLength) {
        this.rootPath = rootPath;
        this.directoryLength = directoryLength;
    }

    @Override
    protected List<Path> compute() {
        List<Path> directories = new ArrayList<>();

        if (Files.isDirectory(rootPath)) {
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(rootPath)) {
                List<AlgorithmSearchDirectoryForLength> tasks = new ArrayList<>();

                for (Path p : ds) {
                    if (Files.isDirectory(p)) {
                        tasks.add(new AlgorithmSearchDirectoryForLength(p, directoryLength));
                    }
                }

                invokeAll(tasks);

                for (AlgorithmSearchDirectoryForLength task : tasks) {
                    directories.addAll(task.join());
                }

                long fileCount = Files.list(rootPath).filter(Files::isRegularFile).count();

                if (fileCount > directoryLength) {
                    directories.add(rootPath);
                }
            } catch (IOException e) {
                SEARCH_FOR_LENGTH_LOGGER.info(FILE_ERROR);
                SEARCH_FOR_LENGTH_LOGGER.info(e.getMessage());
                SEARCH_FOR_LENGTH_LOGGER.info(e.getStackTrace());
            }
        }

        return directories;
    }
}
