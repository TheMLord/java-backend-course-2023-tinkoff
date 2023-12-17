package edu.hw10.task1.models;

import edu.hw10.task1.value_annorations.Max;
import edu.hw10.task1.value_annorations.Min;
import edu.hw10.task1.value_annorations.NotNull;

public class Human {
    private final Integer age;
    private String name;

    private Human(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public static Human createHuman(@NotNull @Max(intValue = 50) @Min(intValue = 20) Integer age, String name) {
        return new Human(age, name);
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
