package edu.hw2.Task2;

/**
 * Class square.The inheritor of the rectangle class
 */
public class Square extends Rectangle {
    /**
     * Class constrictor
     *
     * @param size square side size.
     */
    public Square(int size) {
        super(size, size);
    }

    @Override
    public void resize(int width, int height) throws NumberFormatException {
        isValidSize(width, height);
        if (width != height) {
            throw new NumberFormatException("The width and height must be the same size");
        }
        super.resize(width, height);
    }
}
