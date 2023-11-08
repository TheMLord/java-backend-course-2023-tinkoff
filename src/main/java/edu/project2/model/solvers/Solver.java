package edu.project2.model.solvers;

import edu.project2.model.Cell;
import edu.project2.model.Coordinate;
import edu.project2.model.Maze;
import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс с контрактом для алгоритмов решения лабиринта.
 * Содержит описание контракта поиска решения и
 * метод для нахождения соседей в графе (используется в алгоритмах BFS и DFS).
 */
public abstract class Solver {
    public abstract List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);

    private static final int PATH_OPTIONS = 4;

    /**
     * Метод поиска соседей для текущей клетки.
     * Соседи - ближайщие клетки к текущей, которые не являются стенами.
     *
     * @param maze                  лабиринт.
     * @param currentCellCoordinate координаты текущей клетки лабиринта.
     * @return возвращает список координат соседей.
     */
    protected List<Coordinate> getNeighbors(Maze maze, Coordinate currentCellCoordinate) {
        List<Coordinate> neighbors = new ArrayList<>();
        int rows = maze.getSize();
        int cols = maze.getSize();

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for (int i = 0; i < PATH_OPTIONS; i++) {
            int newRow = currentCellCoordinate.row() + dx[i];
            int newCol = currentCellCoordinate.col() + dy[i];

            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                Cell neighborCell = maze.getGrid()[newRow][newCol];
                if (neighborCell.cellType() == Cell.CellType.PASSAGE) {
                    neighbors.add(new Coordinate(newRow, newCol));
                }
            }
        }
        return neighbors;
    }

}
