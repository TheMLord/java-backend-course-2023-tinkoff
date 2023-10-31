package edu.project2.controller.menu_controllers;

import edu.project2.controller.MazeGenerationListener;
import javax.swing.JMenuItem;

public class SolveMazeMenuController extends JMenuItem {
    private final MazeGenerationListener generationListener;

    public SolveMazeMenuController(
        String nameController,
        int keyEventCode,
        MazeGenerationListener generationListener
    ) {
        super(nameController, keyEventCode);
        this.generationListener = generationListener;

        addActionListener((event) -> {
            this.generationListener.respondMazeGenerationRequest();
        });
    }
}
