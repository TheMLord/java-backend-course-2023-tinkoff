package edu.hw3;

/**
 * Task1 class.
 */
public final class Task1 {
    private static final int ASCII_START_LOWERCASE_CHAR = 97;

    private static final int ASCII_START_UPPERCASE_CHAR = 65;

    private static final int POSITION_ACCOUNTING = 1;

    private static final int NUMBER_LATIN_LETTER = 26;

    /**
     * Class constructor.
     */
    private Task1() {

    }

    /**
     * Method that encodes a string using the atbash cipher.
     *
     * @param inputStringToEncode the string to be encoded.
     * @return atbash encoded string.
     */
    public static String atBash(String inputStringToEncode) {
        char[] charStringToEncode = inputStringToEncode.toCharArray();

        var charEncodeResult = processingCharArray(charStringToEncode);

        return encodeCharToString(charEncodeResult);
    }

    /**
     * Utility method for processing input characters.
     *
     * @param charStringToEncode input array of char string.
     * @return processed array.
     */
    private static char[] processingCharArray(char[] charStringToEncode) {
        int arraySize = charStringToEncode.length;
        var charResultEncode = new char[arraySize];

        for (int i = 0; i < arraySize; i++) {
            if (!Character.isLetter(charStringToEncode[i])) {
                charResultEncode[i] = charStringToEncode[i];
                continue;
            }
            charResultEncode[i] = getEncodeCharCode(charStringToEncode[i]);
        }
        return charResultEncode;
    }

    /**
     * Method that gives the mirror symbol.
     *
     * @param ch current char.
     * @return mirror char code symbol.
     */
    private static char getEncodeCharCode(char ch) {
        return (Character.isLowerCase(ch)) ? getCharacterMirrorPosition(ch, ASCII_START_LOWERCASE_CHAR)
            : getCharacterMirrorPosition(ch, ASCII_START_UPPERCASE_CHAR);
    }

    /**
     * Method that calculating the code of a new mirror symbol
     *
     * @param ch    current char symbol.
     * @param start start ASCII position in LOWER or UPPER case letter.
     * @return mirror code symbol.
     */
    private static char getCharacterMirrorPosition(char ch, int start) {
        int offset = ch - start;
        int codeEncodeCharOffset = (NUMBER_LATIN_LETTER - offset) - POSITION_ACCOUNTING;
        return (char) (codeEncodeCharOffset + start);
    }

    /**
     * Method that converted char array to string.
     *
     * @param encodeCharArray result char array
     * @return result atbash encode string.
     */
    private static String encodeCharToString(char[] encodeCharArray) {
        return new String(encodeCharArray);
    }
}
