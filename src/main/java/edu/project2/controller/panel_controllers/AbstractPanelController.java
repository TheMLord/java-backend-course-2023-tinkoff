package edu.project2.controller.panel_controllers;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class AbstractPanelController extends JPanel {
    protected static final String CHOOSE_MAZE_SOLUTION_ALGORITHM_LABEL_NAME = "выбрать алгоритм решения лабиринта";
    protected static final String INPUT_NUMBER_LABEL_HEIGHT_NAME = "введите высоту";
    protected static final String INPUT_NUMBER_LABEL_WEIGHT_NAME = "введите ширину";

    protected JLabel prepareLabel(String name) {
        return new JLabel(name);
    }

    protected JComboBox<String> prepareComboBox(String[] comboBoxItems) {
        return new JComboBox<>(comboBoxItems);
    }

    protected JTextField prepareTextField() {
        return new JTextField();
    }
}
