package edu.project2.controller.generators;

import edu.project2.model.Maze;

/**
 * Контракт генератора лабиринта
 */
public interface Generator {
    /**
     * Метод генерирует лабиринт по указанному размеру.
     *
     * @param size размер лабиринта.
     * @return возвращает сгенерированный лабиринт.
     */
    Maze generate(int size);
}
