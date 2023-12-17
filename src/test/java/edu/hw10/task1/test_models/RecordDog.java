package edu.hw10.task1.test_models;

import edu.hw10.task1.value_annorations.Max;
import edu.hw10.task1.value_annorations.Min;
import edu.hw10.task1.value_annorations.NotNull;

public record RecordDog(@NotNull String name, @Min(intValue = 3) @Max(intValue = 10) int age) {
}
