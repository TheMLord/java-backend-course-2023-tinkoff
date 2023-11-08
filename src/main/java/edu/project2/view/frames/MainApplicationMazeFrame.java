package edu.project2.view.frames;

import edu.project2.controller.MazeGenerationListener;
import edu.project2.controller.generators.EllerMazeGenerator;
import edu.project2.controller.generators.Generator;
import edu.project2.controller.panel_controllers.CreatorMazePanelController;
import edu.project2.controller.panel_controllers.SolutionMazePanelController;
import edu.project2.model.Coordinate;
import edu.project2.model.Maze;
import edu.project2.model.solvers.Solver;
import edu.project2.model.solvers.SolverBFS;
import edu.project2.model.solvers.SolverDFS;
import edu.project2.view.menu_bars.MainMenuBar;
import edu.project2.view.panels.MazeOriginalPanel;
import edu.project2.view.panels.MazeSolutionPanel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Класс главного окна UI.
 */
public final class MainApplicationMazeFrame extends JFrame implements MazeGenerationListener {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private static final int INSET = 50;
    private static final String DIALOG_GET_SOLUTION_MESSAGE = "Заполните параметры решения лабиринта";
    private static final String DIALOG_GET_GENERATE_MESSAGE = "Заполните параметры генерации лабиринта";
    private static final String DIALOG_MESSAGE_INVALID_SIZE = "Введите число в диапазоне от 4 до 20";
    private static final String DIALOG_MESSAGE_INVALID_TEXT = "Необходимо ввести корректные данные";
    private static final String DIALOG_MESSAGE_DEAD_END = "Путь не существует";
    private static final String ORIGINAL_INTERNAL_PANEL_TITLE = "лабиринт";
    private static final String SOLUTION_INTERNAL_PANEL_TITLE = "решение лабиринта";
    private static final int CODE_EXIT_ERROR = 90;
    private static final int LOWER_LIMIT_SIZE_MAZE = 4;
    private static final int UPPER_LIMIT_SIZE_MAZE = 20;
    private static final int BOUNDARY_COEFFICIENT = 2;
    private static final Point SOLUTION_POSITION =
        new Point(
            Toolkit.getDefaultToolkit().getScreenSize().height / 2,
            0
        );
    private final Maze generatedMaze;
    private final List<Coordinate> emptyMazeCells;

    /**
     * Конструктор класса.
     */
    public MainApplicationMazeFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(INSET, INSET,
            screenSize.width - INSET * BOUNDARY_COEFFICIENT,
            screenSize.height - INSET * BOUNDARY_COEFFICIENT
        );
        setContentPane(desktopPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.generatedMaze = getMaze();
        if (generatedMaze == null) {
            WindowEvent closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
            System.exit(CODE_EXIT_ERROR);
        }

        this.emptyMazeCells = this.generatedMaze.getEmptyCells();

        var mazeWindow = createOriginalMazeWindow();
        addWindow(mazeWindow);

        setJMenuBar(new MainMenuBar(this));
    }

