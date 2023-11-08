package edu.project2.model;

/**
 * Class maze cell model.
 *
 * @param row  the position of the cell in the row of the matrix.
 * @param col  the position of the cell in the matrix column.
 * @param cellType definition of a matrix cell.
 */
public record Cell(int row, int col, CellType cellType) {
    public enum CellType {
        WALL, PASSAGE
    }
}
