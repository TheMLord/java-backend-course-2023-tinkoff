package edu.project2.controller.panel_controllers;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.GridLayout;

public final class CreatorMazePanelController extends AbstractPanelController {
    private final JComboBox generatingComboBox;
    private final JTextField heightField;
    private final JTextField weightField;

    public CreatorMazePanelController() {
        int row = 3;
        int col = 3;

        setLayout(new GridLayout(row, col));

        this.generatingComboBox = prepareComboBox(
            new String[] {
                "Алгоритм Прима",
                "Алгоритм Эллера"
            }
        );

        this.heightField = prepareTextField();
        this.weightField = prepareTextField();

        add(prepareLabel(CHOOSE_MAZE_SOLUTION_ALGORITHM_LABEL_NAME));
        add(generatingComboBox);

        add(prepareLabel(INPUT_NUMBER_LABEL_HEIGHT_NAME));
        add(heightField);

        add(prepareLabel(INPUT_NUMBER_LABEL_WEIGHT_NAME));
        add(weightField);
    }

    public String getGeneratingComboBoxSelectedItem() {
        return (String) generatingComboBox.getSelectedItem();
    }

    public Integer getHeightFieldInputNumber() {
        return Integer.parseInt(heightField.getText());
    }

    public Integer getWeightFieldInputNumber() {
        return Integer.parseInt(weightField.getText());
    }
}
