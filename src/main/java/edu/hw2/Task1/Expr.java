package edu.hw2.Task1;

public sealed interface Expr {
    double evaluate();

    record Constant(double n) implements Expr {
        @Override
        public double evaluate() {
            return n;
        }
    }

    record Negate(Expr n) implements Expr {
        @Override
        public double evaluate() {
            return -n.evaluate();
        }
    }

    record Exponent(Expr n, int pow) implements Expr {
        @Override
        public double evaluate() {
            return Math.pow(n.evaluate(), pow);
        }
    }

    record Addition(Expr n1, Expr n2) implements Expr {
        @Override
        public double evaluate() {
            return n1.evaluate() + n2.evaluate();
        }
    }

    record Multiplication(Expr n1, Expr n2) implements Expr {
        @Override
        public double evaluate() {
            return n1.evaluate() + n2.evaluate();
        }
    }
}
