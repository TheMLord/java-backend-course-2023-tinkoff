package edu.project4.models;

/**
 * Модель фрактального пламени.
 *
 * @param pixels массив пикселей под размер финального изображения.
 * @param height высота изображения.
 * @param weight ширина изображения.
 */
public record FractalImage(Pixel[][] pixels, int height, int weight) {
}
