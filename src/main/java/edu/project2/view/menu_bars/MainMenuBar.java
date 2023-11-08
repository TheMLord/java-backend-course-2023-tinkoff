package edu.project2.view.menu_bars;

import edu.project2.controller.MazeGenerationListener;
import edu.project2.controller.menu_controllers.SolveMazeMenuController;
import edu.project2.view.frames.MainApplicationMazeFrame;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * Класс MenuBar.
 */
public class MainMenuBar extends JMenuBar {
    private static final String MENU_GENERATING_MAZE_NAME = "Выбор решения лабиринта";
    private static final String MENU_GENERATING_MAZE_DESCRIPTION =
        "Выбор алгоритма решения лабиринта";
    private static final String MENU_GENERATING_MAZE_ITEM_NAME = "решить лабиринт";
    private final MazeGenerationListener mazeGenerationListener;

    /**
     * Конструктор класса
     *
     * @param applicationMazeFrame ссылка на слушатель события от меню.
     */
    public MainMenuBar(MainApplicationMazeFrame applicationMazeFrame) {
        this.mazeGenerationListener = applicationMazeFrame;

        add(createMenuMazeGenerating());
    }

    /**
     * Метод подготовки Menu.
     *
     * @return возвращает настроенный Menu с настроенным обработчиком событий нажатия на меню.
     */
    private JMenu createMenuMazeGenerating() {
        JMenu mazeGeneratingMenu = new JMenu(MENU_GENERATING_MAZE_NAME);
        mazeGeneratingMenu.setMnemonic(KeyEvent.VK_G);
        mazeGeneratingMenu.getAccessibleContext().setAccessibleDescription(
            MENU_GENERATING_MAZE_DESCRIPTION);

        mazeGeneratingMenu.add(new SolveMazeMenuController(
            MENU_GENERATING_MAZE_ITEM_NAME,
            KeyEvent.VK_G,
            mazeGenerationListener
        ));

        return mazeGeneratingMenu;
    }
}
