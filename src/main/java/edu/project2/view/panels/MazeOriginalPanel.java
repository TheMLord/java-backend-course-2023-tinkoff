package edu.project2.view.panels;

import edu.project2.model.Cell;
import edu.project2.model.Maze;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Панель с отрисовкой начального лабиринта.
 * Панель нужна для просмотра исходного лабиринта, она остается без изменений.
 */
public class MazeOriginalPanel extends JPanel {
    protected static final int CELL_SIZE = 20;
    protected final Maze maze;
    protected int countWall;
    protected int countPassage;

    public MazeOriginalPanel(Maze maze, int size) {
        this.maze = maze;
        setSize(size * CELL_SIZE, size * CELL_SIZE);
        setSize(size * CELL_SIZE, size * CELL_SIZE);
        setPreferredSize(new Dimension(size * CELL_SIZE, size * CELL_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x;
        int y;

        int mazeSize = this.maze.getSize();

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                x = j * CELL_SIZE;
                y = i * CELL_SIZE;

                if (this.maze.getGrid()[i][j].cellType().equals(Cell.CellType.WALL)) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    countWall++;
                } else {
                    countPassage++;
                }
            }
        }
    }

    /**
     * Отладочный метод для проверки отрисовки нужного количества стен.
     *
     * @return количество отрисованных стен.
     */
    public int getDebuggingDataCountWall() {
        return this.countWall;
    }

    /**
     * Отладочный метод для проверки отрисовки нужного количества проходов.
     *
     * @return количество отрисованных проходов.
     */
    public int getDebuggingDataCountPassage() {
        return this.countPassage;
    }

}
