package edu.project1.game;

import edu.project1.dictionary.Dictionary;
import edu.project1.game.model.GuessResult;
import java.util.Arrays;
import java.util.Formatter;
import org.jetbrains.annotations.NotNull;

/**
 * Game Session Class.
 */
public final class Session {
    private static final char UNSOLVED_CHAR = '*';
    static final String GIVE_UP_MESSAGE = "You lost!";
    private static final String REPEAT_MESSAGE = "Have you already called this letter";
    private static final String SUCCESSFUL_GUESS = "Hit!";
    private static final String FAILED_GUESS = "Missed, mistake %s out of %s.";
    private static final String WIN_GAME = "You won!";
    private final String answer;
    private final char[] userAnswer;
    private final int maxAttempts = 5;
    private int attempts;

    /**
     * Class constructor.
     *
     * @param dictionaryGame word service.
     */
    public Session(Dictionary dictionaryGame) {
        this.answer = dictionaryGame.getRandomWord();
        this.userAnswer = initializedUserAnswerChar(answer.length());
    }

    /**
     *
     */
    @NotNull GuessResult guess(char guess) {
        if (isGuessWas(guess)) {
            return new GuessResult.Repeat(
                new String(this.userAnswer),
                false,
                REPEAT_MESSAGE
            );
        }
        if (this.answer.indexOf(guess) != -1) {
            return checkCorrectAnswer(guess);
        }
        return checkWrongAnswer();

    }

    /**
     * Method of processing the exit from the game.
     *
     * @return model GuessResult.
     */
    @NotNull GuessResult giveUp() {
        return new GuessResult.Defeat(
            answer,
            true,
            GIVE_UP_MESSAGE
        );
    }

    /**
     * Method of processing the input of an incorrect character.
     *
     * @return model GuessResult.
     */
    private GuessResult checkWrongAnswer() {
        this.attempts += 1;
        if (attempts == this.maxAttempts) {
            return giveUp();
        }

        Formatter formatter = new Formatter();
        return new GuessResult.FailedGuess(
            new String(this.userAnswer),
            false,
            formatter.format(FAILED_GUESS, this.attempts, this.maxAttempts).toString()
        );
    }

    /**
     * Method of processing the input of a correct character.
     *
     * @param guess entered character.
     * @return model GuessResult.
     */
    private GuessResult checkCorrectAnswer(char guess) {
        markLetter(guess);
        if (isWin()) {
            return new GuessResult.Win(
                new String(this.userAnswer),
                true,
                WIN_GAME
            );
        }
        return new GuessResult.SuccessfulGuess(
            new String(this.userAnswer),
            false,
            SUCCESSFUL_GUESS
        );
    }

    /**
     * Method of checking whether such a letter has been guessed
     *
     * @param guess entered character.
     * @return true if the user called the letter and false in other case.
     */
    private boolean isGuessWas(char guess) {
        for (char c : this.userAnswer) {
            if (c == guess) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method of checking whether the game is won.
     *
     * @return true if the word is completely guessed and false in other case.
     */
    private boolean isWin() {
        String userAnswerString = new String(this.userAnswer);
        return userAnswerString.equals(this.answer);
    }

    /**
     * Method that marks the guessed character
     *
     * @param guess entered character.
     */
    private void markLetter(char guess) {
        var answerChar = this.answer.toCharArray();

        for (int i = 0; i < this.answer.length(); i++) {
            if (guess == answerChar[i]) {
                this.userAnswer[i] = guess;
            }
        }
    }

    /**
     * Method for initializing an array of guessed words.
     *
     * @param wordSize length of the given word.
     * @return filled char array.
     */
    private char[] initializedUserAnswerChar(int wordSize) {
        var answerChar = new char[wordSize];
        Arrays.fill(answerChar, UNSOLVED_CHAR);
        return answerChar;
    }

    public String getAnswer() {
        return answer;
    }
}
