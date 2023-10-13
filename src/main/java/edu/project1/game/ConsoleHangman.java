package edu.project1.game;

import edu.project1.game.model.GuessResult;
import java.util.Scanner;

public final class ConsoleHangman {
    private static final String GUESS_A_LETTER = "Guess a letter:";
    private static final String RIGHT_WORD = "right word: ";
    private static final String EXIT_MESSAGE = "exit";
    private static final String WRONG_INPUT = "incorrect input";
    private static final String DICTIONARY_SERVICE_ERROR = "Failed start game. Dictionary service is broken";

    private final Session gameSession;

    public ConsoleHangman(Session gameSession) {
        this.gameSession = gameSession;
    }

    public void run() {
        if (gameSession.getAnswer().isEmpty()) {
            System.out.println(DICTIONARY_SERVICE_ERROR);
        } else {
            Scanner inputScanner = new Scanner(System.in);
            while (true) {
                System.out.println(GUESS_A_LETTER);
                String input = inputScanner.nextLine().toLowerCase();

                if (input.equals(EXIT_MESSAGE)) {
                    GuessResult guessResult = this.gameSession.giveUp();
                    System.out.println(guessResult.message());
                    System.out.println(RIGHT_WORD + guessResult.currentWord());
                    break;
                }

                if (input.length() != 1) {
                    System.out.println(WRONG_INPUT);
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

    private GuessResult tryGuess(Session session, char guess) {
        return session.guess(guess);
    }

    private void printState(GuessResult guess) {
        System.out.println(guess.message());
        System.out.println(guess.currentWord());
    }
}
