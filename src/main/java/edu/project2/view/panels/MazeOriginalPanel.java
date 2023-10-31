package edu.project2.view.panels;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class MazeOriginalPanel extends JPanel {
    private final char[][] maze;
//    private final ArrayList<Coordinate> solve;

    //ArrayList<Coordinate> path
    public MazeOriginalPanel(char[][] maze) {
        this.maze = maze;
//        this.solve = path;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellSize = 30; // Размер ячейки
        int x, y;

        // Отрисовка лабиринта
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                x = j * cellSize;
                y = i * cellSize;

                if (maze[i][j] == '█') {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, cellSize, cellSize);
                } else if (maze[i][j] == 'S') {
                    g.setColor(Color.GREEN);
                    g.fillRect(x, y, cellSize, cellSize);
                } else if (maze[i][j] == 'E') {
                    g.setColor(Color.RED);
                    g.fillRect(x, y, cellSize, cellSize);
                }
            }
        }
//        // Отрисовка пути
//        g.setColor(Color.BLUE);
//        for (Coordinate point : solve) {
//            x = point.row() * cellSize + cellSize / 2;
//            y = point.col() * cellSize + cellSize / 2;
//            g.fillOval(x - cellSize / 4, y - cellSize / 4, cellSize / 2, cellSize / 2);
//        }
    }
}
