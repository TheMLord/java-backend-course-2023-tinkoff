package edu.hw9.Task2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class AlgorithmSearchForPredicate extends RecursiveTask<List<Path>> {
    private final Path rootPath;
    private final Predicate<Path> filePredicate;

    public AlgorithmSearchForPredicate(Path rootPath, Predicate<Path> filePredicate) {
        this.rootPath = rootPath;
        this.filePredicate = filePredicate;
    }

    @Override
    protected List<Path> compute() {
        List<Path> matchingFiles = new ArrayList<>();

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
