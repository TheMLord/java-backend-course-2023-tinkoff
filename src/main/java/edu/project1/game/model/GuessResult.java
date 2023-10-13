package edu.project1.game.model;

import org.jetbrains.annotations.NotNull;

/**
 * Interface of models for the hangman game.
 */
public sealed interface GuessResult {
    /**
     * Method that returns the current guessed word.
     *
     * @return current guessed word.
     */
    @NotNull String currentWord();

    /**
     * Method that return flag of the end of the game
     *
     * @return true if game is end and false in other case.
     */
    boolean isEnd();

    /**
     * Method that returns a message to the game model
     *
     * @return game message.
     */
    @NotNull String message();

    /**
     * Defeat model.
     */
    record Defeat(String currentWord,
                  boolean isEnd,
                  String message) implements GuessResult {
    }

    /**
     * Repeat model.
     */
    record Repeat(String currentWord,
                  boolean isEnd,
                  String message) implements GuessResult {
    }

    /**
     * Win model.
     */
    record Win(String currentWord,
               boolean isEnd,
               String message) implements GuessResult {
    }

    /**
     * SuccessfulGuess model.
     */
    record SuccessfulGuess(String currentWord,
                           boolean isEnd,
                           String message) implements GuessResult {
    }

    /**
     * FailedGuess model.
     */
    record FailedGuess(String currentWord,
                       boolean isEnd,
                       String message) implements GuessResult {
    }
}
