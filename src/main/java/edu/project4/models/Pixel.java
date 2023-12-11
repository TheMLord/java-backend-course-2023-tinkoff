package edu.project4.models;

/**
 * Модель пикселя.
 *
 * @param rgb       цвет пикселя в палитре RGB.
 * @param hitCount  количество попаданий алгоритмом в пиксель.
 * @param normalize параметр нормализации для пост-обработки.
 */
public record Pixel(RGB rgb, int hitCount, double normalize) {
}
