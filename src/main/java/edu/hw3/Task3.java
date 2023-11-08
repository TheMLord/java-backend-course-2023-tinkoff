package edu.hw3;

import java.util.HashMap;
import java.util.Map;

/**
 * Class with method that searches for the frequency of words in the list.
 */
public final class Task3 {
    private static final int STEP_FREQ = 1;
    private static final int INITIAL_VALUE = 1;

    /**
     * Class constructor.
     */
    private Task3() {

    }

    /**
     * Method that searches for the frequency of words in an array of words.
     *
     * @param listWords array with word.
     * @return dictionary with word frequency.
     */
    public static <T> Map<T, Integer> freqDict(T[] listWords) {
        Map<T, Integer> freqMap = new HashMap<>();

        for (var symbol : listWords) {
            if (freqMap.containsKey(symbol)) {
                freqMap.put(
                    symbol,
                    freqMap.get(symbol) + STEP_FREQ
                );
            } else {
                freqMap.put(symbol, INITIAL_VALUE);
            }
        }
        return freqMap;
    }
}
