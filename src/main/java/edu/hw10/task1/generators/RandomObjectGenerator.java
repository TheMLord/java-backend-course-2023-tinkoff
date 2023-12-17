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

/**
 * Content generation class for POJO and Records representatives.
 * <p>
 * The findAnnotatedMethods() method performs reflection and finds methods in the current class
 * or superclasses and returns methods that are capable of generating object fields to generate.
 * The produceObjectParameters() method examines the parameters of a factory class or
 * constructor and calls the corresponding generation method obtained from findAnnotatedMethods().
 * <p>
 * You cannot override findAnnotatedMethods() and produceObjectParameters()
 * <p>
 * It is possible to write new methods marked with the @ObjectGenerator annotation
 * or redefine the methods of the parent. Thus, the capabilities of this class are expanded
 */
public class RandomObjectGenerator {

    /**
     * Class constructor.
     */
    public RandomObjectGenerator() {

    }

    /**
     * Method of generating an object through the constructor.
     *
     * @param objectClass the class to create an instance of.
     * @return an instance of the objectClass class with initialized fields specified in the constructor.
     */
    public final Object nextObject(Class<?> objectClass)
        throws InvocationTargetException, IllegalAccessException, InstantiationException {
        var constructor = objectClass.getConstructors()[0];
        var constructorParameters = constructor.getParameters();

        var objectParameters = produceObjectParameters(constructorParameters);

        return constructor.newInstance(objectParameters);
    }

    /**
     * Method of generating an object through the factory method.
     *
     * @param objectClass the class to create an instance of.
     * @param methodName  name of the factory method.
     * @return an instance of the objectClass class with initialized fields specified in the factory method
     */
    public final Object nextObject(Class<?> objectClass, String methodName)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var constructor = objectClass.getDeclaredConstructors()[0];

        var factoryMethod = objectClass.getMethod(methodName, constructor.getParameterTypes());
        var methodParameters = factoryMethod.getParameters();

        var objectParameters = produceObjectParameters(methodParameters);

        return factoryMethod.invoke(objectClass, objectParameters);
    }

    /**
     * Method that returns generated arguments for a constructor or factory method
     *
     * @param parameters parameters of the constructor or factory method
     */
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

    /**
     * Method for reflecting oneself and superclasses to search for methods marked with an ObjectGenerator annotation.
     */
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

    @SuppressWarnings("MagicNumber")
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
}
