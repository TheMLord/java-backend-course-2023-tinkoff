package edu.hw1;

import java.util.Arrays;

/**
 * Class for finding the Kaprekar number.
 */
public final class Task6 {
    private static final int K_NUMBER = 6174;
    private static final int LOWER_BOUND_NUMBER = 1000;
    private static final int UPPER_BOUND_NUMBER = 10000;
    private static final int SIZE_STRING = 4;
    private static final int INSIGNIFICANT_ZEROS = 0;

    /**
     * Class constructor.
     */
    private Task6() {

    }

    /**
     * Method for finding the Kaprekar number.
     *
     * @param n four-digit number greater than 1000.
     * @return Kaprekar number.
     */
    public static int k(int n) {
        int countSteps = 0;
        if (Math.abs(n) <= LOWER_BOUND_NUMBER || Math.abs(n) >= UPPER_BOUND_NUMBER) {
            return -1;
        }
        if (isAllNumberSame(n)) {
            return -1;
        }
        return getConstantK(String.valueOf(Math.abs(n)), countSteps);
    }

    /**
     * Method that checks whether all the digits in a number are the same.
     *
     * @param n four-digit number greater than 1000.
     * @return true if all the digits in the number are the same and false in other case.
     */
    private static boolean isAllNumberSame(int n) {
        String numberStr = Integer.toString(n);
        char firstDigit = numberStr.charAt(0);

        for (int i = 1; i < SIZE_STRING; i++) {
            if (numberStr.charAt(i) != firstDigit) {
                return false;
            }
        }
        return true;
    }

    /**
     * Recursive method for finding the Kaprekar number.
     *
     * @param n     four-digit number greater than 1000.
     * @param steps count of steps to get the Kaprekar number.
     * @return count steps to get the Kaprekar number.
     */
    private static int getConstantK(String n, int steps) {
        if (Integer.parseInt(n) == K_NUMBER) {
            return steps;
        }
        char[] charN = n.toCharArray();
        Arrays.sort(charN);

        int minNumber = Integer.parseInt(new String(charN));
        int maxNumber = Integer.parseInt(
            new StringBuffer(
                new String(charN))
                .reverse()
                .toString()
        );
        int newNumber = maxNumber - minNumber;

        String resultN = String.valueOf(newNumber);
        if (resultN.length() == SIZE_STRING) {
            return getConstantK(resultN, steps + 1);
        }
        StringBuilder nStringBuilder = new StringBuilder(resultN);
        while (nStringBuilder.length() != SIZE_STRING) {
            nStringBuilder.append(INSIGNIFICANT_ZEROS);
        }
        return getConstantK(nStringBuilder.toString(), steps + 1);
    }

}
