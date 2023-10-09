package edu.hw1;

/**
 * Class with static methods performing cyclic shifts.
 */
public final class Task7 {
    private static final int FOUNDATION_BINARY_SYSTEM = 2;
    private static final int START_INDEX = 0;
    private static final int LOWER_BOUND_POSITIVE_NUMBERS = 0;

    /**
     * Class constructor.
     */
    private Task7() {

    }

    /**
     * Method for performing a cyclic shift to the right.
     *
     * @param n     argument is an integer.
     * @param shift size of the cyclic shift.
     * @return cyclically shifted number.
     */
    public static int rotateRight(int n, int shift) throws RuntimeException {
        return cyclicShift(n, shift, true);
    }

    /**
     * Method for performing a cyclic shift to the left.
     *
     * @param n     argument is an integer.
     * @param shift size of the cyclic shift.
     * @return cyclically shifted number.
     */
    public static int rotateLeft(int n, int shift) throws RuntimeException {
        return cyclicShift(n, shift, false);

    }

    /**
     * Service method for performing a cyclic shift to the left.
     *
     * @param n         argument is an integer.
     * @param shift     size of the cyclic shift.
     * @param shiftFlag value of the cyclic shift.
     * @return cyclically shifted number.
     * @throws RuntimeException if shift less then zero.
     */
    private static int cyclicShift(int n, int shift, boolean shiftFlag) throws RuntimeException {
        if (shift < 0) {
            throw new RuntimeException("Shift can't be less than zero");
        }
        if (n < 0) {
            throw new RuntimeException("N can't be less than zero");
        }

        String binaryN = Integer.toBinaryString(Math.abs(n));
        int sizeNBinary = binaryN.length();
        int cyclicShift = (shift > sizeNBinary) ? shift % sizeNBinary : shift;

        String shiftedBinaryN = (shiftFlag) ? binaryN.substring(sizeNBinary - cyclicShift)
            + binaryN.substring(START_INDEX, sizeNBinary - cyclicShift)
            : binaryN.substring(cyclicShift) + binaryN.substring(START_INDEX, cyclicShift);

        return Integer.parseInt(shiftedBinaryN, FOUNDATION_BINARY_SYSTEM);
    }

}
