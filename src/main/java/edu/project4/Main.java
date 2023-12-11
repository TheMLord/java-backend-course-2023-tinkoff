package edu.project4;

import edu.project4.processors.ImageProcessor;
import edu.project4.utilities.ImageUtils;
import java.io.IOException;

public final class Main {
    private Main() {

    }

    public static void main(String[] args) throws IOException {
        var arguments = ArgumentsParser.parseArguments(args);

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