    /**
     * Метод получения модели лабиринта.
     * Вызывается в при открытии приложения, позволяет настроить будущий лабиринт - выбрать его размеры,
     * указать алгоритм генерации.
     *
     * @return при успешном создании лабиринта, возвращает объект этого типа или null.
     */
    private Maze getMaze() {
        while (true) {
            var creatorMazePanel = new CreatorMazePanelController();

            int choiceOptionPane = JOptionPane.showConfirmDialog(
                null,
                creatorMazePanel,
                DIALOG_GET_GENERATE_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION
            );

            if (choiceOptionPane != JOptionPane.OK_OPTION) {
                break;
            }

            try {
                int inputSize = creatorMazePanel.getSizeMaze();
                if (inputSize >= LOWER_LIMIT_SIZE_MAZE && inputSize <= UPPER_LIMIT_SIZE_MAZE) {
                    Generator generatorAlgorithm =
                        getGeneratedAlgorithm(creatorMazePanel.getGeneratingComboBoxSelectedItem());
                    generatorAlgorithm.generate(inputSize);

                    return generatorAlgorithm.generate(inputSize);
                } else {
                    JOptionPane.showMessageDialog(null, DIALOG_MESSAGE_INVALID_SIZE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, DIALOG_MESSAGE_INVALID_TEXT);
            }

        }
        return null;
    }

    /**
     * Метод создания фрейма лабиринта.
     * Создает фрейм с панелью с отрисованным лабиринтом.
     *
     * @return возвращает подготовленный фрейм.
     */
    private JInternalFrame createOriginalMazeWindow() {
        JInternalFrame mazeInternalFrame = new JInternalFrame(
            ORIGINAL_INTERNAL_PANEL_TITLE,
            false,
            false,
            false,
            false
        );

        JPanel mazePanel = new MazeOriginalPanel(this.generatedMaze, this.generatedMaze.getSize());

        mazeInternalFrame.getContentPane().add(mazePanel);
        mazeInternalFrame.pack();

        return mazeInternalFrame;
    }

    /**
     * Фабричный метод, который возвращает реализацию генератора лабиринта.
     *
     * @param algorithmTypeString указанный пользователем алгоритм генерации.
     * @return конкретную реализацию алгоритма генерации.
     */
    private Generator getGeneratedAlgorithm(String algorithmTypeString) {
        if (algorithmTypeString.equals(
            CreatorMazePanelController.TypeGenerateAlgorithm.ELLER_ALGORITHM.getAlgorithm())
        ) {
            return new EllerMazeGenerator();
        }
        return null;
    }

    /**
     * Метод вызывается если пользователь захочет посмотреть решение лабиринта встроенными алгоритмами.
     */
    @Override
    public void respondMazeGenerationRequest() {
        var solutionMazePanel = new SolutionMazePanelController(this.emptyMazeCells);

        int result = JOptionPane.showConfirmDialog(
            null,
            solutionMazePanel,
            DIALOG_GET_SOLUTION_MESSAGE,
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            Solver algorithmSolution = getMazeSolver(solutionMazePanel.getSolutionComboBoxSelectedItem());
            var start = solutionMazePanel.getCoordinateChoice(solutionMazePanel.getStartCellsComboBox());
            var end = solutionMazePanel.getCoordinateChoice(solutionMazePanel.getEndCellsComboBox());

            var solutionMaze = algorithmSolution.solve(this.generatedMaze, start, end);

            if (solutionMaze.isEmpty()) {
                JOptionPane.showMessageDialog(null, DIALOG_MESSAGE_DEAD_END);

            }

            JInternalFrame solutionMazeInternalFrame = new JInternalFrame(
                SOLUTION_INTERNAL_PANEL_TITLE,
                true,
                true,
                true,
                true
            );

            JPanel mazeSolutionPanel = new MazeSolutionPanel(this.generatedMaze, solutionMaze, start, end,
                this.generatedMaze.getSize()
            );

            solutionMazeInternalFrame.getContentPane().add(mazeSolutionPanel);
            solutionMazeInternalFrame.pack();

            solutionMazeInternalFrame.setLocation(SOLUTION_POSITION);
            this.addWindow(solutionMazeInternalFrame);
        }
    }

    /**
     * Фабричный метод, который возвращает алгоритм решения по выбранному пользователем алгоритму.
     *
     * @param algorithm выбранный пользователем алгоритм.
     * @return реализацию решения лабиринта.
     */
    private Solver getMazeSolver(String algorithm) {
        if (algorithm.equals(SolutionMazePanelController.TypeSolutionAlgorithm.BFS.getNameAlgorithm())) {
            return new SolverBFS();
        } else if (algorithm.equals(SolutionMazePanelController.TypeSolutionAlgorithm.DFS.getNameAlgorithm())) {
            return new SolverDFS();
        }
        return null;
    }

    /**
     * Метод, который добавляет очередной фрейм на основное окно.
     *
     * @param frame какой-либо фрейм.
     */
    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}

