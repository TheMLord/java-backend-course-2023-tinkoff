package edu.hw5;

import java.util.regex.Pattern;

/**
 * Class Task8.
 */
public final class Task8 {
    private static final Pattern ODD_LENGTH_PATTERN = Pattern.compile("^([01]{2})*([01])$");
    private static final Pattern ODD_LENGTH_IF_START_0_AND_EVEN_IF_START_1_PATTERN =
        Pattern.compile("^0([01]{2})*$|^1([01]{2})*[01]$");
    private static final Pattern COUNT_ZERO_IS_MULTIPLE_THREE =
        Pattern.compile("^((1*01*){3})+$");

    private static final Pattern ANY_STRING_OTHER_THAN_11_OR_111 =
        Pattern.compile("^(?!1{2,3}$)[01]+");

    private static final Pattern EACH_ODD_NUMBER_1_PATTERN =
        Pattern.compile("^(1[01]*)+$");

    /**
     * Class constructor.
     */
    private Task8() {

    }

    /**
     * Method checks that the sequence of 0 and 1 has an odd length.
     * Task 8.1
     *
     * @param s a sequence of 0 and 1.
     * @return true if it matches the pattern , and false in the other case.
     */
    public static boolean isOddLengthSequence(String s) {
        return ODD_LENGTH_PATTERN.matcher(s).matches();
    }

    /**
     * Method checks that the sequence starts with 0 and has an odd length, or starts with 1 and has an even length.
     * Task 8.2
     *
     * @param s a sequence of 0 and 1.
     * @return true if it matches the pattern , and false in the other case.
     */
    public static boolean isOddLengthIfSequenceStartWithZeroOrEvenInOther(String s) {
        return ODD_LENGTH_IF_START_0_AND_EVEN_IF_START_1_PATTERN.matcher(s).matches();
    }

    /**
     * Method checks that the number of zeros in the sequence is a multiple of three.
     * Task 8.3
     *
     * @param s a sequence of 0 and 1.
     * @return true if it matches the pattern , and false in the other case.
     */
    public static boolean isCountZeroMultipleThree(String s) {
        return COUNT_ZERO_IS_MULTIPLE_THREE.matcher(s).matches();
    }

    /**
     * Method checks whether the sequence is 11 or 111.
     * Task 8.4
     *
     * @param s a sequence of 0 and 1.
     * @return true if it matches the pattern , and false in the other case.
     */
    public static boolean isAnySequenceOther11Or111(String s) {
        return ANY_STRING_OTHER_THAN_11_OR_111.matcher(s).matches();
    }

    /**
     * Method that verifies that each odd character in the sequence is 1
     * Task 8.5
     *
     * @param s a sequence of 0 and 1.
     * @return true if it matches the pattern , and false in the other case.
     */
    public static boolean isAnyOddNumberIs1(String s) {
        return EACH_ODD_NUMBER_1_PATTERN.matcher(s).matches();
    }

}
