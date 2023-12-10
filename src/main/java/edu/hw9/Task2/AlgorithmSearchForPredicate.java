package edu.hw9.Task2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Recursive algorithm for searching files by the passed predicate from the specified root to the end,
 * which returns a list of files that fit the passed predicate.
 */
public class AlgorithmSearchForPredicate extends RecursiveTask<List<Path>> {
    private static final Logger ALGORITHM_SEARCH_FOR_PREDICATE_LOGGER =
        LogManager.getLogger(AlgorithmSearchForPredicate.class.getName());
    private static final String WORK_MESSAGE = "do task";

    private final Path rootPath;
    private final Predicate<Path> filePredicate;

    public AlgorithmSearchForPredicate(Path rootPath, Predicate<Path> filePredicate) {
        this.rootPath = rootPath;
        this.filePredicate = filePredicate;
    }

    @Override
    protected List<Path> compute() {
        List<Path> matchingFiles = new ArrayList<>();
        ALGORITHM_SEARCH_FOR_PREDICATE_LOGGER.info(WORK_MESSAGE);

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(rootPath)) {
            List<AlgorithmSearchForPredicate> tasks = new ArrayList<>();

            for (Path p : ds) {
                if (Files.isDirectory(p)) {
                    var task = new AlgorithmSearchForPredicate(p, filePredicate);
                    tasks.add(task);
                    task.fork();
                } else if (filePredicate.test(p)) {
                    matchingFiles.add(p);
                }
            }

            for (AlgorithmSearchForPredicate task : tasks) {
                matchingFiles.addAll(task.join());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return matchingFiles;
    }
}
