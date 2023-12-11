package edu.project4.processors;

import edu.project4.models.FractalImage;
import edu.project4.models.Pixel;
import edu.project4.models.RGB;
import edu.project4.models.flame_models.FlameSettings;

/**
 * Класс обработок изображения.
 */
public final class ImageProcessor {
    private ImageProcessor() {

    }

    /**
     * Пост-обработка изображение с помощью логарифмической гамма-коррекции для уменьшения шума.
     *
     * @param canvas        изображение.
     * @param flameSettings настройки пламени.
     * @return возвращает обработанное алгоритмом логарифмической гамма-коррекции изображение.
     */
    public static FractalImage gammaCorrection(FractalImage canvas, FlameSettings flameSettings) {
        double max = 0.0;

        double gamma = flameSettings.gamma();

        for (int i = 0; i < canvas.height(); i++) {
            for (int j = 0; j < canvas.weight(); j++) {
                var pixel = canvas.pixels()[i][j];
                if (pixel.hitCount() != 0) {
                    double newNormal = Math.log10(pixel.hitCount());

                    if (newNormal > max) {
                        max = newNormal;
                    }
                    canvas.pixels()[i][j] = new Pixel(pixel.rgb(), pixel.hitCount(), newNormal);
                }
            }
        }
        for (int i = 0; i < canvas.height(); i++) {
            for (int j = 0; j < canvas.weight(); j++) {
                var pixel = canvas.pixels()[i][j];

                var normal = pixel.normalize() / max;

                char r = (char) (((double) pixel.rgb().r()) * Math.pow(normal, (1.0 / gamma)));
                char g = (char) (((double) pixel.rgb().g()) * Math.pow(normal, (1.0 / gamma)));
                char b = (char) (((double) pixel.rgb().b()) * Math.pow(normal, (1.0 / gamma)));

                canvas.pixels()[i][j] = new Pixel(new RGB(r, g, b), pixel.hitCount(), pixel.normalize());
            }
        }
        return canvas;
    }
}
