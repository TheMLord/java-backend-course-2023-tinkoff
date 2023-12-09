package edu.project4.utilities;

import edu.project4.models.RGB;
import edu.project4.models.Rect;
import edu.project4.models.flame_models.FlameCoefficient;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс утилитарных функций аффинных преобразований.
 */
public final class AffineUtils {
    private static final String COEFFICIENT_FILE_PARSER_ERROR = "coefficient file contains incorrect data";

    private static final int MIN_RGB_BORDER = 0;
    private static final int MAX_RGB_BORDER = 256;

    private static final double MIN_COEFFICIENT_BORDER = -1.5;
    private static final double MAX_COEFFICIENT_BORDER = 1.5;

    private static final String COEFFICIENT_SEPARATOR = "\\s+";
    private static final int A_COEFFICIENT_INDEX = 0;
    private static final int B_COEFFICIENT_INDEX = 1;
    private static final int C_COEFFICIENT_INDEX = 2;
    private static final int D_COEFFICIENT_INDEX = 3;
    private static final int E_COEFFICIENT_INDEX = 4;
    private static final int F_COEFFICIENT_INDEX = 5;
    private static final int RED_COEFFICIENT_INDEX = 6;
    private static final int GREEN_COEFFICIENT_INDEX = 7;
    private static final int BLUE_COEFFICIENT_INDEX = 8;

    private AffineUtils() {

    }

    /**
     * Метод генерации массива коэффициентов для аффинных преобразований с базовым цветом
     *
     * @param countCoefficients количество различных наборов аффинных преобразований.
     */
    public static FlameCoefficient[] generateAffineCoefficients(int countCoefficients) {
        var resultCoefficient = new FlameCoefficient[countCoefficients];

        var curCount = 0;

        double a;
        double b;
        double c;
        double d;
        double e;
        double f;

        while (curCount < countCoefficients) {
            a = generateCoefficient();
            b = generateCoefficient();
            c = generateCoefficient();
            d = generateCoefficient();
            e = generateCoefficient();
            f = generateCoefficient();

            if (isValidCoefficient(a, b, d, e)) {
                resultCoefficient[curCount] = new FlameCoefficient(a, b, c, d, e, f, generateRGB());
                curCount += 1;
            }
        }

        return resultCoefficient;
    }

    /**
     * Метод выполняющий считывание набора пользовательских коэффициентов с цветами.
     *
     * @param path путь до файла с коэффициентами
     * @return набор коэффициентов для игры хаоса.
     */
    public static FlameCoefficient[] parseFlameCoefficient(Path path) throws IOException {
        try {
            List<String> lines = Files.lines(path)
                .filter(line -> !line.trim().isEmpty())
                .toList();

            FlameCoefficient[] coefficients = new FlameCoefficient[lines.size()];

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] values = line.split(COEFFICIENT_SEPARATOR);

                double a = Double.parseDouble(values[A_COEFFICIENT_INDEX]);
                double b = Double.parseDouble(values[B_COEFFICIENT_INDEX]);
                double c = Double.parseDouble(values[C_COEFFICIENT_INDEX]);
                double d = Double.parseDouble(values[D_COEFFICIENT_INDEX]);
                double e = Double.parseDouble(values[E_COEFFICIENT_INDEX]);
                double f = Double.parseDouble(values[F_COEFFICIENT_INDEX]);
                char red = (char) Integer.parseInt(values[RED_COEFFICIENT_INDEX]);
                char green = (char) Integer.parseInt(values[GREEN_COEFFICIENT_INDEX]);
                char blue = (char) Integer.parseInt(values[BLUE_COEFFICIENT_INDEX]);

                coefficients[i] = new FlameCoefficient(a, b, c, d, e, f, new RGB(red, green, blue));
            }

            return coefficients;
        } catch (IOException e) {
            throw new IOException(COEFFICIENT_FILE_PARSER_ERROR);
        }
    }

    /**
     * Метод вычисления аффинного прямоугольника.
     *
     * @param xRes высота итогового изображения.
     * @param yRes ширина итогового изображения.
     */
    public static Rect getAffineRect(int xRes, int yRes) {
        double xMax = (double) xRes / yRes;
        double xMin = -xMax;
        double yMax = (double) Math.round((double) xRes / yRes / 2.0);
        double yMin = -yMax;

        return new Rect(xMin, xMax, yMin, yMax);
    }

    /**
     * Метод проверки валидности коэффициентов по правилам СИФ.
     */
    private static boolean isValidCoefficient(double a, double b, double d, double e) {
        return (a * a + d * d < 1 && b * b + e * e < 1
            && a * a + b * b + d * d + e * e < 1 + (a * e - b * d) * (a * e - b * d));
    }

    /**
     * Метод генерации случайного RGB цвета.
     */
    private static RGB generateRGB() {
        return new RGB(
            (char) ThreadLocalRandom.current().nextInt(MIN_RGB_BORDER, MAX_RGB_BORDER),
            (char) ThreadLocalRandom.current().nextInt(MIN_RGB_BORDER, MAX_RGB_BORDER),
            (char) ThreadLocalRandom.current().nextInt(MIN_RGB_BORDER, MAX_RGB_BORDER)
        );
    }

    /**
     * Метод генерации случайного аффинного коэффициента.
     */
    private static double generateCoefficient() {
        return ThreadLocalRandom.current().nextDouble(MIN_COEFFICIENT_BORDER, MAX_COEFFICIENT_BORDER);

    }

}
