package edu.hw10.task1.models;

import edu.hw10.task1.value_annorations.Min;
import org.jetbrains.annotations.NotNull;

public record People(@NotNull String name, @Min(4) int age) {
}
