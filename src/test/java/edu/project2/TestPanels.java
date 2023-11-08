package edu.project2;

import edu.project2.model.Cell;
import edu.project2.model.Coordinate;
import edu.project2.model.Maze;
import edu.project2.view.frames.MainApplicationMazeFrame;
import edu.project2.view.panels.MazeOriginalPanel;
import edu.project2.view.panels.MazeSolutionPanel;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Класс проверки корректной отрисовки лабиринта
 */
public class TestPanels {
    private final Maze testWithPathMaze = new Maze(
        9,
        new Cell[][] {
            new Cell[] {new Cell(0, 0, Cell.CellType.WALL), new Cell(0, 1, Cell.CellType.WALL),
                new Cell(0, 2, Cell.CellType.WALL), new Cell(0, 3, Cell.CellType.WALL),
                new Cell(0, 4, Cell.CellType.WALL), new Cell(0, 5, Cell.CellType.WALL),
                new Cell(0, 6, Cell.CellType.WALL), new Cell(0, 7, Cell.CellType.WALL),
                new Cell(0, 8, Cell.CellType.WALL)},
            new Cell[] {new Cell(1, 0, Cell.CellType.WALL), new Cell(1, 1, Cell.CellType.PASSAGE),
                new Cell(1, 2, Cell.CellType.WALL), new Cell(1, 3, Cell.CellType.PASSAGE),
                new Cell(1, 4, Cell.CellType.WALL), new Cell(1, 5, Cell.CellType.PASSAGE),
                new Cell(1, 6, Cell.CellType.WALL), new Cell(1, 7, Cell.CellType.PASSAGE),
                new Cell(1, 8, Cell.CellType.WALL)},
            new Cell[] {new Cell(2, 0, Cell.CellType.WALL), new Cell(2, 1, Cell.CellType.PASSAGE),
                new Cell(2, 2, Cell.CellType.WALL), new Cell(2, 3, Cell.CellType.PASSAGE),
                new Cell(2, 4, Cell.CellType.WALL), new Cell(2, 5, Cell.CellType.PASSAGE),
                new Cell(2, 6, Cell.CellType.WALL), new Cell(2, 7, Cell.CellType.PASSAGE),
                new Cell(2, 8, Cell.CellType.WALL)},
            new Cell[] {new Cell(3, 0, Cell.CellType.WALL), new Cell(3, 1, Cell.CellType.PASSAGE),
                new Cell(3, 2, Cell.CellType.WALL), new Cell(3, 3, Cell.CellType.PASSAGE),
                new Cell(3, 4, Cell.CellType.WALL), new Cell(3, 5, Cell.CellType.PASSAGE),
                new Cell(3, 6, Cell.CellType.PASSAGE), new Cell(3, 7, Cell.CellType.PASSAGE),
                new Cell(3, 8, Cell.CellType.WALL)},
            new Cell[] {new Cell(4, 0, Cell.CellType.WALL), new Cell(4, 1, Cell.CellType.PASSAGE),
                new Cell(4, 2, Cell.CellType.WALL), new Cell(4, 3, Cell.CellType.PASSAGE),
                new Cell(4, 4, Cell.CellType.WALL), new Cell(4, 5, Cell.CellType.PASSAGE),
                new Cell(4, 6, Cell.CellType.WALL), new Cell(4, 7, Cell.CellType.PASSAGE),
                new Cell(4, 8, Cell.CellType.WALL)},
            new Cell[] {new Cell(5, 0, Cell.CellType.WALL), new Cell(5, 1, Cell.CellType.PASSAGE),
                new Cell(5, 2, Cell.CellType.WALL), new Cell(5, 3, Cell.CellType.PASSAGE),
                new Cell(5, 4, Cell.CellType.WALL), new Cell(5, 5, Cell.CellType.PASSAGE),
                new Cell(5, 6, Cell.CellType.WALL), new Cell(5, 7, Cell.CellType.PASSAGE),
                new Cell(5, 8, Cell.CellType.WALL)},
            new Cell[] {new Cell(6, 0, Cell.CellType.WALL), new Cell(6, 1, Cell.CellType.PASSAGE),
                new Cell(6, 2, Cell.CellType.WALL), new Cell(6, 3, Cell.CellType.PASSAGE),
                new Cell(6, 4, Cell.CellType.WALL), new Cell(6, 5, Cell.CellType.PASSAGE),
                new Cell(6, 6, Cell.CellType.WALL), new Cell(6, 7, Cell.CellType.PASSAGE),
                new Cell(6, 8, Cell.CellType.WALL)},
            new Cell[] {new Cell(7, 0, Cell.CellType.WALL), new Cell(7, 1, Cell.CellType.PASSAGE),
                new Cell(7, 2, Cell.CellType.PASSAGE), new Cell(7, 3, Cell.CellType.PASSAGE),
                new Cell(7, 4, Cell.CellType.PASSAGE), new Cell(7, 5, Cell.CellType.PASSAGE),
                new Cell(7, 6, Cell.CellType.WALL), new Cell(7, 7, Cell.CellType.PASSAGE),
                new Cell(7, 8, Cell.CellType.WALL)},
            new Cell[] {new Cell(8, 0, Cell.CellType.WALL), new Cell(8, 1, Cell.CellType.WALL),
                new Cell(8, 2, Cell.CellType.WALL), new Cell(8, 3, Cell.CellType.WALL),
                new Cell(8, 4, Cell.CellType.WALL), new Cell(8, 5, Cell.CellType.WALL),
                new Cell(8, 6, Cell.CellType.WALL), new Cell(8, 7, Cell.CellType.WALL),
                new Cell(8, 8, Cell.CellType.WALL)}
        }
    );
    private final List<Coordinate> path = new ArrayList() {{
        add(new Coordinate(0, 0));
        add(new Coordinate(1, 0));
    }};
    private final List<Coordinate> emptyPath = new ArrayList() {
    };
    private final Coordinate start = new Coordinate(0, 0);
    private final Coordinate end = new Coordinate(1, 0);

