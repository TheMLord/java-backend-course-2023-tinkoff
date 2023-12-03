package edu.hw7.Task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the user database via Synchronized. Task 3
 */
public class SynchronizedPeopleSearchService implements PersonDatabase {
    private final Map<Integer, Person> database = new HashMap<>();

    @Override
    public void add(Person person) {
        synchronized (this.database) {
            database.put(person.id(), person);
        }
    }

    @Override
    public synchronized void delete(int id) {
        synchronized (this.database) {
            database.remove(id);
        }
    }

    @Override
    public List<Person> findByName(String name) {
        synchronized (this.database) {
            return database.values().stream()
                .filter(person -> person.name().equals(name) && isAttributeNotNull(person))
                .toList();
        }
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        synchronized (this.database) {
            return database.values().stream()
                .filter(person -> person.address().equals(address) && isAttributeNotNull(person))
                .toList();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        synchronized (this.database) {
            return database.values().stream()
                .filter(person -> person.phoneNumber().equals(phone) && isAttributeNotNull(person))
                .toList();
        }
    }

    /**
     * A method that verifies that no attribute of a person is null.
     *
     * @return true if no attribute of person is null and false in other case.
     */
    private boolean isAttributeNotNull(Person person) {
        return person.address() != null && person.name() != null && person.phoneNumber() != null;
    }

    /**
     * Method that returns the size of the database.
     */
    public int getDataBaseSize() {
        return this.database.size();
    }

}

