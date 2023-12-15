package edu.hw10.task1;

import edu.hw10.task1.value_annorations.Max;
import edu.hw10.task1.value_annorations.Min;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RandomObjectGenerator {

    public RandomObjectGenerator() {

    }

    public final void nextObject(Class<?> objectClass)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Constructor<?>[] constr = objectClass.getConstructors();

        Class<?>[] parameterTypes = Arrays.stream(constr[0].getParameters())
            .map(Parameter::getType)
            .toArray(Class[]::new);

        Map<Class<?>, Method> methodMap =
            Arrays.stream(this.getClass().getMethods())
                .filter(method -> !method.getReturnType().equals(void.class)
                    && !method.isSynthetic())
                .collect(Collectors.toMap(Method::getReturnType, method -> method));

        System.out.println(methodMap);
        System.out.println(methodMap.get(Integer.class).invoke(this));

        for (var parameter : constr[0].getParameters()) {
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Min) {
                    Min minAnnotation = (Min) annotation;
                    System.out.println("Min value for parameter " + parameter.getName() + ": " + minAnnotation.value());
                } else if (annotation instanceof Max) {
                    Max maxAnnotation = (Max) annotation;
                    System.out.println("Max value for parameter " + parameter.getName() + ": " + maxAnnotation.value());
                }
            }
        }

        Object[] randomParameterObjects = new Object[parameterTypes.length];

        var c = objectClass.getConstructor(parameterTypes);

    }

    protected Integer generateRandomInteger() {
        return ThreadLocalRandom.current().nextInt();
    }

}
