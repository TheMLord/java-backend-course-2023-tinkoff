package edu.project4;

import edu.project4.models.Arguments;
import edu.project4.models.FractalImage;
import edu.project4.models.ImageFormat;
import edu.project4.models.flame_models.FlameCoefficient;
import edu.project4.models.flame_models.FlameSettings;
import edu.project4.transform_functions.Diamond;
import edu.project4.transform_functions.Disk;
import edu.project4.transform_functions.Handkerchief;
import edu.project4.transform_functions.Heart;
import edu.project4.transform_functions.Horseshoe;
import edu.project4.transform_functions.Linear;
import edu.project4.transform_functions.Polar;
import edu.project4.transform_functions.Sinusoidal;
import edu.project4.transform_functions.Spherical;
import edu.project4.transform_functions.Spiral;
import edu.project4.transform_functions.Swirl;
import edu.project4.transform_functions.Transform;
import edu.project4.utilities.AffineUtils;
import edu.project4.utilities.ImageUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"ModifiedControlVariable", "InnerAssignment", "MultipleStringLiterals", "ParameterAssignment"})
public final class ArgumentsParser {
    private static final String SIZE_SEPARATOR = "x";
    private static final String INCORRECT_TRANSFORM_ARGUMENTS = "invalid specified argument of a non-linear function";
    private static final String INVALID_ARGUMENT = "you have specified an incorrect argument";

    private ArgumentsParser() {

    }

    /*
    --size 1920x1080
    --nonlinear 2 Swirl Spiral
    --coeff auto 20 | /home/themlord/IdeaProjects/java-backend-course-2023-tinkoff/coeff.txt
    --samples 40000
    --iterations 3500
    --symmetry 2
    --gamma 2.2
    --threads 5
     */
    public static Arguments parseArguments(String[] args)
        throws IllegalArgumentException, IOException, NumberFormatException {
        List<Integer> sizeList = new ArrayList<>();
        List<Transform> transformList = new ArrayList<>();
        FlameCoefficient[] flameCoefficients = null;
        int samples = 0;
        int iterations = 0;
        int symmetry = 0;
        double gamma = 0.0;
        int countThreads = 0;
        Path pathToSave = null;
        ImageFormat imageFormat = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--size" -> sizeList = parseSize(args, ++i);
                case "--nonlinear" -> {
                    int n = Integer.parseInt(args[++i]);
                    i++;
                    transformList = parseTransform(args, n, i);
                    i += n - 1;
                }
                case "--coeff" -> {
                    flameCoefficients = parseCoefficients(args, ++i);
                    i = (args[i].equals("auto")) ? i + 1 : i;
                }
                case "--samples" -> samples = parseIntegerNumber(args, ++i);
                case "--iterations" -> iterations = parseIntegerNumber(args, ++i);
                case "--symmetry" -> symmetry = parseIntegerNumber(args, ++i);
                case "-gamma" -> gamma = parseDoubleNumber(args, ++i);
                case "--threads" -> countThreads = parseIntegerNumber(args, ++i);
                case "--pathToSave" -> pathToSave = Path.of(args[++i]);
                case "--formatImg" -> imageFormat = parseImageFormat(args, ++i);
                default -> throw new IllegalArgumentException(INVALID_ARGUMENT);
            }
        }
        return new Arguments(
            new FractalImage(
                ImageUtils.pixelsImageInit(sizeList.get(0), sizeList.get(1)),
                sizeList.get(0),
                sizeList.get(1)
            ),

            new FlameSettings(
                flameCoefficients,
                samples,
                iterations,
                symmetry,
                gamma
            ),
            AffineUtils.getAffineRect(sizeList.get(0), sizeList.get(1)),
            transformList,
            countThreads,
            pathToSave,
            imageFormat
        );
    }

    private static List<Integer> parseSize(String[] args, int iPosition) {
        return Arrays.stream(args[iPosition].split(SIZE_SEPARATOR))
            .map(Integer::parseInt)
            .toList();
    }

    private static List<Transform> parseTransform(String[] args, int n, int iPosition) {
        List<Transform> transforms = new ArrayList<>();

        for (int j = iPosition; j < iPosition + n; j++) {
            transforms.add(parseNonLinearTransform(args[j]));
        }
        return transforms;
    }

    private static Transform parseNonLinearTransform(String s) throws IllegalArgumentException {
        Transform transform;
        switch (s) {
            case "Diamonds" -> transform = new Diamond();
            case "Disk" -> transform = new Disk();
            case "Handkerchief" -> transform = new Handkerchief();
            case "Heart" -> transform = new Heart();
            case "Horseshoe" -> transform = new Horseshoe();
            case "Linear" -> transform = new Linear();
            case "Polar" -> transform = new Polar();
            case "Sinusoidal" -> transform = new Sinusoidal();
            case "Spherical" -> transform = new Spherical();
            case "Spiral" -> transform = new Spiral();
            case "Swirl" -> transform = new Swirl();
            default -> throw new IllegalArgumentException(INCORRECT_TRANSFORM_ARGUMENTS);
        }
        return transform;
    }

    private static FlameCoefficient[] parseCoefficients(String[] args, int iPosition) throws IOException {
        if (!args[iPosition].equals("auto")) {
            return AffineUtils.parseFlameCoefficient(Path.of(args[iPosition]));
        }
        iPosition++;
        int n = Integer.parseInt(args[iPosition]);
        return AffineUtils.generateAffineCoefficients(n);
    }

    private static int parseIntegerNumber(String[] args, int iPosition) {
        return Integer.parseInt(args[iPosition]);
    }

    private static double parseDoubleNumber(String[] args, int iPosition) {
        return Double.parseDouble(args[iPosition]);
    }

    private static ImageFormat parseImageFormat(String[] args, int iPosition) {
        return ImageFormat.valueOf(args[iPosition]);
    }

}
