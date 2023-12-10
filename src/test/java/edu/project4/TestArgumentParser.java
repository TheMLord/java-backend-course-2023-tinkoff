package edu.project4;

import edu.project4.models.ImageFormat;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.project4.models.ImageFormat.BMP;
import static edu.project4.models.ImageFormat.JPEG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TestArgumentParser {

    private static final String PATH_TO_COEFF =
        Paths.get(
            String.valueOf(Paths.get("").toAbsolutePath()),
            "src/test/java/edu/project4/coeff.txt"
        ).toString();

    private static Stream<Arguments> validArg() {
        return Stream.of(
            Arguments.of(
                "--size 1920x1080 " +
                    "--nonlinear 1 Diamond " +
                    "--coeff auto 20 " +
                    "--samples 40000 " +
                    "--iterations 3500 " +
                    "--symmetry 1 " +
                    "--gamma 2.2 " +
                    "--threads 5 " +
                    "--pathToSave /home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/a.bmp " +
                    "--formatImg BMP",
                1920,
                1080,
                1,
                20,
                40000,
                3500,
                1,
                2.2,
                5,
                Path.of("/home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/a.bmp"),
                BMP
            ),
            Arguments.of(
                "--size 1920x1080 " +
                    "--nonlinear 11 Diamond Disk Handkerchief Heart Horseshoe Linear Polar Sinusoidal Spherical Spiral Swirl " +
                    "--coeff " + PATH_TO_COEFF + " " +
                    "--samples 45684 " +
                    "--iterations 4000 " +
                    "--symmetry 2 " +
                    "--gamma 1.2 " +
                    "--threads 5 " +
                    "--pathToSave /home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/b.jpeg " +
                    "--formatImg JPEG",
                1920,
                1080,
                11,
                4,
                45684,
                4000,
                2,
                1.2,
                5,
                Path.of("/home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/b.jpeg"),
                JPEG
            )
        );
    }

    private static Stream<Arguments> invalidArg() {
        return Stream.of(
            Arguments.of(
                "--size 1920x1080 " +
                    "--nonliear 1 Diamond " +
                    "--coeff auto 20 " +
                    "--samples 40000 " +
                    "--iterations 3500 " +
                    "--symmetry 1 " +
                    "--gamma 2.2 " +
                    "--threads 5 " +
                    "--pathToSave /home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/a.bmp " +
                    "--formatImg BMP"
            ),
            Arguments.of(
                "--size 1920x1080 " +
                    "--nonlinear 11 Diamddond Disk Handkerchief Heart Horseshoe Linear Polar Sinusoidal Spherical Spiral Swirl " +
                    "--coeff " + PATH_TO_COEFF + " " +
                    "--samples 45684 " +
                    "--iterations 4000 " +
                    "--symmetry 2 " +
                    "--gamma 1.2 " +
                    "--threads 5 " +
                    "--pathToSave /home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/b.jpeg " +
                    "--formatImg JPEG"
            ),
            Arguments.of(
                "--size 1920x1080 " +
                    "--nonlinear 11 Diamddond Disk Handkerchief Heart Horseshoe Linear Polar Sinusoidal Spherical Spiral Swirl " +
                    "--coeff " + PATH_TO_COEFF + " " +
                    "--samples 45684 " +
                    "--iterations 4000 " +
                    "--symmetry 2 " +
                    "--gamma 1.2 " +
                    "--threads 5 " +
                    "--pathToSave /home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/b.jpeg " +
                    "--formatImg fdsfdf"
            ),
            Arguments.of(
                "--sizzes 1920x1080 " +
                    "--nonlinear11 Diamddond Disk Handkerchief Heart Horseshoe Linear Polar Sinusoidal Spherical Spiral Swirl " +
                    "--coeff " + PATH_TO_COEFF + " " +
                    "--samples 45684 " +
                    "--iterations 4000 " +
                    "--symmetry 2 " +
                    "--gamma 1.2 " +
                    "--threads 5 " +
                    "--pathToSave /home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/b.jpeg " +
                    "--formatImg JPEG"
            )

        );
    }

    @ParameterizedTest
    @MethodSource("validArg")
    @DisplayName("Test that arguments are parsed correctly and returns correctly recognized arguments for correct input")
    void testThatArgumentsAreParsedCorrectlyAndReturnsCorrectlyRecognizedArgumentsForCorrectInput(
        String arg,
        int exceptedHeight,
        int exceptedWeight,
        int exceptedCountNonLinearFunc,
        int exceptedCountCoeff,
        int exceptedSamples,
        int exceptedCountIter,
        int exceptedSymmetry,
        double exceptedGamma,
        int exceptedCountThread,
        Path exceptedPathToSave,
        ImageFormat exceptedImageFormat
    ) throws IOException {
        var actualArg = ArgumentsParser.parseArguments(arg.split(" "));

        assertThat(actualArg.image().height()).isEqualTo(exceptedHeight);
        assertThat(actualArg.image().weight()).isEqualTo(exceptedWeight);
        assertThat(actualArg.nonLinearTransformList().size()).isEqualTo(exceptedCountNonLinearFunc);
        assertThat(actualArg.settings().coefficient().length).isEqualTo(exceptedCountCoeff);
        assertThat(actualArg.settings().samples()).isEqualTo(exceptedSamples);
        assertThat(actualArg.settings().iterations()).isEqualTo(exceptedCountIter);
        assertThat(actualArg.settings().symmetry()).isEqualTo(exceptedSymmetry);
        assertThat(actualArg.settings().gamma()).isEqualTo(exceptedGamma);
        assertThat(actualArg.countThread()).isEqualTo(exceptedCountThread);
        assertThat(actualArg.pathToSave()).isEqualTo(exceptedPathToSave);
        assertThat(actualArg.format()).isEqualTo(exceptedImageFormat);
    }

    @ParameterizedTest
    @MethodSource("invalidArg")
    @DisplayName("Test that the arguments are parsed correctly and returns the correct error for incorrect input")
    void testThatTheArgumentsAreParsedCorrectlyAndReturnsTheCorrectErrorForIncorrectInput(String arg) {
        assertThatThrownBy(() -> ArgumentsParser.parseArguments(arg.split(" ")))
            .isInstanceOf(IllegalArgumentException.class);

    }

}
