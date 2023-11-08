package edu.hw3;

import java.util.Comparator;

/**
 * Class implements comparator for TreeMap that supports null.
 */
public class Task7<T> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return 1;
        } else if (o1 instanceof Comparable && o2 instanceof Comparable) {
            return ((Comparable) o1).compareTo(o2);
        } else {
            throw new IllegalArgumentException("Objects are not comparable.");
        }
    }
}
