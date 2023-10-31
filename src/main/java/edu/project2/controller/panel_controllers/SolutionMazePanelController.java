package edu.project2.controller.panel_controllers;

import javax.swing.JComboBox;
import java.awt.GridLayout;

public class SolutionMazePanelController extends AbstractPanelController {
    private final JComboBox solutionComboBox;

    public SolutionMazePanelController() {
        int row = 2;
        int col = 2;

        setLayout(new GridLayout(row, col));
        this.solutionComboBox = prepareComboBox(new String[] {
            "Поиск в ширину",
            "Поиск в глубину"
        });

        add(prepareLabel(CHOOSE_MAZE_SOLUTION_ALGORITHM_LABEL_NAME));
        add(solutionComboBox);
    }

    public String getSolutionComboBoxSelectedItem() {
        return (String) solutionComboBox.getSelectedItem();
    }
}
