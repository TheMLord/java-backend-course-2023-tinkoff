package edu.hw10.task1.generators;

import edu.hw10.task1.value_annorations.Max;
import edu.hw10.task1.value_annorations.Min;
import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class to demonstrate an extension of the parent
 * class Random Object Generator with a redefined and new generation method.
 */
public class ChildRandomObjectGenerator extends RandomObjectGenerator {
    /**
     * Demonstration of overriding the method of the parent class
     */
    @Override
    @ObjectGenerator
    protected String generateRandomString(Annotation[] annotations) {
        return "Michael";
    }

    /**
     * Demonstration of adding a new generating method
     */
    @ObjectGenerator
    protected double generateRandomDoublePrimitive(Annotation[] annotations) {
        double minBorder = Double.MIN_VALUE;
        double maxBorder = Double.MAX_VALUE;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Min) {
                minBorder = ((Min) annotation).doubleValue();
            } else if (annotation instanceof Max) {
                maxBorder = ((Max) annotation).doubleValue();
            }
        }

        return ThreadLocalRandom.current().nextDouble(minBorder, maxBorder);
    }
}
