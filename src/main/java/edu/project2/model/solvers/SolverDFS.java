package edu.project2.model.solvers;

import edu.project2.model.Coordinate;
import edu.project2.model.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Реализация алгоритма поиска в глубину для решения лабиринта.
 */
public class SolverDFS extends Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Stack<Coordinate> stack = new Stack<>();
        stack.push(start);

        int size = maze.getSize();
        Coordinate[][] parentMap = new Coordinate[size][size];

        boolean[][] visited = new boolean[maze.getSize()][maze.getSize()];

        while (!stack.isEmpty()) {
            Coordinate current = stack.pop();

            if (current.equals(end)) {
                path.add(current);
                while (!current.equals(start)) {
                    path.add(current);
                    current = parentMap[current.row()][current.col()];
                }
                path.add(start);
                break;
            }

            visited[current.row()][current.col()] = true;

            List<Coordinate> neighbors = getNeighbors(maze, current);

            for (Coordinate neighbor : neighbors) {
                if (!visited[neighbor.row()][neighbor.col()]) {
                    stack.push(neighbor);
                    parentMap[neighbor.row()][neighbor.col()] = current;
                }
            }
        }

        Collections.reverse(path);
        return path;
    }
}
