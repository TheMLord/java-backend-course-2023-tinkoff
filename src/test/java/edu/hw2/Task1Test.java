package edu.hw2;

import edu.hw2.Task1.Expr;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Test creating constants")
    void testCreatingConstants() {
        int a = 5;

        Expr constant = new Expr.Constant(a);

        assertThat(constant).isInstanceOf(Expr.class);
        assertThat(constant.evaluate()).isEqualTo(a);
    }

    @Test
    @DisplayName("Test negative constants")
    void testNegativeConstants() {
        int a = -100;

        Expr constant = new Expr.Constant(a);

        assertThat(constant).isInstanceOf(Expr.class);
        assertThat(constant.evaluate()).isEqualTo(a);
    }

    @Test
    @DisplayName("Test calculating the expression")
    void testCalculatingTheExpression() {
        var two = new Expr.Constant(2);
        var four = new Expr.Constant(4);
        var negOne = new Expr.Negate(new Expr.Constant(1));
        var sumTwoFour = new Expr.Addition(two, four);
        var mult = new Expr.Multiplication(sumTwoFour, negOne);
        var exp = new Expr.Exponent(mult, 2);
        var res = new Expr.Addition(exp, new Expr.Constant(1));

        assertThat(res).isInstanceOf(Expr.class);
        assertThat(res.evaluate()).isEqualTo(26);
    }

}
