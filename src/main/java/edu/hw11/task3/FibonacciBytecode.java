package edu.hw11.task3;

import java.lang.reflect.Modifier;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

public final class FibonacciBytecode implements Implementation {
    private static final String METHOD_NAME = "fib";
    private static final String METHOD_DESCRIPTOR = "(I)J";
    private static final String METHOD_METHOD_OWNER = "edu/hw11/task3/Fibonacci";

    /**
     * Class constructor.
     */
    private FibonacciBytecode() {

    }

    /**
     * Method that returns the constructed Fibonacci class with the fib method for calculating the fibonacci number
     */
    public static Class<?> createClassFibonacci() {
        try (var byteBuddyClass = new ByteBuddy()
            .subclass(Object.class)
            .name("edu.hw11.task3.Fibonacci")
            .defineMethod(
                METHOD_NAME,
                long.class,
                Modifier.PUBLIC | Modifier.STATIC
            )
            .withParameters(int.class)
            .intercept(new FibonacciBytecode())
            .make()) {
            return byteBuddyClass
                .load(
                    ClassLoader.getSystemClassLoader(),
                    ClassLoadingStrategy.Default.INJECTION
                )
                .getLoaded();
        }
    }

    /*
    public static long fib(int number) {
        if (number <= 1) {
            return number;
        } else {
            return fib(number - 1) + fib(number - 2);
        }
    }
     */

    /**
     * Method for building a dynamic Fibonacci class via ByteCodeAppender
     */
    @SuppressWarnings("MagicNumber")
    @Override
    public @NotNull ByteCodeAppender appender(@NotNull Target target) {
        return (methodVisitor, context, methodDescription) -> {
            methodVisitor.visitCode();

            buildLoadingParameter(methodVisitor);

            buildBranches(methodVisitor);

            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            buildRecursiveCalls(methodVisitor);

            methodVisitor.visitInsn(Opcodes.LADD);
            methodVisitor.visitInsn(Opcodes.LRETURN);

            methodVisitor.visitEnd();

            return new ByteCodeAppender.Size(
                new ByteCodeAppender.Size(10, 10).getLocalVariableSize(),
                methodDescription.getStackSize()
            );
        };
    }

    /**
     * Method setting the local variable for the method from the parameter
     */
    private void buildLoadingParameter(net.bytebuddy.jar.asm.MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
        methodVisitor.visitInsn(Opcodes.I2L);
        methodVisitor.visitInsn(Opcodes.LCONST_1);
        methodVisitor.visitInsn(Opcodes.LCMP);
    }

    /**
     * Method of building branches if number <= 1 else ...
     */
    private void buildBranches(net.bytebuddy.jar.asm.MethodVisitor methodVisitor) {
        var conditionalBranchesLabel = new Label();
        methodVisitor.visitJumpInsn(Opcodes.IFGT, conditionalBranchesLabel);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
        methodVisitor.visitInsn(Opcodes.I2L);
        methodVisitor.visitInsn(Opcodes.LRETURN);
        methodVisitor.visitLabel(conditionalBranchesLabel);
    }

    /**
     * Method of buildings recursive calls to find the Fibonacci number such as fib(number-1) amd fib(number-2)
     */
    private void buildRecursiveCalls(net.bytebuddy.jar.asm.MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
        methodVisitor.visitInsn(Opcodes.I2L);
        methodVisitor.visitInsn(Opcodes.LCONST_1);
        methodVisitor.visitInsn(Opcodes.LSUB);
        methodVisitor.visitInsn(Opcodes.L2I);
        methodVisitor.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            METHOD_METHOD_OWNER,
            METHOD_NAME,
            METHOD_DESCRIPTOR,
            false
        );

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
        methodVisitor.visitInsn(Opcodes.I2L);
        methodVisitor.visitInsn(Opcodes.ICONST_2);
        methodVisitor.visitInsn(Opcodes.I2L);
        methodVisitor.visitInsn(Opcodes.LSUB);
        methodVisitor.visitInsn(Opcodes.L2I);
        methodVisitor.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            METHOD_METHOD_OWNER,
            METHOD_NAME,
            METHOD_DESCRIPTOR,
            false
        );
    }

    @Override
    public @NotNull InstrumentedType prepare(@NotNull InstrumentedType instrumentedType) {
        return instrumentedType;
    }
}
