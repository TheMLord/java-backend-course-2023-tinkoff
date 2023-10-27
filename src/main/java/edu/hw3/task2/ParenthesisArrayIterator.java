package edu.hw3.task2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** most valuable stock.
 * Iterator class by the input list of parentheses.
 */
public final class ParenthesisArrayIterator implements Iterator {
    private final char[] parenthesisArray;
    private int indexArray;

    /**
     * Class constructor.
     *
     * @param parenthesisArray input char array.
     */
    public ParenthesisArrayIterator(char[] parenthesisArray) {
        this.parenthesisArray = parenthesisArray;
    }

    @Override
    public boolean hasNext() {
        return indexArray < parenthesisArray.length;
    }

    @Override
    public Character next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return parenthesisArray[indexArray++];
    }
}
