package edu.project1.game.model;

import org.jetbrains.annotations.NotNull;

public sealed interface GuessResult {
    @NotNull String currentWord();

    boolean isEnd();

    @NotNull String message();

    record Defeat(String currentWord,
                  boolean isEnd,
                  String message) implements GuessResult {
    }

    record Repeat(String currentWord,
                  boolean isEnd,
                  String message) implements GuessResult {
    }

    record Win(String currentWord,
               boolean isEnd,
               String message) implements GuessResult {
    }

    record SuccessfulGuess(String currentWord,
                           boolean isEnd,
                           String message) implements GuessResult {
    }

    record FailedGuess(String currentWord,
                       boolean isEnd,
                       String message) implements GuessResult {
    }
}
