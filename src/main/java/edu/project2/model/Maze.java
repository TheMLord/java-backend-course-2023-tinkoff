package edu.project2.model;

/**
 * Class model of a maze that consists of a two-dimensional array of cells.
 */
public class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    /**
     * Class constructor.
     *
     * @param height the size of the maze in height.
     * @param width  the size of the maze in width.
     * @param grid   the matrix defining the maze.
     */
    public Maze(int height, int width, Cell[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
    }
}
