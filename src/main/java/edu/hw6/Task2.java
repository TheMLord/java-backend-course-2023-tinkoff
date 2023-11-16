package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class Task2 {
    private static final Formatter FORMATTER = new Formatter();

    private static final String THIS_NOT_FILE_STATUS = "Copying failed. To copy, you must pass a file to the method";
    private static final String NOT_EXIST_FILE_STATUS = "Copying failed.  You cannot copy a non-existent file";
    private static final String OK_COPY_STATUS = "Successful copying. New file name %s - %s";
    private static final String EXCEPTION_COPY_STATUS = "Copying failed. An exception has occurred - %s";

    private static final String GLOB_COPY_PATTERN = "%s - копия (?)?.%s";

    private static final String FIRST_COPY = "%s - копия.%s";
    private static final String N_COPY = "%s - копия (%d).%s";
    private static final int FIRST_NUMBER_COPY = 1;

    private static final String NAME_EXTENSION_FILE_SEPARATOR = "\\.";
    private static final int FILE_NAME_INDEX = 0;
    private static final int FILE_EXTENSION_INDEX = 1;

    private Task2() {

    }

    public static String clonePath(Path path) {
        if (!Files.exists(path)) {
            return NOT_EXIST_FILE_STATUS;
        }
        if (!Files.isRegularFile(path)) {
            return THIS_NOT_FILE_STATUS;
        }

        try {
            Path newPathCopyFile = getNewPath(path);
            saveCopyfile(path, newPathCopyFile);
            return String.valueOf(FORMATTER.format(OK_COPY_STATUS, path.getFileName(), newPathCopyFile.getFileName()));
        } catch (IOException e) {
            return String.valueOf(FORMATTER.format(EXCEPTION_COPY_STATUS, e.getMessage()));
        }
    }

    private static Path getNewPath(Path path) throws IOException {
        var fileFullName = path.getFileName().toString().split(NAME_EXTENSION_FILE_SEPARATOR);
        var nameFile = fileFullName[FILE_NAME_INDEX];
        var fileExtension = fileFullName[FILE_EXTENSION_INDEX];
        var parentPath = path.getParent();

        try (var ds = Files.newDirectoryStream(
            parentPath,
            String.valueOf(FORMATTER.format(GLOB_COPY_PATTERN, nameFile, fileExtension))
        )) {
            Set<String> setPath = StreamSupport.stream(ds.spliterator(), false)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toSet());

            var copyFilePath = String.valueOf(FORMATTER.format(FIRST_COPY, nameFile, fileExtension));

            if (setPath.contains(copyFilePath)) {
                return Paths.get(parentPath.toString(), copyFilePath);
            }

            int countCopy = FIRST_NUMBER_COPY;
            while (setPath.contains(copyFilePath)) {
                copyFilePath = String.valueOf(FORMATTER.format(N_COPY, nameFile, countCopy, fileExtension));
                countCopy++;
            }
            return Paths.get(parentPath.toString(), copyFilePath);

        } catch (IOException e) {
            throw new IOException(e);
        }

    }

    private static void saveCopyfile(Path prevPath, Path copyPath) throws IOException {
        Files.copy(prevPath, copyPath);
    }
}
