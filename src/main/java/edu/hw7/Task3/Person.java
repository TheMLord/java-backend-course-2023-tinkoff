package edu.hw7.Task3;

/**
 * Person model.
 *
 * @param id          mandatory unique person identifier.
 * @param name        name person. Maybe null.
 * @param address     person address. Maybe null.
 * @param phoneNumber pearson phone number. Maybe null.
 */
public record Person(int id, String name, String address, String phoneNumber) {
}

