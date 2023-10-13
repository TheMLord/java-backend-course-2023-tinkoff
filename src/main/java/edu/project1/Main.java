package edu.project1;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.WordsService;
import edu.project1.game.ConsoleHangman;
import edu.project1.game.Session;

/**
 * Main class.
 */
public class Main {
    /**
     * Class constructor.
     */
    private Main() {

    }

    /**
     * Method is the entry point of the program.
     * A word service and game session object is created and passed to ConsoleHangman,
     * after which the run() method is run which starts the game.
     */
    public static void main(String[] args) {
        Dictionary dictionaryWords = new WordsService();
        var gameSession = new Session(dictionaryWords);

        var consoleHangman = new ConsoleHangman(gameSession);
        consoleHangman.run();
    }
}
