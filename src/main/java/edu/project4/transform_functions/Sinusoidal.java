package edu.project4.transform_functions;

import edu.project4.models.Point;

public class Sinusoidal implements Transform {
    @Override
    public Point apply(Point point) {
        return new Point(
            Math.sin(point.x()),
            Math.sin(point.y())
        );
    }
}
