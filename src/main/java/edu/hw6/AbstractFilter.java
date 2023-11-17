package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

/**
 * Task3 interface. Chain Filter API for DirectoryStream Path.
 */
public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    String GLOB_STRING_FORMAT = "glob:";
    String ANY_REGEX = ".*";

    /**
     * Checking for regular file.
     *
     * @param path the path to the file.
     */
    static AbstractFilter regularFile(Path path) {
        return Files.isRegularFile(path) ? path::equals : null;
    }

    /**
     * Checking for readable file.
     *
     * @param path the path to the file.
     */
    static AbstractFilter readable(Path path) {
        return Files.isReadable(path) ? path::equals : null;
    }

    /**
     * Checking for writable file.
     *
     * @param path the path to the file.
     */
    static AbstractFilter writable(Path path) {
        return Files.isReadable(path) ? path::equals : null;
    }

    /**
     * Checking size
     */
    static AbstractFilter largerThan(long size) {
        return path -> Files.size(path) >= size;

    }

    /**
     * Checking magic number.
     */
    static AbstractFilter magicNumber(byte... magicBytes) {
        return path -> {
            try {
                byte[] fileBytes = Files.readAllBytes(path);
                if (fileBytes.length >= magicBytes.length) {
                    for (int i = 0; i < magicBytes.length; i++) {
                        if (fileBytes[i] != magicBytes[i]) {
                            return false;
                        }
                    }
                    return true;
                }
            } catch (IOException e) {
                return false;
            }
            return false;
        };
    }

    /**
     * Checking glob matches.
     */
    static AbstractFilter globMatches(String glob) {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(GLOB_STRING_FORMAT + glob);
        return path -> pathMatcher.matches(path.getFileName());
    }

    /**
     * Checking regex contains.
     */
    static AbstractFilter regexContains(String regex) {
        return path -> path.getFileName().toString().matches(ANY_REGEX + regex + ANY_REGEX);
    }

    default AbstractFilter and(AbstractFilter other) {
        return path -> this.accept(path) && other.accept(path);
    }
}
