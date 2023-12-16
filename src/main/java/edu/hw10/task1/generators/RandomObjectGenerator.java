package edu.hw10.task1.generators;

import edu.hw10.task1.value_annorations.Max;
import edu.hw10.task1.value_annorations.Min;
import edu.hw10.task1.value_annorations.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RandomObjectGenerator {

    public RandomObjectGenerator() {

    }

    public final Object nextObject(Class<?> objectClass)
        throws InvocationTargetException, IllegalAccessException, InstantiationException {
        var constructor = objectClass.getConstructors()[0];
        var constructorParameters = constructor.getParameters();

        var objectParameters = produceObjectParameters(constructorParameters);

        return constructor.newInstance(objectParameters);
    }

    public final Object nextObject(Class<?> objectClass, String methodName)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var constructor = objectClass.getDeclaredConstructors()[0];

        var factoryMethod = objectClass.getMethod(methodName, constructor.getParameterTypes());
        var methodParameters = factoryMethod.getParameters();

        var objectParameters = produceObjectParameters(methodParameters);

        return factoryMethod.invoke(objectClass, objectParameters);
    }

    private Object[] produceObjectParameters(Parameter[] parameters)
        throws InvocationTargetException, IllegalAccessException {
        var annotatedMethods = findAnnotatedMethods();

        int parameterLength = parameters.length;

        Object[] randomParameterObjects = new Object[parameterLength];
        for (int i = 0; i < parameterLength; i++) {
            randomParameterObjects[i] = annotatedMethods
                .get(parameters[i].getType())
                .invoke(this, (Object) parameters[i].getAnnotations());
        }

        return randomParameterObjects;
    }

    private Map<Class<?>, Method> findAnnotatedMethods() {
        Map<Class<?>, Method> methodMap = new HashMap<>();

        Arrays.stream(getClass().getDeclaredMethods())
            .filter(method -> method.getAnnotation(ObjectGenerator.class) != null)
            .forEach(method -> methodMap.put(method.getReturnType(), method));

        Class<?> superClass = getClass().getSuperclass();
        while (superClass != null && superClass != Object.class) {
            Arrays.stream(superClass.getDeclaredMethods())
                .filter(method -> method.getAnnotation(ObjectGenerator.class) != null)
                .forEach(method -> methodMap.putIfAbsent(method.getReturnType(), method));

            superClass = superClass.getSuperclass();
        }
        return methodMap;
    }

    @ObjectGenerator
    protected Integer generateRandomInteger(Annotation[] annotations) {
        int minBorder = Integer.MIN_VALUE;
        int maxBorder = Integer.MAX_VALUE;
        boolean maybeNull = false;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Min) {
                minBorder = ((Min) annotation).intValue();
            } else if (annotation instanceof NotNull) {
                maybeNull = true;
            } else if (annotation instanceof Max) {
                maxBorder = ((Max) annotation).intValue();
            }
        }

        return (maybeNull) ? ThreadLocalRandom.current().nextInt(minBorder, maxBorder) : null;
    }

    @ObjectGenerator
    protected int generateRandomInt(Annotation[] annotations) {
        int minBorder = Integer.MIN_VALUE;
        int maxBorder = Integer.MAX_VALUE;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Min) {
                minBorder = ((Min) annotation).intValue();
            } else if (annotation instanceof Max) {
                maxBorder = ((Max) annotation).intValue();
            }
        }
        return ThreadLocalRandom.current().nextInt(minBorder, maxBorder);
    }

    @ObjectGenerator
    protected String generateRandomString(Annotation[] annotations) {
        boolean maybeNull = false;
        for (Annotation annotation : annotations) {
            if (annotation instanceof NotNull) {
                maybeNull = true;
            }
        }

        return (maybeNull) ? buildString() : null;
    }

    private String buildString() {
        String characters = "abcde123";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            randomString.append(
                characters.charAt(ThreadLocalRandom.current().nextInt(characters.length()))
            );
        }
        return randomString.toString();
    }

    @ObjectGenerator
    protected Double generateRandomDouble(Annotation[] annotations) {
        double minBorder = Double.MIN_VALUE;
        double maxBorder = Double.MAX_VALUE;
        boolean maybeNull = false;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Min) {
                minBorder = ((Min) annotation).doubleValue();
            } else if (annotation instanceof NotNull) {
                maybeNull = true;
            } else if (annotation instanceof Max) {
                maxBorder = ((Max) annotation).doubleValue();
            }
        }

        return (maybeNull) ? ThreadLocalRandom.current().nextDouble(minBorder, maxBorder) : null;
    }

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
