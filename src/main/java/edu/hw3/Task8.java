package edu.hw3;

import java.util.Iterator;
import java.util.List;

/**
 * Class reversed iterator.
 *
 * @param <T> type collection.
 */
public final class Task8<T> implements Iterator<T> {
    private final List<T> collection;
    private int currentIndex;

    /**
     * Class constructor.
     *
     * @param collection collection of type T.
     */
    public Task8(List<T> collection) {
        this.collection = collection;
        this.currentIndex = collection.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return currentIndex >= 0;
    }

    @Override
    public T next() {
        T element = collection.get(currentIndex);
        currentIndex--;
        return element;
    }
}
