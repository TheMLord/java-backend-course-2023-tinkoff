package edu.hw7.Task3;

import java.util.List;

/**
 * Database Contract.
 */
public interface PersonDatabase {
    /**
     * The method of adding a person to the database.
     */
    void add(Person person);

    /**
     * Method of deleting a user from the database.
     *
     * @param id person ID.
     */
    void delete(int id);

    /**
     * Method that searches for people by name.
     *
     * @return a list of users that match the name, but provided that no attribute is null.
     */
    List<Person> findByName(String name);

    /**
     * Method that searches for people by address.
     *
     * @return a list of users that match the address, but provided that no attribute is null.
     */
    List<Person> findByAddress(String address);

    /**
     * Method that searches for people by phone number.
     *
     * @return a list of users that match the phone number, but provided that no attribute is null.
     */
    List<Person> findByPhone(String phone);
}
