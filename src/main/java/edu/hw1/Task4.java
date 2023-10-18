package edu.hw1;

/**
 * Class with a static method for correcting a string.
 */
public final class Task4 {
    private static final int MIN_STRING_SIZE = 1;

    /**
     * Class constructor.
     */
    private Task4() {

    }

    /**
     * Method correcting the string.
     *
     * @param brokenString argument is a broken string that needs to be fixed.
     * @return corrected string.
     */
    public static String fixString(String brokenString) {
        int lengthString = brokenString.length();

        if (lengthString <= MIN_STRING_SIZE) {
            return brokenString;
        }

        char[] brokenArrayChar = brokenString.toCharArray();
        char[] fixArrayChar = new char[lengthString];

        if (lengthString % 2 != 0) {
            fixArrayChar[lengthString - 1] = brokenArrayChar[lengthString - 1];
            lengthString -= 1;
        }
        for (int i = 1; i < lengthString; i += 2) {
            fixArrayChar[i - 1] = brokenArrayChar[i];
            fixArrayChar[i] = brokenArrayChar[i - 1];
        }
        return new String(fixArrayChar);
    }

}
