package edu.project2.controller.panel_controllers;

import edu.project2.model.Coordinate;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JComboBox;


public class SolutionMazePanelController extends AbstractPanelController {
    private static final int ROW_PANEL = 6;
    private static final int COL_PANEL = 6;
    private final JComboBox solutionComboBox;
    private final JComboBox startCellsComboBox;
    private final JComboBox endCellsComboBox;
    private final List<Coordinate> emptyCells;

    public SolutionMazePanelController(List<Coordinate> emptyCells) {
        this.emptyCells = emptyCells;

        setLayout(new GridLayout(ROW_PANEL, COL_PANEL));

        this.solutionComboBox = prepareComboBox(new String[] {
            TypeSolutionAlgorithm.BFS.getNameAlgorithm(),
            TypeSolutionAlgorithm.DFS.getNameAlgorithm()
        });

        String[] cells = prepareEmptyCells(this.emptyCells);

        this.startCellsComboBox = prepareComboBox(
            cells
        );
        this.endCellsComboBox = prepareComboBox(
            cells
        );

        add(prepareLabel(CHOOSE_MAZE_SOLUTION_ALGORITHM_LABEL_NAME));
        add(solutionComboBox);

        add(prepareLabel(CHOOSE_START));
        add(startCellsComboBox);

        add(prepareLabel(CHOOSE_END));
        add(endCellsComboBox);
    }

    private String[] prepareEmptyCells(List<Coordinate> coordinates) {
        String[] cells = new String[coordinates.size()];

        for (int i = 0; i < cells.length; i++) {
            cells[i] = coordinates.get(i).row() + " " + coordinates.get(i).col();
        }
        return cells;
    }

    public String getSolutionComboBoxSelectedItem() {
        return (String) solutionComboBox.getSelectedItem();
    }

    public JComboBox getStartCellsComboBox() {
        return startCellsComboBox;
    }

    public JComboBox getEndCellsComboBox() {
        return endCellsComboBox;
    }

    public Coordinate getCoordinateChoice(JComboBox<String> coordinateComboBox) {
        var choice = coordinateComboBox.getSelectedItem().toString().split(" ");
        return new Coordinate(Integer.parseInt(choice[0]), Integer.parseInt(choice[1]));
    }

    public enum TypeSolutionAlgorithm {
        BFS("Поиск в ширину"), DFS("Поиск в глубину");
        private final String nameAlgorithm;

        TypeSolutionAlgorithm(String nameAlgorithm) {
            this.nameAlgorithm = nameAlgorithm;
        }

        public String getNameAlgorithm() {
            return nameAlgorithm;
        }
    }
}
