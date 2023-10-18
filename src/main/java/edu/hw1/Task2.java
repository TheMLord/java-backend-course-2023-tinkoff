package edu.hw1;

/**
 * Class with a static method for counting digits in a number.
 */
public final class Task2 {
    /**
     * Class constructor.
     */
    private Task2() {

    }

    /**
     * Method for determining the number of digits in a number.
     *
     * @param number an argument representing an integer.
     * @return the quantity of digits in the argument.
     */
    public static int countDigits(int number) {
        return (number == 0) ? 1 : (int) Math.floor(
            Math.log10(
                Math.abs(number)
            )
        ) + 1;
    }

}
