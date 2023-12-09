package edu.project4.models.flame_models;

/**
 * Модель настроек пламени.
 *
 * @param coefficient массив коэффициентов для СИФ и цветов.
 * @param samples     количество точек.
 * @param iterations  количество итераций.
 * @param symmetry    параметр симметрии (должен быть >= 1).
 * @param gamma       параметр гаммы изображения.
 */
public record FlameSettings(FlameCoefficient[] coefficient,
                            int samples,
                            int iterations,
                            int symmetry,
                            double gamma) {
}
