package edu.project4.models;

/**
 * Модель цвета в формате RGB.
 *
 * @param r насыщенность красного цвета (от 0 до 255).
 * @param g насыщенность зеленого цвета (от 0 до 255).
 * @param b насыщенность голубого цвета (от 0 до 255).
 */
public record RGB(char r, char g, char b) {
}
