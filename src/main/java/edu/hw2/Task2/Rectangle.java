package edu.hw2.Task2;

/**
 * Class rectangle
 */
public class Rectangle {
    private int width;
    private int height;

    /**
     * Rectangle constructor.
     *
     * @param width  width rectangle.
     * @param height height rectangle.
     */
    public Rectangle(int width, int height) {
        isValidSize(width, height);
        this.width = width;
        this.height = height;
    }

    /**
     * Method size change's rectangle.
     *
     * @param width  new width rectangle.
     * @param height new height rectangle.
     */
    public void resize(int width, int height) {
        isValidSize(width, height);
        this.width = width;
        this.height = height;
    }

    /**
     * Method of checking the sides of a rectangle for non-negativity.
     *
     * @param width  width rectangle.
     * @param height height rectangle.
     * @throws NumberFormatException if side of rectangle is negativity return NumberFormatException.
     */
    protected void isValidSize(int width, int height) throws NumberFormatException {
        if (width <= 0 || height <= 0) {
            throw new NumberFormatException("invalid dimensions");
        }
    }

    /**
     * Method for calculate rectangle's area.
     *
     * @return rectangle's area.
     */
    public double area() {
        return this.width * this.height;
    }
}
