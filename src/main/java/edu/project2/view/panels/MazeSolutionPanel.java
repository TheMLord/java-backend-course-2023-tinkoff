package edu.project2.view.panels;

import edu.project2.model.Coordinate;
import edu.project2.model.Maze;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 * Панель с отрисованным лабиринтом и результатом его решения.
 * Нужна для демонстрации работа алгоритмов поиска.
 */
public class MazeSolutionPanel extends MazeOriginalPanel {
    private final List<Coordinate> solve;
    private final Coordinate start;
    private final Coordinate end;
    private static final int FOUR_COEFFICIENT = 4;
    private static final int TWO_COEFFICIENT = 2;

    private boolean isStartSet;
    private boolean isEndSet;

    private int lengthPaintPath;

    /**
     * Конструктор класса.
     *
     * @param maze  исходный лабиринт.
     * @param path  путь прохождения лабиринта.
     * @param start позиция начала пути.
     * @param end   позиция конца пути
     * @param size  размер лабиринта
     */
    public MazeSolutionPanel(Maze maze, List<Coordinate> path, Coordinate start, Coordinate end, int size) {
        super(maze, size);
        this.solve = path;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x;
        int y;

        paintPosition(start, Color.GREEN, g);
        isStartSet = true;

        paintPosition(end, Color.RED, g);
        isEndSet = true;

        g.setColor(Color.BLUE);

        for (Coordinate point : solve) {
            x = point.col() * CELL_SIZE + CELL_SIZE / TWO_COEFFICIENT;
            y = point.row() * CELL_SIZE + CELL_SIZE / TWO_COEFFICIENT;
            g.fillOval(
                x - CELL_SIZE / FOUR_COEFFICIENT,
                y - CELL_SIZE / FOUR_COEFFICIENT,
                CELL_SIZE / TWO_COEFFICIENT,
                CELL_SIZE / TWO_COEFFICIENT
            );
            lengthPaintPath++;
        }
    }

    /**
     * Метод отрисовывает позиции старта или конца пути.
     */
    private void paintPosition(Coordinate position, Color color, Graphics g) {
        int x;
        int y;

        g.setColor(color);

        x = position.col() * CELL_SIZE + CELL_SIZE / TWO_COEFFICIENT;
        y = position.row() * CELL_SIZE + CELL_SIZE / TWO_COEFFICIENT;
        g.fillOval(
            x - CELL_SIZE / FOUR_COEFFICIENT,
            y - CELL_SIZE / FOUR_COEFFICIENT,
            CELL_SIZE / TWO_COEFFICIENT,
            CELL_SIZE / TWO_COEFFICIENT
        );
    }

    /**
     * Метод возвращает отладочную информацию, чтобы понять, устанавливается ли позиция начала пути.
     */
    public boolean getDebuggingDataIsSetStart() {
        return this.isStartSet;
    }

    /**
     * Метод возвращает отладочную информацию, чтобы понять, устанавливается ли позиция начала пути.
     */
    public boolean getDebuggingDataIsSetEnd() {
        return this.isEndSet;
    }

    /**
     * Метод возвращает отладочную информацию, чтобы понять, отрисовывается ли путь
     */
    public int getDebuggingDataLengthPath() {
        return this.lengthPaintPath;
    }

}
