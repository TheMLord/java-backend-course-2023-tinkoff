package edu.hw10.task1.models;

import edu.hw10.task1.value_annorations.Max;
import edu.hw10.task1.value_annorations.Min;
import edu.hw10.task1.value_annorations.NotNull;

public record People(@NotNull String name, @Min(intValue = 4) @Max(intValue = 10) int age) {
}
