package edu.hw5;

/**
 * Class task5.
 */
public final class Task5 {
    private static final String LICENSE_PLATE = "[А-Я]\\d{3}[А-Я]{2}\\d{2,3}";

    /**
     * Class constructor.
     */
    private Task5() {

    }

    /**
     * Method checks the validity of Russian license plates on cars.
     * A valid car number is considered if it matches the
     * template CNNNCCNNN or CNNNCCNN where C is the capital character from the Russian alphabet,
     * N is a number from the range from 0 to 9.
     *
     * @param plate Russian license plates.
     * @return true if license plates is valid and false in other case.
     */
    public static boolean isValidLicensePlate(String plate) {
        return plate.matches(LICENSE_PLATE);
    }
}
