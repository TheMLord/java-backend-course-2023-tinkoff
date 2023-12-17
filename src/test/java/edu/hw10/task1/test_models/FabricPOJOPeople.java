package edu.hw10.task1.test_models;

import edu.hw10.task1.value_annorations.NotNull;

public class FabricPOJOPeople {
    private final String name;
    private final String Surname;
    private final Integer age;

    private FabricPOJOPeople(String name, String surname, Integer age) {
        this.name = name;
        Surname = surname;
        this.age = age;
    }

    public static FabricPOJOPeople create(@NotNull String name, String surname, Integer age) {
        return new FabricPOJOPeople(name, surname, age);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return Surname;
    }

    public Integer getAge() {
        return age;
    }
}
