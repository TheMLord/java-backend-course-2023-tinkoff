package edu.hw5;

import java.util.regex.Pattern;

/**
 * Class task7.
 */
public final class Task7 {

    private static final Pattern THREE_LENGTH_THIRD_ZERO_PATTERN = Pattern.compile("[0-1]{2}0[0-1]*");
    private static final Pattern BEGIN_AND_END_SAME_NUMBER_PATTERN = Pattern.compile("^[0-1]$|^0[0-1]*0$|^1[0-1]*1$");
    private static final Pattern LENGTH_OF_LEAST_ONE_AND_NO_MORE_THREE_NUMBER_PATTERN = Pattern.compile("[0-1]{1,3}");

    /**
     * Class constructor.
     */
    private Task7() {

    }

    /**
     * Method checks that the sequence is at least 3 characters, with the third character being 0.
     *
     * @param s sequence of 0 and 1.
     * @return true if it matches the pattern , and false in the other case.
     */
    public static boolean isStringHaveLengthNotLessThenThreeAndThirdIsZero(String s) {
        return THREE_LENGTH_THIRD_ZERO_PATTERN.matcher(s).matches();
    }

    /**
     * Method checks that the sequence starts and ends with the same character.
     *
     * @param s sequence of 0 and 1.
     * @return true if it matches the pattern , and false in the other case.
     */
    public static boolean isStringStartAndEntSameNumber(String s) {
        return BEGIN_AND_END_SAME_NUMBER_PATTERN.matcher(s).matches();
    }

    /**
     * Method checks that the sequence length is at least 1 and no more
     *
     * @param s sequence of 0 and 1.
     * @return true if it matches the pattern , and false in the other case.
     */
    public static boolean isStringWithLengthOfLeastOneAndNoMoreThreeNumber(String s) {
        return LENGTH_OF_LEAST_ONE_AND_NO_MORE_THREE_NUMBER_PATTERN.matcher(s).matches();
    }
}
