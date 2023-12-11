package edu.project4;

import edu.project4.models.Arguments;
import edu.project4.models.Pixel;
import edu.project4.processors.ImageProcessor;
import edu.project4.utilities.ImageUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class TestImage {
    private static final String PATH_TO_COEFF =
        Paths.get(
            String.valueOf(Paths.get("").toAbsolutePath()),
            "src/test/java/edu/project4/coeff.txt"
        ).toString();

    private static final String VALID_ARG = "--size 200x200 " +
        "--nonlinear 2 Diamond Spiral " +
        "--coeff " + PATH_TO_COEFF + " " +
        "--samples 20000 " +
        "--iterations 1500 " +
        "--symmetry 2 " +
        "--gamma 2.2 " +
        "--threads 5 " +
        "--pathToSave %s " +
        "--formatImg JPEG";

    @Test
    @DisplayName(
        "Test that the program correctly creates the image according to the parameters and returned the correct parameters for the created image")
    void testThatTheProgramCorrectlyCreatesTheImageAccordingToTheParametersAndReturnedTheCorrectParametersForTheCreatedImage(
        @TempDir Path testDir
    ) throws IOException {
        var exceptedName = "text.jpeg";
        var exceptedHeightImage = 200;
        var exceptedWeightImage = 200;

        var pathToSaveFile = testDir.resolve(exceptedName);

        createFlame(pathToSaveFile);

        var actualImageFile = pathToSaveFile.toFile();
        var actualImage = ImageIO.read(pathToSaveFile.toFile());

        assertThat(actualImageFile.getPath().endsWith("text.jpeg")).isTrue();
        assertThat(actualImage.getHeight()).isEqualTo(exceptedHeightImage);
        assertThat(actualImage.getWidth()).isEqualTo(exceptedWeightImage);
    }

    @Nested
    @DisplayName("image conversion tests")
    class ImageConversion {
        @Test
        @DisplayName("Test that the program changes the pixel color of the original image")
        void testThatTheProgramChangesThePixelColorOfTheOriginalImage(@TempDir Path testDir) throws IOException {
            var arguments = getProgramArguments(testDir);

            var fullBlackPixels = getFullBlackStartImage();
            var pixelsAfterRender = getPixelsAfterProgramWork(arguments);

            assertThat(fullBlackPixels).isNotEqualTo(pixelsAfterRender);
        }

        @Test
        @DisplayName(
            "Test that gamma noise elimination gives the result returned the difference between the processed and unprocessed image")
        void testThatGammaNoiseEliminationGivesTheResultReturnedTheDifferenceBetweenTheProcessedAndUnprocessedImage(
            @TempDir Path testDir
        )
            throws IOException {
            var arguments = getProgramArguments(testDir);
            var arguments2 = getProgramArguments(testDir);

            var onlyRenderedPixels = getPixelsAfterProgramWork(arguments);
            var renderedAndGammaCorrectionPixels = getPixelsAfterCorrection(arguments2);

            assertThat(onlyRenderedPixels).isNotEqualTo(renderedAndGammaCorrectionPixels);
        }

        private Arguments getProgramArguments(Path testDir) throws IOException {
            var exceptedName = "text.jpeg";
            var pathToSaveFile = testDir.resolve(exceptedName);
            return ArgumentsParser.parseArguments(VALID_ARG.formatted(pathToSaveFile.toString()).split(" "));

        }

        private Pixel[][] getFullBlackStartImage() {
            return ImageUtils.pixelsImageInit(200, 200);
        }

        private Pixel[][] getPixelsAfterProgramWork(Arguments arguments) {
            return Renderer.render(
                arguments.image(),
                arguments.settings(),
                arguments.rect(),
                arguments.nonLinearTransformList(),
                arguments.countThread()
            ).pixels();
        }

        private Pixel[][] getPixelsAfterCorrection(Arguments arguments) {
            var render = Renderer.render(
                arguments.image(),
                arguments.settings(),
                arguments.rect(),
                arguments.nonLinearTransformList(),
                arguments.countThread()
            );

            return ImageProcessor.gammaCorrection(render, arguments.settings()).pixels();

        }

    }

    private void createFlame(Path pathToSave) throws IOException {
        var arguments = ArgumentsParser.parseArguments(VALID_ARG.formatted(pathToSave.toString()).split(" "));

        var renderedImage = Renderer.render(
            arguments.image(),
            arguments.settings(),
            arguments.rect(),
            arguments.nonLinearTransformList(),
            arguments.countThread()
        );

        ImageUtils.save(
            ImageProcessor.gammaCorrection(renderedImage, arguments.settings()),
            arguments.pathToSave(),
            arguments.format()
        );
    }

}
