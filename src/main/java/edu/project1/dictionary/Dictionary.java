package edu.project1.dictionary;

import org.jetbrains.annotations.NotNull;

/**
 * Interface services with words for hangman game.
 */
public interface Dictionary {
    /**
     * Method that returns a random word from a collection.
     *
     * @return word for the hangman game.
     */
    @NotNull String getRandomWord();
}
