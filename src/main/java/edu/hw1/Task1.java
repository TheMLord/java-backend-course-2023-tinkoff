package edu.hw1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class with a static method for calculating the length of a video.
 */
public final class Task1 {
    private static final int SECONDS_LIMIT = 60;
    private static final int COUNT_SECOND_TO_MINUTES = 60;
    private static final int WRONG_VALUE = -1;
    private static final int SIZE_INPUT_VALUES = 2;
    private static final String INPUT_SEPARATOR = ":";
    private static final int INDEX_SECONDS = 1;
    private static final int INDEX_MINUTES = 0;
    private static final String WRONG_CHAR_MINUS = "-";
    private static final String WRONG_CHAR_SPACE = " ";

    /**
     * Class constructor.
     */
    private Task1() {

    }

    /**
     * Method for converting video length from mm:ss format to seconds.
     *
     * @param formattedVideoLength video length in mm:ss format.
     * @return video length in seconds.
     */
    public static int minutesToSeconds(String formattedVideoLength) {
        if (formattedVideoLength == null
            || formattedVideoLength.isEmpty()
            || formattedVideoLength.contains(WRONG_CHAR_MINUS)
            || formattedVideoLength.contains(WRONG_CHAR_SPACE)) {
            return WRONG_VALUE;
        }
        List<String> splitTime = Arrays.asList(formattedVideoLength.split(INPUT_SEPARATOR));
        try {
            List<Integer> timeValue = new ArrayList<>();
            splitTime.forEach(x -> timeValue.add(Integer.parseInt(x)));
            if (splitTime.size() != SIZE_INPUT_VALUES || timeValue.get(INDEX_SECONDS) >= SECONDS_LIMIT) {
                return WRONG_VALUE;
            }
            return timeValue.get(INDEX_MINUTES) * COUNT_SECOND_TO_MINUTES + timeValue.get(INDEX_SECONDS);
        } catch (NumberFormatException e) {
            return WRONG_VALUE;
        }
    }

}
