package edu.project5;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Benchmark class for accessing methods through reflection, direct access, MethodHandles and LambdaMetaFactory.
 */
@State(Scope.Benchmark)
public class ReflectionBenchmark {
    private static final String STUDENT_NAME = "Michael";
    private static final String STUDENT_SURNAME = "Berezin";

    private static final String METHOD_NAME = "name";

    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private StudentLambda lambdaMetaFactory;

    /**
     * Preparatory method for performing settings before measuring the performance of various accesses to the method
     */
    @Setup
    public void setup() throws Throwable {
        student = new Student(STUDENT_NAME, STUDENT_SURNAME);
        method = Student.class.getMethod(METHOD_NAME);
        methodHandle = configureMethodHandle();
        lambdaMetaFactory = configureMethodLambda();
    }

    /**
     * Setting method MethodHandle.
     */
    private MethodHandle configureMethodHandle() throws NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Method nameMethod = Student.class.getDeclaredMethod(METHOD_NAME);
        nameMethod.setAccessible(true);

        return lookup.unreflect(nameMethod);
    }

    /**
     * Setting method LambdaMetafactory.
     */
    private StudentLambda configureMethodLambda() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        CallSite callSite = LambdaMetafactory.metafactory(
            lookup,
            METHOD_NAME,
            MethodType.methodType(StudentLambda.class, Student.class),
            MethodType.methodType(String.class),
            lookup.unreflect(Student.class.getDeclaredMethod(METHOD_NAME)),
            MethodType.methodType(String.class)
        );
        return (StudentLambda) callSite.getTarget().bindTo(student).invoke();

    }

    /**
     * Test the performance of accessing a method through direct access.
     */
    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    /**
     * Test the performance of accessing a method through reflection.
     */
    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    /**
     * Test the performance of accessing a method through MethodHandle.
     */
    @Benchmark
    public void methodHandle(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invoke(student);
        bh.consume(name);
    }

    /**
     * Test the performance of accessing a method through LambdaMetaFactory.
     */
    @Benchmark
    public void lambdaMetaFactory(Blackhole bh) {
        String name = lambdaMetaFactory.name();
        bh.consume(name);
    }

}
