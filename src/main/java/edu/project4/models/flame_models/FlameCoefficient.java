package edu.project4.models.flame_models;

import edu.project4.models.RGB;

/**
 * Модель коэффициента для аффинного преобразования.
 *
 * @param a   коэффициент a.
 * @param b   коэффициент b.
 * @param c   коэффициент c.
 * @param d   коэффициент d.
 * @param e   коэффициент e.
 * @param f   коэффициент f.
 * @param rgb базовый цвет аффинного преобразования.
 */
public record FlameCoefficient(double a, double b, double c, double d, double e, double f, RGB rgb) {
}
