package edu.project3;

import edu.project3.analyzer.LogAnalyzer;
import edu.project3.markups.ASCIIDoc;
import edu.project3.markups.MarkDown;
import edu.project3.markups.MarkupsLanguage;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Main {
    private static final String PATH_TO_LOG_ARG = "--pathToLog";
    private static final String PATH_TO_SAVE_ARG = "--pathToSave";
    private static final String FROM_ARG = "--from";
    private static final String TO_ARG = "--to";
    private static final String FORMAT_ARG = "--format";

    private static final String TIME_ZONE = "T00:00:00Z";

    private Main() {

    }

    private static Map<String, String> getArguments(String[] arg) {
        Map<String, String> argumentMap = new HashMap<>();
        for (int i = 0; i < arg.length; i += 2) {
            argumentMap.put(arg[i], arg[i + 1]);
        }
        return argumentMap;
    }

    private static List<String> getLogFilesPath(String globPattern) throws IOException {
        List<String> result = new ArrayList<>();

        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + globPattern);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("").toAbsolutePath())) {
            for (Path path : directoryStream) {
                if (matcher.matches(path.getFileName())) {
                    result.add(path.toAbsolutePath().toString());
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }

        return result;
    }

    private static MarkupsLanguage getMarkup(String markup) {
        return (markup.equals("mardown")) ? new MarkDown() : new ASCIIDoc();
    }

    private static OffsetDateTime parseData(String date) {
        return OffsetDateTime.parse(date + TIME_ZONE);
    }

    public static void main(String[] args) throws IOException {
        var programArguments = getArguments(args);
        if (programArguments.containsKey(PATH_TO_LOG_ARG) && programArguments.containsKey(PATH_TO_SAVE_ARG)
            && programArguments.containsKey(FORMAT_ARG)) {

            Optional<OffsetDateTime> from;
            Optional<OffsetDateTime> to;
            List<String> pathLog;
            MarkupsLanguage markupsLanguage = getMarkup(programArguments.get(FORMAT_ARG));

            if (!programArguments.get(PATH_TO_LOG_ARG).startsWith("http")) {
                pathLog = getLogFilesPath(programArguments.get(PATH_TO_LOG_ARG));
            } else {
                pathLog = List.of(programArguments.get(PATH_TO_LOG_ARG));
            }
            var pathToSave = Path.of(programArguments.get(PATH_TO_SAVE_ARG));

            if (programArguments.containsKey(FROM_ARG)) {
                from = Optional.of(parseData(programArguments.get(FROM_ARG)));
            } else {
                from = Optional.empty();
            }

            if (programArguments.containsKey(TO_ARG)) {
                to = Optional.of(parseData(programArguments.get(TO_ARG)));
            } else {
                to = Optional.empty();
            }

            var logAnalyzer = new LogAnalyzer(pathLog, pathToSave, markupsLanguage, from, to);
            logAnalyzer.analyzeLogs();
        } else {
            throw new RuntimeException("Invalid arguments");
        }

    }

}
