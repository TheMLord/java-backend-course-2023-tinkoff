package edu.project1.dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

/**
 * Server implementation with words for the game.
 */
public class WordsService implements Dictionary {
    private final List<String> hangmanWord = new ArrayList<>() {{
        add("orange");
        add("audi");
        add("book");
        add("cucumber");
    }};

    @Override
    public @NotNull String getRandomWord() {
        Random random = new Random();
        int randomIndex = random.nextInt(hangmanWord.size());

        return hangmanWord.get(randomIndex);
    }
}
