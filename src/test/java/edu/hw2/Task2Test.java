package edu.hw2;

import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task2Test {
    @Test
    @DisplayName("Test polymorphic creation of a valid square")
    void testPolymorphicCreationOfAValidSquare() {
        Rectangle square4x4 = new Square(4);
        assertThat(square4x4).isNotNull();
    }

    @Test
    @DisplayName("Test creating a rectangle with invalid sides")
    void testCreatingARectangleWithInvalidSides() {
        assertThatThrownBy(() -> new Rectangle(4, -8))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("invalid dimensions");

        assertThatThrownBy(() -> new Square(-5))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("invalid dimensions");
    }

    @Test
    @DisplayName("Test invalid change of rectangle sides")
    void testInvalidChangeOfRectangleSides() {
        Rectangle rectangle = new Rectangle(88, 77);

        assertThatThrownBy(() -> rectangle.resize(-4, 5))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("invalid dimensions");

        assertThatThrownBy(() -> rectangle.resize(44, -75))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("invalid dimensions");

        assertThatThrownBy(() -> rectangle.resize(-56, -12))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("invalid dimensions");

        assertThatThrownBy(() -> rectangle.resize(0, 58))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("invalid dimensions");

        assertThatThrownBy(() -> rectangle.resize(0, 0))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("invalid dimensions");

        assertThatThrownBy(() -> rectangle.resize(77, 0))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("invalid dimensions");
    }

    @Test
    @DisplayName("Test invalid change of the sides of the square")
    void testInvalidChangeOfTheSidesOfTheSquare() {
        Rectangle square = new Square(45);
        assertThatThrownBy(() -> square.resize(45, 98))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("The width and height must be the same size");
    }

    @Test
    @DisplayName("Test calculating the area")
    void testCalculatingTheArea() {
        Rectangle rectangle = new Rectangle(88, 79);
        Rectangle square = new Square(45);

        assertThat(rectangle.area()).isEqualTo(6952.0);
        assertThat(square.area()).isEqualTo(2025.0);

    }

}
