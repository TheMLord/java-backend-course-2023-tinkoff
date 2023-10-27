package edu.hw3.task5;

import java.util.Comparator;

/**
 * Class contact comparator.
 */
public class ContactComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact o1, Contact o2) {

        if (o1.getSurname() == null) {
            if (o2.getSurname() == null) {
                return o1.getName().compareTo(o2.getName());
            }
            return o1.getName().compareTo(o2.getSurname());
        }

        if (o2.getSurname() == null) {
            return o1.getSurname().compareTo(o2.getName());
        }

        return o1.getSurname().compareTo(o2.getSurname());
    }
}
