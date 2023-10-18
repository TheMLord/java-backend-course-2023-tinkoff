package edu.hw1;

/**
 * Class with a static method for checking a number or its ancestors for a palindrome.
 */
public final class Task5 {
    private final static int BOUNDARY_EXISTENCE_PALINDROME = 10;

    /**
     * Class constructor.
     */
    private Task5() {

    }

    /**
     * Method for checking a number or its descendant  for a palindrome.
     *
     * @param n the argument that is checked for a palindrome.
     */
    public static boolean isPalindromeDescendant(int n) {
        int value = Math.abs(n);

        if (value <= BOUNDARY_EXISTENCE_PALINDROME || Task2.countDigits(value) % 2 != 0) {
            return false;
        }
        return isPalindrome(value) || isPalindromeDescendant(getDescendant(value));
    }

    /**
     * Method for searching descendant.
     *
     * @param n the argument from which descendant is being searched.
     * @return descendant of the argument.
     */
    private static int getDescendant(Integer n) {
        var nStr = String.valueOf(n);
        var descendantN = new StringBuilder();
        for (int i = 0; i < nStr.length() - 1; i += 2) {
            descendantN.append(
                Character.getNumericValue(nStr.charAt(i))
                    + Character.getNumericValue(nStr.charAt(i + 1))
            );
        }
        return Integer.parseInt(descendantN.toString());
    }

    /**
     * Service method for checking a number for a palindrome.
     *
     * @param n the argument that is checked for a palindrome.
     * @return true if n is palindrome and false in other case.
     */
    private static boolean isPalindrome(int n) {
        var numStr = Integer.toString(n);
        var reversedStr = new StringBuilder(numStr).reverse().toString();
        return numStr.equals(reversedStr);
    }

}
