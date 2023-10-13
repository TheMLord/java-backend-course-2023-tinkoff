package edu.project1;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.WordsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class with a unit test to check the correctness of the WordService.
 */
public class WordServiceTest {
    @Test
    @DisplayName("does the word return ?")
    void doesTheWordReturn() {
        Dictionary dictionary = new WordsService();

        String word = dictionary.getRandomWord();

        assertThat(word).isNotEqualTo(null);
        assertThat(word).isNotEqualTo("");
    }

}
