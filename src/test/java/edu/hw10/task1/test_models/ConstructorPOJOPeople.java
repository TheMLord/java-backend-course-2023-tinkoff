package edu.hw10.task1.test_models;

import edu.hw10.task1.value_annorations.Max;
import edu.hw10.task1.value_annorations.Min;
import edu.hw10.task1.value_annorations.NotNull;

public class ConstructorPOJOPeople {
    private final String name;
    private final String Surname;
    private final int age;

    public ConstructorPOJOPeople(@NotNull String name, @NotNull String surname, @Max(intValue = 10) int age) {
        this.name = name;
        Surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return Surname;
    }

    public int getAge() {
        return age;
    }
}
