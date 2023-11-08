package edu.hw3.task5;

/**
 * Class people contact in contactBook.
 */
public class Contact {
    private final String surname;
    private final String name;

    /**
     * Constructor full contact.
     */
    public Contact(String surname, String name) {
        this.surname = surname;
        this.name = name;
    }

    /**
     * Constructor incomplete contact.
     */
    public Contact(String name) {
        this.surname = null;
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (this.surname == null) {
            return this.name;
        }
        return this.name + " " + this.surname;
    }
}
