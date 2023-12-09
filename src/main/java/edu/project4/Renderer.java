package edu.project4;

import edu.project4.models.FractalImage;
import edu.project4.models.Pixel;
import edu.project4.models.Point;
import edu.project4.models.RGB;
import edu.project4.models.Rect;
import edu.project4.models.flame_models.FlameCoefficient;
import edu.project4.models.flame_models.FlameSettings;
import edu.project4.transform_functions.Transform;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public final class Renderer {
    private static final int DEBUG_STEPS_START = -20;

    private Renderer() {

    }

    public static FractalImage render(
        FractalImage canvas,
        FlameSettings flameSettings,
        Rect rect,
        List<Transform> transformFunctionsList,
        int count
    ) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(count)) {
            for (int num = 0; num < flameSettings.samples(); num++) {
                executorService.submit(() -> {
                    var randomPoint = generateRandomPoint(rect);

                    for (int step = DEBUG_STEPS_START; step < flameSettings.iterations(); step++) {
                        int i = ThreadLocalRandom.current().nextInt(flameSettings.coefficient().length);

                        var transformationPoint = affineTransformation(randomPoint, flameSettings.coefficient()[i]);
                        randomPoint = nonLinearTransformation(transformationPoint, transformFunctionsList);

                        if (step > 0) {
                            double theta = 0.0;
                            for (int s = 0; s < flameSettings.symmetry(); s++) {
                                theta += ((2 * Math.PI) / (flameSettings.symmetry()));

                                var rotatePoint = rotate(randomPoint, theta);

                                if (isValidRotate(rotatePoint, rect)) {
                                    var canvasPoint = getCanvasPixel(rotatePoint, canvas, rect);

                                    if (isValidCanvasPoint(canvasPoint, canvas)) {
                                        synchronized (canvas.pixels()[(int) canvasPoint.x()][(int) canvasPoint.y()]) {
                                            var canvasPixel =
                                                canvas.pixels()[(int) canvasPoint.x()][(int) canvasPoint.y()];

                                            var newRGB = getNewRGB(canvasPixel, flameSettings.coefficient()[i]);

                                            canvas.pixels()[(int) canvasPoint.x()][(int) canvasPoint.y()] =
                                                new Pixel(
                                                    newRGB,
                                                    canvasPixel.hitCount() + 1,
                                                    canvasPixel.normalize()
                                                );
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
            executorService.shutdown();
        }
        return canvas;
    }

    private static Point affineTransformation(Point point, FlameCoefficient coefficient) {
        double x = coefficient.a() * point.x() + coefficient.b() * point.y()
            + coefficient.c();
        double y = coefficient.d() * point.x() + coefficient.e() * point.y()
            + coefficient.f();

        return new Point(x, y);
    }

    private static Point nonLinearTransformation(Point point, List<Transform> nonLinearTransform) {
        var randomTransform = nonLinearTransform.get(ThreadLocalRandom.current().nextInt(nonLinearTransform.size()));

        return randomTransform.apply(point);
    }

    private static Point rotate(Point point, double theta) {
        double xRot = point.x() * Math.cos(theta) - point.y() * Math.sin(theta);
        double yRot = point.x() * Math.sin(theta) + point.y() * Math.cos(theta);

        return new Point(xRot, yRot);
    }

    private static Point generateRandomPoint(Rect rect) {
        double x = ThreadLocalRandom.current().nextDouble(rect.xMin(), rect.xMax());
        double y = ThreadLocalRandom.current().nextDouble(rect.yMin(), rect.yMax());

        return new Point(x, y);
    }

    private static Point getCanvasPixel(Point point, FractalImage fractalImage, Rect rect) {
        int x1 = fractalImage.height()
            - (int) (((rect.xMax() - point.x()) / (rect.xMax() - rect.xMin())) * fractalImage.height());
        int y1 = fractalImage.weight()
            - (int) (((rect.yMax() - point.y()) / (rect.yMax() - rect.yMin())) * fractalImage.weight());

        return new Point(x1, y1);
    }

    private static RGB getNewRGB(Pixel pixel, FlameCoefficient coefficient) {
        char r;
        char g;
        char b;

        if (pixel.hitCount() == 0) {
            r = coefficient.rgb().r();
            g = coefficient.rgb().g();
            b = coefficient.rgb().b();
        } else {
            r = (char) ((pixel.rgb().r() + coefficient.rgb().r()) / 2);
            g = (char) ((pixel.rgb().g() + coefficient.rgb().g()) / 2);
            b = (char) ((pixel.rgb().b() + coefficient.rgb().b()) / 2);
        }

        return new RGB(r, g, b);
    }

    private static boolean isValidRotate(Point point, Rect rect) {
        return point.x() >= rect.xMin() && point.x() <= rect.xMax()
            && point.y() >= rect.yMin() && point.y() <= rect.yMax();
    }

    private static boolean isValidCanvasPoint(Point point, FractalImage fractalImage) {
        return point.x() < fractalImage.height() && point.y() < fractalImage.weight();
    }
}
