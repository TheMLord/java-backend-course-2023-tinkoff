package edu.hw1;

import java.util.Arrays;

/**
 * Class with a static method for checking the nesting of arrays.
 */
public final class Task3 {
    /**
     * Class constructor.
     */
    private Task3() {

    }

    /**
     * Method that checks whether one array is nested in another.
     *
     * @param a1 first array.
     * @param a2 second array.
     * @return true if the first array is nested in the second and false in other case.
     */
    public static boolean isNestable(int[] a1, int[] a2) {
        Arrays.sort(a1);
        Arrays.sort(a2);
        return a1[0] > a2[0] && a1[a1.length - 1] < a2[a2.length - 1];
    }
}
