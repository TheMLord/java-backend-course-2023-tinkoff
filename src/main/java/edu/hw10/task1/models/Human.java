package edu.hw10.task1.models;

import edu.hw10.task1.value_annorations.Max;
import edu.hw10.task1.value_annorations.Min;
import org.jetbrains.annotations.NotNull;

public class Human {
    private final Integer age;
    private String name;

    public Human(@Max(50) @Min(20) Integer age, String name) {
        this.age = age;
        this.name = name;
    }

}
