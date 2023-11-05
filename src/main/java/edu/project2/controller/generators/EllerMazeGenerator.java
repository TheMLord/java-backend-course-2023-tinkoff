package edu.project2.controller.generators;

import edu.project2.model.Cell;
import edu.project2.model.Maze;
import java.util.Random;

/**
 * Класс с реализацией алгоритма Эллера генерации лабиринта.
 */
public final class EllerMazeGenerator implements Generator {
    @Override
    public Maze generate(int size) {
        return generateEllerMaze(size);
    }

    private Maze generateEllerMaze(int size) {
        int increasedOutputSizeMaze = size * 2 + 1;

        var maze = initializingMaze(increasedOutputSizeMaze);

        var resultMaze = organizeEllerCycle(maze, size, increasedOutputSizeMaze);

        return new Maze(increasedOutputSizeMaze, resultMaze);
    }

    private Cell[][] initializingMaze(int increasedOutputSizeMaze) {
        Cell[][] initialMaze = new Cell[increasedOutputSizeMaze][increasedOutputSizeMaze];
        for (int i = 0; i < increasedOutputSizeMaze; i++) {
            for (int j = 0; j < increasedOutputSizeMaze; j++) {
                if ((i % 2 == 1) && (j % 2 == 1)) {
                    initialMaze[i][j] = new Cell(i, j, Cell.CellType.PASSAGE);
                } else if (((i % 2 == 1) && (j % 2 == 0) && (j != 0) && (j != increasedOutputSizeMaze - 1))
                    || ((j % 2 == 1) && (i % 2 == 0) && (i != 0) && (i != increasedOutputSizeMaze - 1))) {

                    initialMaze[i][j] = new Cell(i, j, Cell.CellType.PASSAGE);
                } else {
                    initialMaze[i][j] = new Cell(i, j, Cell.CellType.WALL);
                }
            }
        }
        return initialMaze;
    }

    /**
     * Создание Эллерова цикла в лабиринте.
     *
     * @param maze                    лабиринт.
     * @param originalSize            исходный размер лабиринта.
     * @param increasedOutputSizeMaze размер лабиринта, который будет в результате работы алгоритма.
     */
    private Cell[][] organizeEllerCycle(Cell[][] maze, int originalSize, int increasedOutputSizeMaze) {
        var cells = maze;

        int[] rowSet = new int[originalSize];

        int set = 1;
        Random random = new Random();
        for (int i = 0; i < originalSize; i++) {
            for (int j = 0; j < originalSize; j++) {
                if (rowSet[j] == 0) {
                    rowSet[j] = set++;
                }
            }

            for (int j = 0; j < originalSize - 1; j++) {
                int rightWall = random.nextInt(2);
                if (rightWall == 1 || rowSet[j] == rowSet[j + 1]) {
                    cells[i * 2 + 1][j * 2 + 2] = new Cell(i * 2 + 1, j * 2 + 2, Cell.CellType.WALL);
                } else {
                    int changingSet = rowSet[j + 1];
                    for (int l = 0; l < originalSize; l++) {
                        if (rowSet[l] == changingSet) {
                            rowSet[l] = rowSet[j];
                        }
                    }
                }
            }
            arrangeWallsAndPassageWays(rowSet, originalSize, cells, i);

        }

        return finishAlgorithm(originalSize, rowSet, cells, increasedOutputSizeMaze);
    }

    /**
     * Растанова проходов и стен
     */
    private void arrangeWallsAndPassageWays(int[] rowSet, int originalSize, Cell[][] maze, int i) {
        Random random = new Random();
        for (int j = 0; j < originalSize; j++) {
            int bottomWall = random.nextInt(2);
            int countCurrentSet = 0;
            for (int l = 0; l < originalSize; l++) {
                if (rowSet[j] == rowSet[l]) {
                    countCurrentSet++;
                }
            }
            if (bottomWall == 1 && countCurrentSet != 1) {
                maze[i * 2 + 2][j * 2 + 1] = new Cell(i * 2 + 2, j * 2 + 1, Cell.CellType.WALL);
            }
        }

        if (i != originalSize - 1) {
            for (int j = 0; j < originalSize; j++) {
                int countHole = 0;
                for (int l = 0; l < originalSize; l++) {
                    if (rowSet[l] == rowSet[j]
                        && maze[i * 2 + 2][l * 2 + 1].cellType().equals(Cell.CellType.PASSAGE)) {
                        countHole++;
                    }
                }
                if (countHole == 0) {
                    maze[i * 2 + 2][j * 2 + 1] = new Cell(i * 2 + 2, j * 2 + 1, Cell.CellType.PASSAGE);
                }
            }
        }
    }

    /**
     * Завершение алгоритма
     */
    private Cell[][] finishAlgorithm(int originalSize, int[] rowSet, Cell[][] maze, int increasedOutputSizeMaze) {
        for (int j = 0; j < originalSize - 1; j++) {
            if (rowSet[j] != rowSet[j + 1]) {

                maze[increasedOutputSizeMaze - 2][j * 2 + 2] =
                    new Cell(increasedOutputSizeMaze - 2, j * 2 + 2, Cell.CellType.PASSAGE);

            }
        }
        return maze;
    }
}
