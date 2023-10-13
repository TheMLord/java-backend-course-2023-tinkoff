package edu.project1;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.WordsService;
import edu.project1.game.ConsoleHangman;
import edu.project1.game.Session;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class of integration tests with word choice simulation.
 */
public class ConsoleHangmanTest {
    private final Dictionary dictionaryWords = mock(WordsService.class);

    @Test
    @DisplayName("completely successful game")
    void completelySuccessfulGame() {
        List<String> inputWordsList = new ArrayList<>() {{
            add("a");
            add("c");
            add("r");
        }};

        List<String> correctAnswerProgram = new ArrayList<>() {{
            add("Guess a letter:");
            add("Hit!");
            add("*a*");

            add("Guess a letter:");
            add("Hit!");
            add("ca*");

            add("Guess a letter:");
            add("You won!");
            add("car");
        }};
        when(dictionaryWords.getRandomWord()).thenReturn("car");

        var gameSession = new Session(dictionaryWords);
        var consoleHangman = new ConsoleHangman(gameSession);

        List<String> gameAnswer = getResultProgram(inputWordsList, consoleHangman);

        assertThat(gameAnswer).isEqualTo(correctAnswerProgram);
    }

    @Test
    @DisplayName("word service failure")
    void wordServiceFailure() {
        List<String> inputWordsList = new ArrayList<>() {{
            add("a");
            add("c");
            add("r");
        }};

        List<String> correctAnswerProgram = new ArrayList<>() {{
            add("Failed start game. Dictionary service is broken");
        }};
        when(dictionaryWords.getRandomWord()).thenReturn("");

        var gameSession = new Session(dictionaryWords);
        var consoleHangman = new ConsoleHangman(gameSession);

        List<String> gameAnswer = getResultProgram(inputWordsList, consoleHangman);

        assertThat(gameAnswer).isEqualTo(correctAnswerProgram);
    }

    @Test
    @DisplayName("exit the game immediately")
    void exitTheGameImmediately() {
        List<String> inputWordsList = new ArrayList<>() {{
            add("exit");
        }};

        List<String> correctAnswerProgram = new ArrayList<>() {{
            add("Guess a letter:");
            add("You lost!");
            add("right word: book");
        }};
        when(dictionaryWords.getRandomWord()).thenReturn("book");

        var gameSession = new Session(dictionaryWords);
        var consoleHangman = new ConsoleHangman(gameSession);

        List<String> gameAnswer = getResultProgram(inputWordsList, consoleHangman);

        assertThat(gameAnswer).isEqualTo(correctAnswerProgram);
    }

    @Test
    @DisplayName("give up game")
    void giveUpGame() {
        List<String> inputWordsList = new ArrayList<>() {{
            add("y");
            add("j");
            add("a");
            add("exit");
        }};

        List<String> correctAnswerProgram = new ArrayList<>() {{
            add("Guess a letter:");
            add("Missed, mistake 1 out of 5.");
            add("***");
            add("Guess a letter:");
            add("Hit!");
            add("j**");
            add("Guess a letter:");
            add("Missed, mistake 2 out of 5.");
            add("j**");
            add("Guess a letter:");
            add("You lost!");
            add("right word: job");
        }};
        when(dictionaryWords.getRandomWord()).thenReturn("job");

        var gameSession = new Session(dictionaryWords);
        var consoleHangman = new ConsoleHangman(gameSession);

        List<String> gameAnswer = getResultProgram(inputWordsList, consoleHangman);

        assertThat(gameAnswer).isEqualTo(correctAnswerProgram);
    }

    @Test
    @DisplayName("Wrong input")
    void wrongInput() {
        List<String> inputWordsList = new ArrayList<>() {{
            add("c");
            add("gfdgfdgfdgdfgf");
            add("t");
            add("a");
        }};

        List<String> correctAnswerProgram = new ArrayList<>() {{
            add("Guess a letter:");
            add("Hit!");
            add("c**");
            add("Guess a letter:");
            add("incorrect input");
            add("Guess a letter:");
            add("Hit!");
            add("c*t");
            add("Guess a letter:");
            add("You won!");
            add("cat");
        }};
        when(dictionaryWords.getRandomWord()).thenReturn("cat");

        var gameSession = new Session(dictionaryWords);
        var consoleHangman = new ConsoleHangman(gameSession);

        List<String> gameAnswer = getResultProgram(inputWordsList, consoleHangman);

        assertThat(gameAnswer).isEqualTo(correctAnswerProgram);
    }

    @Test
    @DisplayName("Failed Game")
    void failedGame() {
        List<String> inputWordsList = new ArrayList<>() {{
            add("r");
            add("s");
            add("y");
            add("q");
            add("l");
        }};

        List<String> correctAnswerProgram = new ArrayList<>() {{
            add("Guess a letter:");
            add("Missed, mistake 1 out of 5.");
            add("***");
            add("Guess a letter:");
            add("Missed, mistake 2 out of 5.");
            add("***");
            add("Guess a letter:");
            add("Missed, mistake 3 out of 5.");
            add("***");
            add("Guess a letter:");
            add("Missed, mistake 4 out of 5.");
            add("***");
            add("Guess a letter:");
            add("You lost!");
            add("dog");
        }};
        when(dictionaryWords.getRandomWord()).thenReturn("dog");

        var gameSession = new Session(dictionaryWords);
        var consoleHangman = new ConsoleHangman(gameSession);

        List<String> gameAnswer = getResultProgram(inputWordsList, consoleHangman);

        assertThat(gameAnswer).isEqualTo(correctAnswerProgram);
    }

    /**
     * The method gets the output result of the program during the game.
     * Input words are transmitted to the standard input stream as bytes and intercepted from the standard output stream.
     *
     * @param inputWordsList list of words entered into the game.
     * @param consoleHangman an object of the main class with game logic that runs the run() method.
     * @return list of words that the program will output
     */
    @NotNull private static List<String> getResultProgram(List<String> inputWordsList, ConsoleHangman consoleHangman) {
        String inputWordsString = String.join(System.lineSeparator(), inputWordsList);
        ByteArrayInputStream byteInput = new ByteArrayInputStream(inputWordsString.getBytes());
        System.setIn(byteInput);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        consoleHangman.run();

        List<String> gameOutput = new ArrayList<>(Arrays.asList(outputStream.toString().split(System.lineSeparator())));

        try {
            byteInput.close();
            printStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.setIn(System.in);
        System.setOut(System.out);

        return gameOutput;
    }

}
