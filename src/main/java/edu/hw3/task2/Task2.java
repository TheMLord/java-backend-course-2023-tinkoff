package edu.hw3.task2;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Task2 class.
 */
public final class Task2 {
    private static final char OPEN_PARENTHESIS_CHAR = '(';
    private static final char CLOSED_PARENTHESIS_CHAR = ')';
    private static final String INVALID_LENGTH_ARRAY_ERROR_MESSAGE =
        "The array must have 1 element";
    private static final String INVALID_CHARACTER_ERROR_MESSAGE =
        "The characters in the array must be enclosed in parentheses only";
    private static final String INVALID_PARENTHESIS_ORDER_ERROR_MESSAGE =
        "It is impossible to cluster, the closing bracket cannot be earlier than the opening one";
    private static final String INABILITY_TO_BALANCE_ERROR_MESSAGE =
        "This parenthesis array cannot be balanced";

    private static final int START_COUNT_VALUE = 0;
    private static final int INDEX_PARENTHESIS_ELEMENT = 0;
    private static final int ZERO_COUNT = 0;
    private static final int LENGTH_PARENTHESIS_ARRAY = 1;

    /**
     * class constructor.
     */
    private Task2() {
    }

    /**
     * Method that performs clustering of parentheses.
     *
     * @param parenthesisArray array of strings with one element consisting of opening and closing parentheses.
     * @return clustered list of parentheses.
     * @throws ParenthesisClusteringException clustering error.
     */
    public static String[] clustering(String[] parenthesisArray) throws ParenthesisClusteringException {
        if (!isValidityArray(parenthesisArray)) {
            throw new ParenthesisClusteringException(INVALID_LENGTH_ARRAY_ERROR_MESSAGE);
        }

        var clisterizeList = getClisterizeList(parenthesisArray);

        return castClusterListToArray(clisterizeList);
    }

    /**
     * Method that processes input elements and executes and returns a clustered list.
     *
     * @param parenthesisArray input list.
     * @return clustered list in case of successful processing.
     * @throws ParenthesisClusteringException processing clustered exception.
     */
    private static List<String> getClisterizeList(String[] parenthesisArray) throws ParenthesisClusteringException {
        List<String> clisterizeList = new ArrayList<>();

        int countOpenParenthesis = START_COUNT_VALUE;
        int countClosedParenthesis = START_COUNT_VALUE;

        char[] parenthesisCharArray = parenthesisArray[INDEX_PARENTHESIS_ELEMENT].toCharArray();

        var iterator = new ParenthesisArrayIterator(parenthesisCharArray);

        List<Character> currentClusterElement = new ArrayList<>();

        while (iterator.hasNext()) {
            var element = iterator.next();

            if (!isParenthesisElement(element)) {
                throw new ParenthesisClusteringException(INVALID_CHARACTER_ERROR_MESSAGE);
            }
            if (isInvalidOrderElement(element, countOpenParenthesis)) {
                throw new ParenthesisClusteringException(INVALID_PARENTHESIS_ORDER_ERROR_MESSAGE);
            }

            if (countOpenParenthesis == countClosedParenthesis && countOpenParenthesis != ZERO_COUNT) {
                countOpenParenthesis = START_COUNT_VALUE;
                countClosedParenthesis = START_COUNT_VALUE;

                clisterizeList.add(getCurrentBalanceCluster(
                        currentClusterElement,
                        currentClusterElement.size()
                    )
                );
                currentClusterElement.clear();
            }

            if (element == OPEN_PARENTHESIS_CHAR) {
                countOpenParenthesis++;
            } else {
                countClosedParenthesis++;
            }

            currentClusterElement.add(element);

            if (!iterator.hasNext() && countOpenParenthesis == countClosedParenthesis) {
                clisterizeList.add(getCurrentBalanceCluster(
                        currentClusterElement,
                        currentClusterElement.size()
                    )
                );
                currentClusterElement.clear();

            }
        }

        if (!currentClusterElement.isEmpty()) {
            throw new ParenthesisClusteringException(INABILITY_TO_BALANCE_ERROR_MESSAGE);
        }
        return clisterizeList;
    }

    /**
     * Method that verifies the validity of symbols.
     *
     * @param element current parenthesis.
     * @return true if element is parenthesis.
     */
    private static boolean isParenthesisElement(char element) {
        return element == OPEN_PARENTHESIS_CHAR || element == CLOSED_PARENTHESIS_CHAR;
    }

    /**
     * Method that checks that no closing bracket is placed before the opening one.
     *
     * @param element              current char element.
     * @param countOpenParenthesis number of parentheses that have opened.
     * @return true if element placed in invalid order and false in other case.
     */
    private static boolean isInvalidOrderElement(char element, int countOpenParenthesis) {
        return element == CLOSED_PARENTHESIS_CHAR && countOpenParenthesis == ZERO_COUNT;
    }

    /**
     * Method checks the validity of an incoming array of strings.
     *
     * @param parenthesisArray input array of parentheses.
     * @return false if length array is equals LENGTH_PARENTHESIS_ARRAY
     */
    private static boolean isValidityArray(String @NotNull [] parenthesisArray) {
        return parenthesisArray.length == LENGTH_PARENTHESIS_ARRAY;
    }

    /**
     * Method that forms a clustered string of parentheses.
     *
     * @param currentClusterElement finished clustered element.
     * @param size                  array length.
     * @return string type cluster.
     */
    private static String getCurrentBalanceCluster(List<Character> currentClusterElement, int size) {
        var balancedClusterChar = new char[size];
        for (int i = 0; i < size; i++) {
            balancedClusterChar[i] = currentClusterElement.get(i);
        }
        return new String(balancedClusterChar);
    }

    /**
     * Method converting a list of clustered parentheses to a array of strings.
     */
    private static String[] castClusterListToArray(List<String> clisterizeList) {
        int lengthClusterArray = clisterizeList.size();
        var clusterArrayResult = new String[lengthClusterArray];

        for (int i = 0; i < lengthClusterArray; i++) {
            clusterArrayResult[i] = clisterizeList.get(i);
        }
        return clusterArrayResult;
    }

}
