package edu.hw5;

import java.util.regex.Pattern;

/**
 * Class task6.
 */
public final class Task6 {
    /**
     * Class constructor.
     */
    private Task6() {

    }

    /**
     * Method checks that the given string S is a subsequence of another string T.
     *
     * @param s the main sequence
     * @param t possible subsequence
     * @return true if t is a subsequence of s
     */
    public static boolean isSubsequence(String s, String t) {
        return Pattern.compile(t).matcher(s).find();
    }
}
