package edu.project2.view.frames;

import edu.project2.controller.MazeGenerationListener;
import edu.project2.controller.panel_controllers.CreatorMazePanelController;
import edu.project2.controller.panel_controllers.SolutionMazePanelController;
import edu.project2.view.menu_bars.MainMenuBar;
import edu.project2.view.panels.MazeOriginalPanel;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

public final class MainApplicationMazeFrame extends JFrame implements MazeGenerationListener {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private static final int INSET = 50;
    private static final String DIALOG_GET_SOLUTION_MESSAGE = "Заполните параметры решения лабиринта";
    private static final String DIALOG_GET_GENERATE_MESSAGE = "Заполните параметры генерации лабиринта";

    private static final String ORIGINAL_INTERNAL_PANEL_TITLE = "лабиринт";

    public MainApplicationMazeFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(INSET, INSET,
            screenSize.width - INSET * 2,
            screenSize.height - INSET * 2
        );
        setContentPane(desktopPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            var mazeWindow = createOriginalMazeWindow();
            addWindow(mazeWindow);
        } catch (NullPointerException e) {
            WindowEvent closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
        }

        setJMenuBar(new MainMenuBar(this));

    }

    private JInternalFrame createOriginalMazeWindow() {
        var creatorMazePanel = new CreatorMazePanelController();

        int result = JOptionPane.showConfirmDialog(
            null,
            creatorMazePanel,
            DIALOG_GET_GENERATE_MESSAGE,
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            char[][] maze = {
                {'█', 'S', '█', '█', '█', '█', '█', '█', '█', '█'},
                {'█', ' ', '█', ' ', ' ', ' ', ' ', ' ', ' ', '█'},
                {'█', ' ', '█', '█', '█', ' ', '█', '█', ' ', '█'},
                {'█', ' ', ' ', ' ', '█', ' ', '█', ' ', ' ', '█'},
                {'█', '█', '█', ' ', '█', ' ', '█', '█', '█', '█'},
                {'█', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'E', '█'},
                {'█', '█', '█', '█', '█', '█', '█', '█', '█', '█'}
            };

            var mazeInternalFrame = new JInternalFrame(
                ORIGINAL_INTERNAL_PANEL_TITLE,
                false,
                false,
                true,
                true
            );
            var mazePanel = new MazeOriginalPanel(maze);

            mazeInternalFrame.getContentPane().add(mazePanel);
            mazeInternalFrame.setLocation(10, 10);
            mazeInternalFrame.setPreferredSize(new Dimension(20, 20));
            setMinimumSize(mazeInternalFrame.getPreferredSize());
            mazeInternalFrame.pack();

            return mazeInternalFrame;

        }
        return null;
    }

    @Override
    public void respondMazeGenerationRequest() {
        var solutionMazePanel = new SolutionMazePanelController();

        int result = JOptionPane.showConfirmDialog(
            null,
            solutionMazePanel,
            DIALOG_GET_SOLUTION_MESSAGE,
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            System.out.println(solutionMazePanel.getSolutionComboBoxSelectedItem());
        }
    }

    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}

