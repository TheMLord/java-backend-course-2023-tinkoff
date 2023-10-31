package edu.project2.view.menu_bars;

import edu.project2.controller.menu_controllers.SolveMazeMenuController;
import edu.project2.view.frames.MainApplicationMazeFrame;
import edu.project2.controller.MazeGenerationListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.event.KeyEvent;

public class MainMenuBar extends JMenuBar {
    private static final String menuGeneratingMazeName = "Выбор решения лабиринта";
    private static final String menuGeneratingMazeDescription =
        "Выбор алгоритма решения лабиринта";
    private static final String menuGeneratingMazeItemName = "решить лабиринт";
    private final MazeGenerationListener mazeGenerationListener;

    public MainMenuBar(MainApplicationMazeFrame applicationMazeFrame) {
        this.mazeGenerationListener = applicationMazeFrame;

        add(createMenuMazeGenerating());
    }

    private JMenu createMenuMazeGenerating() {
        JMenu mazeGeneratingMenu = new JMenu(menuGeneratingMazeName);
        mazeGeneratingMenu.setMnemonic(KeyEvent.VK_G);
        mazeGeneratingMenu.getAccessibleContext().setAccessibleDescription(
            menuGeneratingMazeDescription);
        {
            mazeGeneratingMenu.add(new SolveMazeMenuController(
                menuGeneratingMazeItemName,
                KeyEvent.VK_G,
                mazeGenerationListener
            ));
        }
        return mazeGeneratingMenu;
    }
}
