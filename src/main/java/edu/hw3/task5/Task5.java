package edu.hw3.task5;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Task5
 */
public final class Task5 {
    private static final String SPACE_SEPARATOR_NAME = " ";
    private static final int FULL_CONTACT_LENGTH = 2;
    private static final int INDEX_NAME_FULL_CONTACT = 0;
    private static final int INDEX_SURNAME_FULL_CONTACT = 1;
    private static final int INDEX_NAME_INCOMPLETE_CONTACT = 0;

    /**
     * Class constructor.
     */
    private Task5() {

    }

    /**
     * Method that returns a sorted list of objects of type Contact.
     *
     * @param namesArray array of strings with names.
     * @param sortOrder  sorting rule.
     * @return sorted list of objects of type Contact.
     */
    public static List<Contact> parseContacts(String[] namesArray, SortOrder sortOrder) {
        if (isArrayNull(namesArray)) {
            return null;
        }

        var contactComparator = new ContactComparator();

        List<Contact> contacts = getContact(namesArray);

        if (sortOrder.equals(SortOrder.ASC)) {
            return contacts.stream().sorted(contactComparator).toList();
        }
        return contacts.stream().sorted(contactComparator.reversed()).toList();
    }

    /**
     * Method of processing the list of names.
     *
     * @param names array of names.
     * @return list of contact objects without sorting.
     */
    private static List<Contact> getContact(String[] names) {
        List<Contact> contactsBook = new ArrayList<>();
        for (var people : names) {

            var peopleContact = people.split(SPACE_SEPARATOR_NAME);
            if (peopleContact.length == FULL_CONTACT_LENGTH) {
                contactsBook.add(new Contact(
                    peopleContact[INDEX_SURNAME_FULL_CONTACT],
                    peopleContact[INDEX_NAME_FULL_CONTACT]
                ));
            } else {
                contactsBook.add(new Contact(peopleContact[INDEX_NAME_INCOMPLETE_CONTACT]));
            }

        }
        return contactsBook;
    }

    /**
     * Method that verifies the correctness of the input data.
     *
     * @param names array of names.
     * @return true if data invalid and false in other case.
     */
    private static boolean isArrayNull(String[] names) {
        return names == null;
    }
}
