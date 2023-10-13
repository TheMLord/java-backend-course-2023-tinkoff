package edu.project1;

import edu.project1.dictionary.Dictionary;
import edu.project1.dictionary.WordsService;
import edu.project1.game.ConsoleHangman;
import edu.project1.game.Session;

public class Main {
    private Main() {

    }

    public static void main(String[] args) {
        Dictionary dictionaryWords = new WordsService();
        var gameSession = new Session(dictionaryWords);

        var consoleHangman = new ConsoleHangman(gameSession);
        consoleHangman.run();
    }
}
