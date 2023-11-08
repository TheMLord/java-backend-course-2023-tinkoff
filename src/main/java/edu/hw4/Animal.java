package edu.hw4;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {
    private static final int DOG_CAT_COUNT_PAWS = 4;
    private static final int BIRD_COUNT_PAWS = 2;
    private static final int FISH_COUNT_PAWS = 0;
    private static final int SPIDER_COUNT_PAWS = 8;

    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    public int paws() {
        return switch (type) {
            case CAT, DOG -> DOG_CAT_COUNT_PAWS;
            case BIRD -> BIRD_COUNT_PAWS;
            case FISH -> FISH_COUNT_PAWS;
            case SPIDER -> SPIDER_COUNT_PAWS;
        };
    }
}

