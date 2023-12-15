package edu.hw8.Task3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class hack password md5.
 */
public final class HackPassword {
    private static final String PASSWORD_CHARACTER = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final int minLengthPassword;
    private final int maxLengthPassword;
    private final ExecutorService executor;
    private final Map<String, String> encodedPassword;
    private final Map<String, String> dencodedPassword;

    /**
     * Class constructor.
     *
     * @param encodedPassword database of passwords in a dictionary, where the key is a hash and the value
     *                        is the username.
     * @param countThread     count threads.
     * @param minLen          minimum length password.
     * @param maxLen          maximum length password.
     */
    public HackPassword(Map<String, String> encodedPassword, int countThread, int minLen, int maxLen) {
        this.minLengthPassword = minLen;
        this.maxLengthPassword = maxLen;

        this.encodedPassword = encodedPassword;

        executor = Executors.newFixedThreadPool(countThread);

        dencodedPassword = new ConcurrentHashMap<>();
    }

    /**
     * Method trying to hack a database with md5 encrypted passwords.
     *
     * @return decrypted passwords map.
     */
    public Map<String, String> hackDatabasePassword() throws InterruptedException {
        for (int length = minLengthPassword; length <= maxLengthPassword; length++) {
            int currentLength = length;
            executor.submit(() -> nextPassword("", currentLength));
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        return dencodedPassword;
    }

    /**
     * Recursive method that generates the next password.
     * The method collects passwords for the current length,
     * converts it to md5 and checks if there is one in the database,
     * after which it enters the decrypted password,
     * if found.
     *
     * @param currentPassword the current generated password
     * @param length          current password length.
     */
    private void nextPassword(String currentPassword, int length) {
        if (length == 0) {
            var md5Password = MD5.encryptionMD5(currentPassword);
            String username = encodedPassword.get(md5Password);
            if (username != null) {
                dencodedPassword.put(username, currentPassword);
            }
            return;
        }

        for (int i = 0; i < PASSWORD_CHARACTER.length(); i++) {
            nextPassword(currentPassword + PASSWORD_CHARACTER.charAt(i), length - 1);
        }
    }

}

