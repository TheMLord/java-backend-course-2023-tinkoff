package edu.hw5;

/**
 * Class Task4
 */
public final class Task4 {
    private static final String PASSWORD_REGEX = ".*[~!@#$%^&*|]+.*";

    /**
     * Class constructor.
     */
    private Task4() {

    }

    /**
     * The method checks that the password contains at least 1 of the characters ~ ! @ # $ % ^ & * |.
     * Can return true even if only 1 character from this set.
     *
     * @param password any password
     * @return true if the password contains at least 1 character from the set.
     */
    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }
}
