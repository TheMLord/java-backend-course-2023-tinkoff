package edu.project2.model;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Class model of a maze that consists of a two-dimensional array of cells.
 */
public final class Maze {
    private final int size;
    private final Cell[][] grid;

    /**
     * Class constructor.
     *
     * @param size the size of the maze.
     * @param grid the matrix defining the maze.
     */
    public Maze(int size, Cell[][] grid) {
        this.size = size;
        this.grid = grid;
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public @NotNull List<Coordinate> getEmptyCells() {
        List<Coordinate> emptyCoordinateList = new ArrayList<>();

        for (var cells : this.grid) {
            for (var cell : cells) {
                if (cell.cellType().equals(Cell.CellType.PASSAGE)) {
                    emptyCoordinateList.add(new Coordinate(cell.row(), cell.col()));
                }
            }
        }
        return emptyCoordinateList;
    }
}
