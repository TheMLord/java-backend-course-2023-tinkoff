package edu.project4.utilities;

import edu.project4.models.FractalImage;
import edu.project4.models.ImageFormat;
import edu.project4.models.Pixel;
import edu.project4.models.RGB;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс утильных методов для изображения.
 */
public final class ImageUtils {
    private static final Logger IMAGE_UTILS_LOGGER = LogManager.getLogger(ImageUtils.class.getName());
    private static final int RGB_SHIFT_16 = 16;
    private static final int RGB_SHIFT_8 = 8;

    private static final String SAVE_ERROR = "Image saving error";

    private ImageUtils() {

    }

    /**
     * Метод сохранения изображения.
     *
     * @param canvas   изображение.
     * @param fileName имя файля для сохранения.
     * @param format   формат изображения.
     */
    public static void save(FractalImage canvas, Path fileName, ImageFormat format) {
        var image = transpose(canvas);

        var width = image.weight();
        var height = image.height();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pixel pixel = image.pixels()[i][j];
                RGB rgb = pixel.rgb();
                int color = (rgb.r() << RGB_SHIFT_16) | (rgb.g() << RGB_SHIFT_8) | rgb.b();

                bufferedImage.setRGB(i, j, color);
            }
        }
        try {
            ImageIO.write(bufferedImage, format.toString(), fileName.toFile());
        } catch (IOException e) {
            IMAGE_UTILS_LOGGER.info(SAVE_ERROR);
            IMAGE_UTILS_LOGGER.info(e.getMessage());
            IMAGE_UTILS_LOGGER.info(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Метод инициализации пикселей для изображения.
     * Инициализируется двумерный массив по размерам изображения с пикселями с начальными параметрами - черный цвет,
     * нулевое количество попаданий и нулевое число нормализации.
     *
     * @param height высота изображения.
     * @param weight ширина изображения.
     */
    public static Pixel[][] pixelsImageInit(int height, int weight) {
        Pixel[][] pixels = new Pixel[height][weight];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                pixels[i][j] = new Pixel(new RGB((char) 0, (char) 0, (char) 0), 0, 0.0);
            }
        }
        return pixels;
    }

    /**
     * Метод транспонирования изображения для сохранения.
     * На вход программе подается размер в виде 1920x1080 и на протяжении программы изображения воспринимается
     * как вертикальное. Метод позволяет перевернуть изображение и сделать его горизонтальным.
     */
    private static FractalImage transpose(FractalImage canvas) {
        var sizeX = canvas.height();
        var sizeY = canvas.weight();

        return new FractalImage(canvas.pixels(), sizeY, sizeX);
    }
}
