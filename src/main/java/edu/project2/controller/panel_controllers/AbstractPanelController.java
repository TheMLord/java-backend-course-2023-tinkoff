package edu.project2.controller.panel_controllers;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Абстрактный класс с контрактом для панелей контроллеров создания/изменения состояния модели.
 */
public abstract class AbstractPanelController extends JPanel {
    protected static final String CHOOSE_MAZE_SOLUTION_ALGORITHM_LABEL_NAME = "выбрать алгоритм решения лабиринта";
    protected static final String CHOOSE_START = "выберите стартовую точку";
    protected static final String CHOOSE_END = "выберите конечную точку";
    protected static final String INPUT_SIZE_LABEL_HEIGHT_NAME = "введите размер лабиринта (4 <= size <= 20)";

    /**
     * Подготовка Label
     *
     * @param name имя Label
     * @return подготовленный к прикреплению на экран Label.
     */
    protected JLabel prepareLabel(String name) {
        return new JLabel(name);
    }

    /**
     * Подготавливает к прикреплению на экран ComboBox
     *
     * @param comboBoxItems элементы ComboBox.
     * @return подготовленный к прикреплению на экран ComboBox.
     */
    protected JComboBox<String> prepareComboBox(String[] comboBoxItems) {
        return new JComboBox<>(comboBoxItems);
    }

    /**
     * Подготавливает текстовое поле ввода.
     */
    protected JTextField prepareTextField() {
        return new JTextField();
    }
}
