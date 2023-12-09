package edu.project4.transform_functions;

import edu.project4.models.Point;

public class Linear implements Transform {
    @Override
    public Point apply(Point point) {
        return new Point(point.x(), point.x());

    }
}
