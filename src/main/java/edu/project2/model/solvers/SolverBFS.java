package edu.project2.model.solvers;

import edu.project2.model.Coordinate;
import edu.project2.model.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Реализация алгоритма поиска в ширину для решения лабиринта.
 */
public class SolverBFS extends Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Map<Coordinate, Coordinate> parentMap = new HashMap<>();
        Queue<Coordinate> queue = new LinkedList<>();
        Set<Coordinate> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (current.equals(end)) {
                while (!current.equals(start)) {
                    path.add(current);
                    current = parentMap.get(current);
                }
                path.add(start);
                Collections.reverse(path);
                return path;
            }

            for (Coordinate neighbor : getNeighbors(maze, current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList();
    }
}
