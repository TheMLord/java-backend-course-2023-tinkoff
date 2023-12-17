package edu.hw10.task1.test_models;

import edu.hw10.task1.value_annorations.Min;
import edu.hw10.task1.value_annorations.NotNull;

public record InterestingNumber(@Min(doubleValue = 14.3) double number, @NotNull String description) {
}
