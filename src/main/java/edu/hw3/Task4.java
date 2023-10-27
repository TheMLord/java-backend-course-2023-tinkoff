package edu.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Task4.
 */
public final class Task4 {
    private static final int ARABIC_ONE = 1;
    private static final int ARABIC_FIVE = 5;
    private static final int ARABIC_TEN = 10;
    private static final int ARABIC_FIFTY = 50;
    private static final int ARABIC_HUNDRED = 100;
    private static final int ARABIC_FIVE_HUNDRED = 500;
    private static final int ARABIC_ONE_THOUSAND = 1000;

    private static final Map<Integer, String> DECIMAL_TO_ROMAN_MAP = new HashMap<>() {{
        put(ARABIC_ONE, "Ⅰ");
        put(ARABIC_FIVE, "Ⅴ");
        put(ARABIC_TEN, "Ⅹ");
        put(ARABIC_FIFTY, "Ⅼ");
        put(ARABIC_HUNDRED, "Ⅽ");
        put(ARABIC_FIVE_HUNDRED, "D");
        put(ARABIC_ONE_THOUSAND, "Ⅿ");
    }};

    private static final int LOWER_BOUND_ROMAN_NUMERALS = 0;
    private static final int UPPER_BOUND_ROMAN_NUMERALS = 3999;
    private static final int BASE_DECIMAL_SYSTEM = 10;
    private static final int END_VALUE_NUMBER = 0;

    private static final int EDGE_CASE_ONE = 1;
    private static final int EDGE_CASE_FOUR = 4;
    private static final int EDGE_CASE_FIVE = 5;
    private static final int EDGE_CASE_NINE = 9;
    private static final int EDGE_CASE_TEN = 10;

    private static final int STEP_DIGIT = 1;

    private static final String EMPTY_RESULT = null;
    private static final String RESULT_SEPARATOR = "";

    /**
     * Class constructor.
     */
    private Task4() {
    }

    /**
     * Method that converts an Arabic number up to 3999 to a Roman one.
     *
     * @param arabicNumber arabic integer up to 3999.
     * @return arabic number in Roman representation.
     */
    public static String convertToRoman(int arabicNumber) {
        if (arabicNumber <= LOWER_BOUND_ROMAN_NUMERALS || arabicNumber > UPPER_BOUND_ROMAN_NUMERALS) {
            return EMPTY_RESULT;
        }
        List<Integer> decompositionNumber = getDecompositionNumber(arabicNumber);
        int numberDigit = decompositionNumber.size() - 1;

        return getRomanRepresentation(decompositionNumber, numberDigit);
    }

    /**
     * Method for processing an incoming Arabic number and its conversion to Roman digit by digit
     *
     * @param decompositionNumber splitting a number into digits.
     * @param numberDigit         maximum degree of digit of a number in the decimal system
     * @return converted Roman number from Arabic.
     */
    private static String getRomanRepresentation(List<Integer> decompositionNumber, int numberDigit) {
        List<String> convertResult = new ArrayList<>();
        int currentDigit = numberDigit;
        for (var number : decompositionNumber) {

            int currentNumber = number;

            while (currentNumber != END_VALUE_NUMBER) {
                switch (currentNumber) {
                    case EDGE_CASE_FOUR -> {
                        convertResult.add(
                            addRomanNumber(EDGE_CASE_ONE, currentDigit)
                        );
                        convertResult.add(
                            addRomanNumber(EDGE_CASE_FIVE, currentDigit)
                        );
                    }
                    case EDGE_CASE_FIVE -> {
                        convertResult.add(
                            addRomanNumber(EDGE_CASE_FIVE, currentDigit)
                        );
                    }
                    case EDGE_CASE_NINE -> {
                        convertResult.add(
                            addRomanNumber(EDGE_CASE_ONE, currentDigit)
                        );
                        convertResult.add(
                            addRomanNumber(EDGE_CASE_TEN, currentDigit)
                        );
                    }
                    default -> {
                        if (currentNumber > EDGE_CASE_FIVE) {
                            convertResult.add(
                                addRomanNumber(EDGE_CASE_FIVE, currentDigit)
                            );
                            currentNumber -= EDGE_CASE_FIVE;
                        } else {
                            convertResult.add(
                                addRomanNumber(EDGE_CASE_ONE, currentDigit)
                            );
                            currentNumber--;
                        }
                        continue;
                    }
                }
                currentNumber = 0;

            }
            currentDigit -= STEP_DIGIT;
        }
        return getStringRomanRepresentation(convertResult);
    }

    /**
     * Utility method that adds a new Roman numeral.
     *
     * @param currentNumber arabic number.
     * @param currentDigit  current degree of the number.
     * @return string representation of an Arabic number in the Roman system.
     */
    private static String addRomanNumber(int currentNumber, int currentDigit) {
        return DECIMAL_TO_ROMAN_MAP.get(
            currentNumber * (int) (Math.pow(BASE_DECIMAL_SYSTEM, currentDigit))
        );
    }

    /**
     * Method that forms the final number conversion response
     *
     * @param convertResult list of Roman numbers in the correct order.
     * @return converted string from the list that is the result of translation from the Arabic system to the Roman one.
     */
    private static String getStringRomanRepresentation(List<String> convertResult) {
        return String.join(RESULT_SEPARATOR, convertResult);
    }

    /**
     * Method dividing the Arabic number into units, tens, hundreds, etc.
     *
     * @param number arabic number.
     * @return decomposition number list.
     */
    private static List<Integer> getDecompositionNumber(int number) {
        int currentNumber = number;
        List<Integer> decompositionNumberList = new ArrayList<>();
        while (currentNumber != END_VALUE_NUMBER) {
            decompositionNumberList.add(currentNumber % BASE_DECIMAL_SYSTEM);
            currentNumber /= BASE_DECIMAL_SYSTEM;
        }
        return decompositionNumberList.reversed();
    }

}
