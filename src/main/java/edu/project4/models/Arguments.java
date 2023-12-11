package edu.project4.models;

import edu.project4.models.flame_models.FlameSettings;
import edu.project4.transform_functions.Transform;
import java.nio.file.Path;
import java.util.List;

/**
 * Модель входных аргументов для программы.
 *
 * @param image                  сформированное изображение.
 * @param settings               настройки пламени.
 * @param rect                   параметры прямоугольника для алгоритма.
 * @param nonLinearTransformList массив нелинейных функций преобразования точки.
 * @param countThread            количество обрабатывающих изображение потоков.
 * @param pathToSave             путь для сохранения результата работы программы.
 * @param format                 формат сохраняемого изображения.
 */
public record Arguments(FractalImage image,
                        FlameSettings settings,
                        Rect rect,
                        List<Transform> nonLinearTransformList,
                        int countThread,
                        Path pathToSave,
                        ImageFormat format) {

}
