package edu.project2.controller.panel_controllers;

import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Контроллер панели создания лабиринта.
 */
public final class CreatorMazePanelController extends AbstractPanelController {
    private static final int ROW_PANEL = 3;
    private static final int COL_PANEL = 3;
    private final JComboBox generatingComboBox;
    private final JTextField sizeMazeField;

    public CreatorMazePanelController() {
        setLayout(new GridLayout(ROW_PANEL, COL_PANEL));

        this.generatingComboBox = prepareComboBox(
            new String[] {
                TypeGenerateAlgorithm.ELLER_ALGORITHM.getAlgorithm(),
            }
        );

        this.sizeMazeField = prepareTextField();

        add(prepareLabel(CHOOSE_MAZE_SOLUTION_ALGORITHM_LABEL_NAME));
        add(generatingComboBox);

        add(prepareLabel(INPUT_SIZE_LABEL_HEIGHT_NAME));
        add(sizeMazeField);

    }

    public String getGeneratingComboBoxSelectedItem() {
        return (String) generatingComboBox.getSelectedItem();
    }

    public Integer getSizeMaze() {
        return Integer.parseInt(sizeMazeField.getText());
    }

    public enum TypeGenerateAlgorithm {
        ELLER_ALGORITHM("Алгоритм Эллера");

        private final String algorithm;

        TypeGenerateAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getAlgorithm() {
            return algorithm;
        }
    }
}
