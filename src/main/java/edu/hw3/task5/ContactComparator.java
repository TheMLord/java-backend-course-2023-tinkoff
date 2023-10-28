package edu.hw3.task5;

import java.util.Comparator;

/**
 * Class contact comparator.
 */
public class ContactComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact o1, Contact o2) {
        String surnameO1 = o1.getSurname();
        String nameO1 = o1.getName();

        String surnameO2 = o2.getSurname();
        String nameO2 = o2.getName();

        if (surnameO1 != null && surnameO2 != null) {
            int compare = surnameO1.compareTo(surnameO2);
            return (compare == 0) ? nameO1.compareTo(nameO2) : compare;

        }

        if (surnameO1 == null && surnameO2 == null) {
            return nameO1.compareTo(nameO2);
        }

        if (surnameO1 == null && surnameO2 != null) {
            return nameO1.compareTo(surnameO2);
        } else {
            return surnameO1.compareTo(nameO2);
        }

    }
}