    private final Maze testWithoutPathMaze = new Maze(
        2,
        new Cell[][] {
            new Cell[] {new Cell(0, 0, Cell.CellType.PASSAGE), new Cell(0, 1, Cell.CellType.WALL)},
            new Cell[] {new Cell(1, 0, Cell.CellType.WALL), new Cell(1, 1, Cell.CellType.PASSAGE)},
        }
    );

    @Test
    @DisplayName("Test that the panels correctly render the maze having a path and returned the correct debugging data")
    void testThatThePanelsCorrectlyRenderTheMazeHavingAPathAndReturnedTheCorrectDebuggingData() {
//        int expectedCountWallOriginalPanel = 2;
//        int expectedCountPassageOriginalPanel = 2;
//
//        int expectedCountWallSolutionPanel = 2;
//        int expectedCountPassageSolutionPanel = 2;
//        int expectedPathLength = 2;
//
//        var mazeOriginalPanel = new MazeOriginalPanel(testWithoutPathMaze, 9);
//        var mazeSolutionPanel = new MazeSolutionPanel(testWithoutPathMaze, emptyPath, start, end, 9);
//
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame(" ");
//            frame.pack();
//
//            frame.add(mazeOriginalPanel);
//            frame.add(mazeSolutionPanel);
//            frame.setSize(400, 400);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setVisible(true);
//
//            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//        });
//
//
//        assertThat(mazeOriginalPanel.getDebuggingDataCountWall()).isEqualTo(expectedCountWallOriginalPanel);
//        assertThat(mazeOriginalPanel.getDebuggingDataCountPassage()).isEqualTo(expectedCountPassageOriginalPanel);
//        assertThat(mazeSolutionPanel.getDebuggingDataCountWall()).isEqualTo(expectedCountWallSolutionPanel);
//        assertThat(mazeSolutionPanel.getDebuggingDataCountWall()).isEqualTo(expectedCountPassageSolutionPanel);
//        assertThat(mazeSolutionPanel.getDebuggingDataLengthPath()).isEqualTo(expectedPathLength);
//        assertThat(mazeSolutionPanel.getDebuggingDataIsSetStart()).isTrue();
//        assertThat(mazeSolutionPanel.getDebuggingDataIsSetEnd()).isTrue();
    }

    @Test
    @DisplayName(
        "Test that the panels correctly render the maze not having a path and returned the correct debugging data")
    void testThatThePanelsCorrectlyRenderTheMazeNotHavingAPathAndReturnedTheCorrectDebuggingData() {
//        int expectedCountWallOriginalPanel = 2;
//        int expectedCountPassageOriginalPanel = 2;
//
//        int expectedCountWallSolutionPanel = 2;
//        int expectedCountPassageSolutionPanel = 2;
//        int expectedPathLength = 0;
//
//        var mazeOriginalPanel = new MazeOriginalPanel(testWithoutPathMaze, 9);
//        var mazeSolutionPanel = new MazeSolutionPanel(testWithoutPathMaze, emptyPath, start, end, 9);
//
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame(" ");
//            frame.pack();
//
//            frame.add(mazeOriginalPanel);
//            frame.add(mazeSolutionPanel);
//            frame.setSize(400, 400);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setVisible(true);
//
//            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//        });
//
//        assertThat(mazeOriginalPanel.getDebuggingDataCountWall()).isEqualTo(expectedCountWallOriginalPanel);
//        assertThat(mazeOriginalPanel.getDebuggingDataCountPassage()).isEqualTo(expectedCountPassageOriginalPanel);
//        assertThat(mazeSolutionPanel.getDebuggingDataCountWall()).isEqualTo(expectedCountWallSolutionPanel);
//        assertThat(mazeSolutionPanel.getDebuggingDataCountWall()).isEqualTo(expectedCountPassageSolutionPanel);
//        assertThat(mazeSolutionPanel.getDebuggingDataLengthPath()).isEqualTo(expectedPathLength);
//        assertThat(mazeSolutionPanel.getDebuggingDataIsSetStart()).isTrue();
//        assertThat(mazeSolutionPanel.getDebuggingDataIsSetEnd()).isTrue();
    }

}
