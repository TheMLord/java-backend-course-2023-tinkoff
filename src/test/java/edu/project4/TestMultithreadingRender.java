package edu.project4;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class TestMultithreadingRender {
    private static final double NANO_T0_SECONDS = 0.000000001;

    private static final String PATH_TO_COEFF =
        Paths.get(
            String.valueOf(Paths.get("").toAbsolutePath()),
            "src/test/java/edu/project4/coeff.txt"
        ).toString();

    private static final String MULTITHREADING_ARG = "--size 1920x1080 " +
        "--nonlinear 2 Diamond Spiral " +
        "--coeff " + PATH_TO_COEFF + " " +
        "--samples 20000 " +
        "--iterations 1500 " +
        "--symmetry 2 " +
        "--gamma 2.2 " +
        "--threads 5 " +
        "--pathToSave %s " +
        "--formatImg JPEG";

    private static final String SINGLE_THREADING_ARG = "--size 1920x1080 " +
        "--nonlinear 2 Diamond Spiral " +
        "--coeff " + PATH_TO_COEFF + " " +
        "--samples 20000 " +
        "--iterations 1500 " +
        "--symmetry 2 " +
        "--gamma 2.2 " +
        "--threads 1 " +
        "--pathToSave %s " +
        "--formatImg JPEG";

    @Test
    @DisplayName(
        "Test that multithreaded image rendering is faster than single-threaded rendering under the same conditions and returned the benchmark result")
    void testThatMultithreadedImageRenderingIsFasterThanSingleThreadedRenderingUnderTheSameConditionsAndReturnedTheBenchmarkResult(
        @TempDir Path testPath
    ) throws IOException {

        var timeSingleRender = getBenchmarkSingleRender(testPath);
        var timeMultiRender = getBenchmarkMultiRender(testPath);

        System.out.printf(
            "Время однопоточного рендеринга %f секунд\n",
            timeSingleRender
        );
        System.out.printf(
            "Время многопоточного рендеринга в 5 потоков  %f секунд\n",
            timeMultiRender
        );

        assertThat(timeSingleRender > timeMultiRender).isTrue();
    }

    private double getBenchmarkSingleRender(Path testPath) throws IOException {
        var imageNameSingleThreading = "singleThreading.jpeg";
        var pathToSaveFileSingle = testPath.resolve(imageNameSingleThreading);

        var startTimeSingle = System.nanoTime();
        render(pathToSaveFileSingle, SINGLE_THREADING_ARG);
        var endTimeSingle = System.nanoTime();

        return (endTimeSingle - startTimeSingle) * NANO_T0_SECONDS;
    }

    private double getBenchmarkMultiRender(Path testPath) throws IOException {
        var imageNameMultiThreading = "multiThreading.jpeg";

        var pathToSaveFileMulti = testPath.resolve(imageNameMultiThreading);

        var startTimeMulti = System.nanoTime();
        render(pathToSaveFileMulti, MULTITHREADING_ARG);
        var endTimeMulti = System.nanoTime();

        return (endTimeMulti - startTimeMulti) * NANO_T0_SECONDS;
    }

    private void render(Path pathToSave, String args) throws IOException {
        var arguments = ArgumentsParser.parseArguments(args.formatted(pathToSave.toString()).split(" "));

        Renderer.render(
            arguments.image(),
            arguments.settings(),
            arguments.rect(),
            arguments.nonLinearTransformList(),
            arguments.countThread()
        );
    }

}
