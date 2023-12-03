package edu.hw7.Task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of the user database via Lock. Task 3.5.
 */
public class LockPeopleSearchService implements PersonDatabase {
    private static final ReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock(true);
    private final Map<Integer, Person> database = new HashMap<>();

    @Override
    public void add(Person person) {
        READ_WRITE_LOCK.writeLock().lock();
        try {
            database.put(person.id(), person);
        } finally {
            READ_WRITE_LOCK.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        READ_WRITE_LOCK.writeLock().lock();
        try {
            database.remove(id);
        } finally {
            READ_WRITE_LOCK.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        READ_WRITE_LOCK.readLock().lock();
        try {
            return database.values().stream()
                .filter(person -> person.name().equals(name) && isAttributeNotNull(person))
                .toList();
        } finally {
            READ_WRITE_LOCK.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        READ_WRITE_LOCK.readLock().lock();
        try {
            return database.values().stream()
                .filter(person -> person.address().equals(address)
                    && isAttributeNotNull(person)).toList();
        } finally {
            READ_WRITE_LOCK.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        READ_WRITE_LOCK.readLock().lock();
        try {
            return database.values().stream()
                .filter(person -> person.phoneNumber().equals(phone) && isAttributeNotNull(person))
                .toList();
        } finally {
            READ_WRITE_LOCK.readLock().unlock();
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

    public int getDataBaseSize() {
        return this.database.size();
    }
}
