package edu.hw9.Task2;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ForkJoinPool;

@SuppressWarnings({"MagicNumber"})
public final class Main {

    private Main() {

    }

    public static void main(String[] args) {
        try (var pw = new PrintWriter(System.out)) {
            Path rootPath = Paths.get(args[0]);
            int directoryLengthThreshold = 4;

            AlgorithmSearchDirectoryForLength
                searchDirectoryForLength = new AlgorithmSearchDirectoryForLength(rootPath, directoryLengthThreshold);

            AlgorithmSearchForPredicate
                searchForPredicate =
                new AlgorithmSearchForPredicate(
                    rootPath,
                    path -> Files.isRegularFile(path) && path.toString().endsWith(".xml")
                );

            try (var fjLength = new ForkJoinPool()) {

                var dirs = fjLength.invoke(searchDirectoryForLength);

                dirs.forEach(pw::println);
            }

            try (var flPredicate = new ForkJoinPool()) {
                var files = flPredicate.invoke(searchForPredicate);
                files.forEach(pw::println);
            }
        }

    }
}
