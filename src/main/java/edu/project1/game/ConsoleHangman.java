package edu.project1.game;

import edu.project1.game.model.GuessResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Game process control class.
 */
public final class ConsoleHangman {
    private static final String GUESS_A_LETTER = "Guess a letter:";
    private static final String RIGHT_WORD = "right word: ";
    private static final String EXIT_MESSAGE = "exit";
    private static final String WRONG_INPUT = "incorrect input";
    private static final String DICTIONARY_SERVICE_ERROR = "Failed start game. Dictionary service is broken";
    private static final Logger LOGGER = Logger.getLogger(ConsoleHangman.class.getName());
    private final List<String> gameAnswer = new ArrayList<>();

    private final Session gameSession;

    /**
     * Class constructor.
     *
     * @param gameSession object Session.
     */
    public ConsoleHangman(Session gameSession) {
        this.gameSession = gameSession;
    }

    /**
     * Method that starts the gameplay
     */
    public void run() {
        if (gameSession.getAnswer().isEmpty()) {
            gameAnswer.add(DICTIONARY_SERVICE_ERROR);
            LOGGER.info(DICTIONARY_SERVICE_ERROR);
        } else {
            Scanner inputScanner = new Scanner(System.in);
            while (true) {
                gameAnswer.add(GUESS_A_LETTER);
                LOGGER.info(GUESS_A_LETTER);
                String input = inputScanner.nextLine().toLowerCase();

                if (input.equals(EXIT_MESSAGE)) {
                    GuessResult guessResult = this.gameSession.giveUp();

                    gameAnswer.add(guessResult.message());
                    gameAnswer.add(RIGHT_WORD + guessResult.currentWord());
                    LOGGER.info(guessResult.message());
                    LOGGER.info(RIGHT_WORD + guessResult.currentWord());
                    break;
                }

                if (input.length() != 1) {
                    gameAnswer.add(WRONG_INPUT);
                    LOGGER.info(WRONG_INPUT);
                    continue;
                }

                GuessResult guessResult = tryGuess(this.gameSession, input.charAt(0));
                printState(guessResult);

                if (guessResult.isEnd()) {
                    break;
                }
            }
        }
    }

    /**
     * Method that delegates processing of an incoming character to the Session class.
     *
     * @param session Session object.
     * @param guess   character entered by the user.
     * @return model GuessResult.
     */
    private GuessResult tryGuess(Session session, char guess) {
        return session.guess(guess);
    }

    /**
     * Method that prints the game message.
     *
     * @param guess model GuessResult.
     */
    private void printState(GuessResult guess) {
        gameAnswer.add(guess.message());
        gameAnswer.add(guess.currentWord());
        LOGGER.info(guess.message());
        LOGGER.info(guess.currentWord());
    }

    /**
     * Method that returns a list with the current system responses of the game.
     *
     * @return arrayList with current system responses of the game.
     */
    public List<String> getGameAnswer() {
        return this.gameAnswer;
    }
}
