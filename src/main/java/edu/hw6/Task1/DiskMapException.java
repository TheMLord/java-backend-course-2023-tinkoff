package edu.hw6.Task1;

/**
 * Class of errors that can occur when the DiskMap program is running.
 */
public class DiskMapException extends RuntimeException {
    public DiskMapException(String message) {
        super(message);
    }
}
