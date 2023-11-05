package edu.project2;

import edu.project2.view.frames.MainApplicationMazeFrame;
import java.awt.Frame;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class Main {
    private static final String LOOK_AND_FEEL_STYLE_ONE = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private Main() {
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(LOOK_AND_FEEL_STYLE_ONE);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        SwingUtilities.invokeLater(() -> {
            var frame = new MainApplicationMazeFrame();
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
