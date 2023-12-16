package edu.hw10.task1.generators;

import java.lang.annotation.Annotation;
import java.util.concurrent.ThreadLocalRandom;

public class ChildRandomObjectGenerator extends RandomObjectGenerator {
    @Override
    @ObjectGenerator
    protected String generateRandomString(Annotation[] annotations) {
        return "Michael";
    }

    @Override
    @ObjectGenerator
    protected double generateRandomDoublePrimitive(Annotation[] annotations) {
        return ThreadLocalRandom.current().nextDouble(1.0);
    }
}
